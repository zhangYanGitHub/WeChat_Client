package com.zhang.chat.main.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.corelib.recycleview.IRecyclerView;
import com.zhang.chat.main.adapter.ChatAdapter;
import com.zhang.chat.main.chat.contract.ChatContract;
import com.zhang.chat.main.chat.model.ChatModel;
import com.zhang.chat.main.chat.presenter.ChatPresenter;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.view.CustomSubTitleBar;

public class ChatActivity extends BaseActivity<ChatPresenter, ChatModel>
        implements ChatContract.View {

    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.recycle_view)
    IRecyclerView recycleView;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.et_reply)
    EditText etReply;
    @BindView(R.id.iv_expression)
    ImageView ivExpression;
    @BindView(R.id.iv_add_more)
    ImageView ivAddMore;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    private Friend friend;
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatAdapter chatAdapter;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, Friend friend) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra("friend", friend);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initPresenter() {
        friend = (Friend) getIntent().getSerializableExtra("friend");
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {

        if (friend == null) {
            finish();
            return;
        }
        titleBar.setTitleText(friend.getF_friend_type_id());
        etReply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    btnSend.setVisibility(View.VISIBLE);
                    ivAddMore.setVisibility(View.GONE);
                } else {
                    btnSend.setVisibility(View.GONE);
                    ivAddMore.setVisibility(View.VISIBLE);
                }
            }
        });
        chatAdapter = new ChatAdapter(this, messages);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(chatAdapter);
        chatAdapter.setFriendUserInfo(friend ,mPresenter.getUser());
        mPresenter.getMessage();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public Friend getFriend() {
        return friend;
    }

    @Override
    public void notify(Message message) {
        if (!messages.contains(message))
            messages.add(message);
        chatAdapter.notifyDataSetChanged();
        recycleView.scrollToPosition(messages.size() - 1);
    }

    @Override
    public void notify(List<Message> message) {
        messages.clear();
        messages.addAll(message);
        chatAdapter.notifyDataSetChanged();
        recycleView.scrollToPosition(messages.size() - 1);

    }

    @OnClick({R.id.iv_expression, R.id.iv_add_more, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_expression:
                break;
            case R.id.iv_add_more:
                break;
            case R.id.btn_send:
                String msg = etReply.getText().toString();
                etReply.setText(null);
                mPresenter.sendMessage(msg);
                break;
        }
    }

    @Override
    public void notifyMessage(byte type) {
        if (type == ProtocolHeader.PERSON_MESSAGE) {
            mPresenter.getResponse();
        }
    }
}
