package com.zhang.chat.netty.handler;

import java.util.Date;

import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.netty.NettyClient;
import com.zhang.chat.netty.NettyListener;
import com.zhang.chat.netty.ResponseListener;
import com.zhang.chat.netty.protocol.MessageHolder;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.netty.serializer.Serializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by 张俨 on 2017/10/9.
 */

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private NettyListener listener;

    public NettyClientHandler(NettyListener listener) {
        this.listener = listener;
    }

    private ResponseListener responseListener;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyClient.getInstance().setConnectStatus(true);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_SUCCESS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyClient.getInstance().setConnectStatus(false);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_CLOSED);
        NettyClient.getInstance().reconnect();
    }

    /**
     * channel 通道
     * Read    读
     * <p>
     * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据
     * 但是这个数据在不进行解码时它是ByteBuf类型的,后面例子我们在介绍
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof MessageHolder) {
          if(listener != null) {
                MessageHolder messageHolder = (MessageHolder) msg;
              listener.onMessageResponse(messageHolder);
          }
        }


        //注意此处已经不需要手工解码了
        System.out.println(new Date() + " " + msg);

        //通知您已经链接上客户端[给客户端穿回去的数据加个换行]
        String str = "客户端端收到：" + new Date() + " " + msg + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * channelReadComplete
     * <p>
     * channel  通道
     * Read     读取
     * Complete 完成
     * <p>
     * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作
     * ctx.flush()
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /*
     * exceptionCaught
     *
     * exception	异常
     * Caught		抓住
     *
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     *
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }


}
