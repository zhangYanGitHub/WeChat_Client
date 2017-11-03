package com.zhang.chat.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhang.chat.R;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.corelib.adapter.BaseReclyerViewAdapter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.LoadImage;
import com.zhang.chat.utils.ShareUtil;
import com.zhang.chat.utils.TimeUtils;

/**
 * Created by 张俨 on 2017/9/27.
 */

public class ChatAdapter extends BaseReclyerViewAdapter<Message, ChatAdapter.ViewHolder> {

    private static final int OTHER_MSG = 1;
    private static final int MINE_MSG = 2;
    private Friend friend;
    private User user;

    public ChatAdapter(Context context, List<Message> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MINE_MSG:
                return new MineViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_mine, parent, false));
            case OTHER_MSG:
                return new OtherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_other, parent, false));
            default:
                return null;
        }

    }

    @Override
    public int getItemViewType(int position) {
        long m_toUserID = data.get(position).getM_ToUserID();
        boolean isMine = (m_toUserID == Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME)));
        return isMine ? MINE_MSG : OTHER_MSG;
    }

    @Override
    public void BindViewHolder(ViewHolder holder, int position) {
        if (ListUtil.isEmpty(data) || data.get(position) == null) return;
        int viewType = getItemViewType(position);

        Message message = data.get(position);
        if (viewType == MINE_MSG) {
            setMineValue((MineViewHolder) holder, message);

        } else {
            setOtherValue((OtherViewHolder) holder, message);
        }
    }

    private void setMineValue(MineViewHolder holder, Message message) {
        MineViewHolder mineViewHolder = holder;
        LoadImage.loadFromUserImageFace(mContext, mineViewHolder.ivMineFace,
                String.valueOf(UrlConstant.BASE_FILE_URL + user.getUser_img_face_path()));
        String timeFromLong = TimeUtils.getTimeFromLong(Long.parseLong(message.getM_Time()));
        mineViewHolder.tvTime.setText(timeFromLong);
        switch (message.getM_MessagesTypeID()) {
            case 1: //文本消息
                mineViewHolder.tvMessageContent.setText(message.getM_PostMessages());
                break;
            case 2:
                break;
            case 3:
                break;
        }
        //  1 发送中 2  发送成功 3  发送失败
        switch (message.getM_status()) {
            case 1:
                mineViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
            case 2:
                mineViewHolder.progressBar.setVisibility(View.GONE);
                break;
            case 3:
                break;
        }
    }

    private void setOtherValue(OtherViewHolder holder, Message message) {
        OtherViewHolder otherViewHolder = holder;
        LoadImage.loadFromUserImageFace(mContext, otherViewHolder.ivMineFace, String.valueOf(UrlConstant.BASE_FILE_URL + friend.getUser_img_face_path()));
        otherViewHolder.tvTime.setText(TimeUtils.getTimeFromLong(Long.parseLong(message.getM_Time())));
        switch (message.getM_MessagesTypeID()) {
            case 1: //文本消息
                otherViewHolder.tvMessageContent.setText(message.getM_PostMessages());
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public void setFriendUserInfo(Friend friend, User user) {

        this.friend = friend;
        this.user = user;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 我所发的消息
     */
    public class MineViewHolder extends ViewHolder {

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_time)
        LinearLayout llTime;
        @BindView(R.id.iv_mine_face)
        ImageView ivMineFace;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.iv_mine_image)
        ImageView ivMineImage;

        public MineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 别人所发的消息
     */
    public class OtherViewHolder extends ViewHolder {

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_time)
        LinearLayout llTime;
        @BindView(R.id.iv_mine_face)
        ImageView ivMineFace;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.iv_mine_image)
        ImageView ivMineImage;

        public OtherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
