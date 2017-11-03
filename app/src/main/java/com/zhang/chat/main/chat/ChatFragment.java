package com.zhang.chat.main.chat;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseFragment;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.corelib.recycleview.IRecyclerView;
import com.zhang.chat.main.adapter.ChatFragmentAdapter;
import com.zhang.chat.main.chat.activity.ChatActivity;
import com.zhang.chat.main.chat.contract.ChatFragmentContract;
import com.zhang.chat.main.chat.model.ChatFragmentModel;
import com.zhang.chat.main.chat.presenter.ChatFragmentPresenter;
import com.zhang.chat.netty.protocol.ProtocolHeader;

/**
 * Created by 张俨 on 2017/9/11.
 * <p>
 * 聊天记录
 */

public class ChatFragment extends BaseFragment<ChatFragmentPresenter, ChatFragmentModel> implements ChatFragmentContract.View {
    @BindView(R.id.recycle_view)
    IRecyclerView recycleView;
    private ArrayList<MessageList> messages;
    private ChatFragmentAdapter adapter;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        messages = new ArrayList<>();
        adapter = new ChatFragmentAdapter(getContext(), messages);
        recycleView.setAdapter(adapter);
        mPresenter.setList(messages);
        adapter.setOnClickListener(position -> {
            if (mPresenter.getFriend(messages.get(position).getFriendID()) != null)
                mPresenter.update(messages.get(position));
                ChatActivity.startAction(getActivity(), mPresenter.getFriend(messages.get(position).getFriendID()));
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }


    @Override
    public void notifyList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refresh();
    }

    @Override
    public void notifyMessage(byte type) {
        switch (type) {
            case ProtocolHeader.PERSON_MESSAGE:
                mPresenter.refresh();
                break;
        }
    }
}
