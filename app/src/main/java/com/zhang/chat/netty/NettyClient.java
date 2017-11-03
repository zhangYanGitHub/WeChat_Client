package com.zhang.chat.netty;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.zhang.chat.bean.NettyHeader;
import com.zhang.chat.bean.chat.Header;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.netty.mesagecoder.ProtocolEncoder;
import com.zhang.chat.netty.protocol.MessageHolder;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.netty.serializer.Serializer;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by 张俨 on 2017/10/9.
 */

public class NettyClient {

    private static NettyClient nettyClient = new NettyClient();

    private EventLoopGroup group;

    private NettyListener listener;

    private Channel channel;

    private boolean isConnect = false;

    private int reconnectNum = Integer.MAX_VALUE;

    private long reconnectIntervalTime = 5000;

    public static NettyClient getInstance() {
        return nettyClient;
    }

    public synchronized NettyClient connect() {
        if (!isConnect) {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap().group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer(listener));
            try {
                ChannelFuture future = bootstrap.connect(UrlConstant.SOCKET_HOST, UrlConstant.SOCKET_PORT).sync();
                if (future != null && future.isSuccess()) {
                    channel = future.channel();
                    isConnect = true;
                } else {
                    isConnect = false;
                }


            } catch (Exception e) {
                e.printStackTrace();
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                reconnect();
            }
        }
        return this;
    }

    public void disconnect() {
        group.shutdownGracefully();
    }

    public void reconnect() {
        if (reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AppLog.e("重新连接");
            disconnect();
            connect();
        } else {
            disconnect();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    private Header header = new Header();
    private NettyHeader nettyHeader = new NettyHeader();

    public boolean sendMsgToServer(MessageHolder msg, ChannelFutureListener listener) {
        boolean flag = channel != null && isConnect;
        if (flag) {
            String body = msg.getBody();
            header.setUser_id(ShareUtil.getPreferStr(Constant.USER_NAME));
            header.setToken(ShareUtil.getPreferStr(Constant.COOKIES_TOKEN));
            nettyHeader.setBody(body);
            nettyHeader.setHeader(header);
            body = Serializer.serialize(nettyHeader);
            if (body == null) {
                throw new NullPointerException("body == null");
            }
//            AppLog.e(NettyClient.class.getName());
            // 编码
            byte[] bytes = new byte[0];
            try {
                bytes = body.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ByteBuf buf = Unpooled.buffer();

            buf.writeShort(ProtocolHeader.MAGIC)
                    .writeByte(msg.getSign())
                    .writeByte(msg.getType())
                    .writeByte(msg.getStatus())
                    .writeInt(bytes.length)
                    .writeBytes(bytes);
            if(listener !=null){

                channel.writeAndFlush(buf).addListener(listener);
            }else {
                channel.writeAndFlush(buf).addListener(new FutureListener() {
                    @Override
                    public void success() {
                        AppLog.i( msg.toString());
                    }

                    @Override
                    public void error() {
                        AppLog.e(String.valueOf("失败" + msg.toString()));
                    }
                });
            }
        }
        return flag;
    }

    public void setReconnectNum(int reconnectNum) {
        this.reconnectNum = reconnectNum;
    }

    public void setReconnectIntervalTime(long reconnectIntervalTime) {
        this.reconnectIntervalTime = reconnectIntervalTime;
    }

    public boolean getConnectStatus() {
        return isConnect;
    }

    public void setConnectStatus(boolean status) {
        this.isConnect = status;
    }

    public void setListener(NettyListener listener) {
        this.listener = listener;
    }
}
