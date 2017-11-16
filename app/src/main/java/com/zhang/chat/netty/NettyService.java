package com.zhang.chat.netty;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.greendao.gen.UserDao;

import java.util.List;

import com.zhang.chat.app.AppManager;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.base.BaseResponse;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Header;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.login.LoginActivity;
import com.zhang.chat.main.MainActivity;
import com.zhang.chat.main.MainContract;
import com.zhang.chat.netty.protocol.MessageHolder;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.netty.serializer.Serializer;
import com.zhang.chat.utils.AppToast;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

public class NettyService extends Service implements NettyListener {

    private NetworkReceiver receiver;
    public static final String TAG = NettyService.class.getName();

    private void shutdown() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NettyClient.getInstance().setListener(this);
        connect();
        return START_NOT_STICKY;
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {     //连接状态监听
        AppLog.i(String.format("connect status: %d", statusCode));
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
            authenticData();
        } else {
            AppLog.e("tcp connect error");
        }
    }

    /**
     * 认证数据请求
     */
    private void authenticData() {
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setType(ProtocolHeader.USER_MESSAGE);
        messageHolder.setSign(ProtocolHeader.REQUEST);
        AppLog.e("authenticData  " + ProtocolHeader.USER_MESSAGE);
        Header user = new Header();
        user.setUser_id(ShareUtil.getPreferStr(Constant.USER_NAME));
        user.setToken(ShareUtil.getPreferStr(Constant.COOKIES_TOKEN));

        messageHolder.setBody(Serializer.serialize(user));
        NettyClient.getInstance().sendMsgToServer(messageHolder, new FutureListener() {
            @Override
            public void success() {
                AppLog.e("认证数据请求成功");
            }

            @Override
            public void error() {
                AppLog.e("认证数据请求失败");

            }
        });
    }

    @Override
    public void onMessageResponse(MessageHolder messageHolder) {
        MainActivity main = AppManager.getAppManager().getMain();
        MainContract.MainModel mainModel;
        if (main != null) {
            mainModel = main.getMainModel();
        } else {
            AppLog.e("mainModel == null");
            return;
        }
        switch (messageHolder.getType()) {
            case ProtocolHeader.PERSON_MESSAGE:
                mainModel.addBySocket(Serializer.deserialize(messageHolder.getBody(), com.zhang.chat.bean.chat.Message.class));
                break;
            case ProtocolHeader.PERSONAL_MESSAGE_LIST:
                mainModel.addBySocket(Serializer.deserialize(messageHolder.getBody(), ListMessage.class));
                break;
            case ProtocolHeader.Token:
                checkToken(messageHolder);
                break;
            case ProtocolHeader.ADD_FRIEND:
                mainModel.addBySocket(Serializer.deserialize(messageHolder.getBody(), Verification.class));
                break;
            case ProtocolHeader.Add_New_Friend_Info:
                mainModel.addBySocket(Serializer.deserialize(messageHolder.getBody(), Friend.class));
                break;
            default:
                break;
        }
        AppLog.e(messageHolder.toString());
        for (BaseActivity baseActivity : AppManager.getActivityStack()) {
            Message message = new Message();
            message.obj = messageHolder.getType();
            baseActivity.getHandler().sendMessage(message);
        }
    }

    private void checkToken(MessageHolder messageHolder) {
        BaseResponse deserialize = Serializer.deserialize(messageHolder.getBody(), BaseResponse.class);
        AppToast.showToast(deserialize.getInfo());
        ShareUtil.setPreferStr(Constant.COOKIES_TOKEN, "");
        ShareUtil.setPreferStr(Constant.USER_NAME, "");
        UserDao userDao = GreenDaoManager.getInstance().getUserDaoSession().getUserDao();
        List<User> list = userDao.queryBuilder()
                .where(UserDao.Properties.U_UserState.eq(1))
                .list();
        if (ListUtil.isEmpty(list) || list.size() != 1) {
            AppLog.e("数据库用户状态异常  IsPresenterUser == false");
        }
        User user = list.get(0);
        GreenDaoManager.getInstance().closeConnection();
        user.setU_UserState(0);
        AppLog.e(TAG + "  checkToken()  user.setU_UserState(0);");
        userDao.update(user);
        LoginActivity.startAction(AppManager.getAppManager().currentActivity());
        AppManager.getAppManager().removeAllActivity();
    }


    private void connect() {
        if (!NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();//连接服务器
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        shutdown();
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connect();
                }
            }
        }
    }
}