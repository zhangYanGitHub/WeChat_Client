package com.zhang.chat.main.menu.presenter;

import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.menu.contract.SearchFriendContract;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.ListUtil;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class SearchFriendPresenter extends SearchFriendContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void searchFriend(String key) {
        mModel.searchFriend(key, 1).subscribe(new ApiSubscribe<ResList<User>>(context, TAG, 0, true) {
            @Override
            public void onSuccess(int whichRequest, ResList<User> userResList) {
                for (User user : userResList.getList()) {
                    AppLog.e(TAG + " " + user.toString() + "\n");
                }
//                mModel.save(userResList.getList());
                if (ListUtil.isEmpty(userResList.getList())) {
                    mView.updateEmpty();
                    return;
                }
                mView.updateResult(userResList.getList());
            }

            @Override
            public void onError(int whichRequest, Throwable e) {

            }
        });

    }

}
