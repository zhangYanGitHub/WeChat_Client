package com.zhang.chat.main.contact;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import com.zhang.chat.R;
import com.zhang.chat.base.BaseFragment;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.corelib.recycleview.IRecyclerView;
import com.zhang.chat.main.adapter.ContactAdapter;
import com.zhang.chat.main.chat.activity.FriendDataActivity;
import com.zhang.chat.main.menu.activity.NewFriendStateActivity;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.view.HintSideBar;
import com.zhang.chat.view.SideBar;

/**
 * Created by 张俨 on 2017/9/11.
 */

public class ContactFragment extends BaseFragment<ContactPresenter, ContactModel>
        implements SideBar.OnChooseLetterChangedListener,
        ContactAdapter.OnClickListener, ContactContract.View {
    @BindView(R.id.rv_userList)
    IRecyclerView rvUserList;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;


    private ContactAdapter adapter;

    private LinearLayoutManager manager;
    private List<Friend> friends = new ArrayList<>();
    private View headView;
    private View ll_dot_new_friend;
    private View ll_dot_group_chat;
    private TextView tv_dot_new_friend;
    private TextView tv_dot_group_chat;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {
        manager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvUserList.setLayoutManager(manager);
        rvUserList.removeHeaderAllView();
        headView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_header, null, false);
        rvUserList.addHeaderView(headView);
        ll_dot_new_friend = headView.findViewById(R.id.ll_dot_new_friend);
        ll_dot_group_chat = headView.findViewById(R.id.ll_dot_group_chat);
        tv_dot_new_friend = (TextView) headView.findViewById(R.id.tv_dot_new_friend);
        tv_dot_group_chat = (TextView) headView.findViewById(R.id.tv_dot_group_chat);
        adapter = new ContactAdapter(mActivity, friends);
        adapter.setData(friends);
        adapter.setOnClickListener(this);
        rvUserList.setAdapter(adapter);
        initListener();

        mPresenter.initData();
    }

    private void initListener() {
        //添加朋友
        headView.findViewById(R.id.ll_new_friend).setOnClickListener(v -> {
            NewFriendStateActivity.startAction(getActivity());
            mPresenter.setIsRead();

        });
        //群聊
        headView.findViewById(R.id.ll_group_chat).setOnClickListener(v -> {

        });
        //标签
        headView.findViewById(R.id.ll_sign).setOnClickListener(v -> {
        });
        //公众号
        headView.findViewById(R.id.ll_public_sign).setOnClickListener(v -> {
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public void onChooseLetter(String s) {
        int i = adapter.getFirstPositionByChar(s.charAt(0));
        if (i == -1) {
            return;
        }
        manager.scrollToPositionWithOffset(i, 0);
    }

    @Override
    public void onNoChooseLetter() {

    }

    @Override
    public void initData(List<Friend> friends) {
        this.friends.clear();
        this.friends.addAll(friends);

        Collections.sort(friends);
        adapter.notifyDataSetChanged();
        hintSideBar.setOnChooseLetterChangedListener(this);
    }

    @Override
    public void setNotice(int type, int num) {
        switch (type) {
            case 0: //新朋友
                if (num == 0) {
                    ll_dot_new_friend.setVisibility(View.GONE);
                } else if (num < 100) {
                    ll_dot_new_friend.setVisibility(View.VISIBLE);
                    tv_dot_new_friend.setText(String.valueOf(num));
                } else {
                    ll_dot_new_friend.setVisibility(View.VISIBLE);
                    tv_dot_new_friend.setText(String.valueOf("99+"));
                }
                break;
            case 1:
                if (num == 0) {
                    ll_dot_group_chat.setVisibility(View.GONE);
                } else if (num < 100) {
                    ll_dot_group_chat.setVisibility(View.VISIBLE);
                    tv_dot_group_chat.setText(String.valueOf(num));
                } else {
                    ll_dot_group_chat.setVisibility(View.VISIBLE);
                    tv_dot_group_chat.setText(String.valueOf("99+"));
                }
                break;
        }
    }

    @Override
    public void onCLick(int position) {
        if (ListUtil.isEmpty(friends))
            return;
        Friend friend = friends.get(position);
        if (friend == null) {
            return;
        }

        FriendDataActivity.startAction(this.getActivity(), friend, 0);

    }

    @Override
    public void notifyMessage(byte type) {
        switch (type) {
            case ProtocolHeader.ADD_FRIEND:
                mPresenter.updateNewFriend();
                break;
            case ProtocolHeader.Add_New_Friend_Info:
                mPresenter.refresh();
                break;
        }
    }
}