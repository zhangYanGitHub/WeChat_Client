package com.zhang.chat.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.zhang.chat.R;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.adapter.BaseReclyerViewAdapter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.LoadImage;
import com.zhang.chat.utils.ShareUtil;

/**
 * Created by 张俨 on 2017/10/20.
 * 新朋友
 */

public class NewFriendStateAdapter extends BaseReclyerViewAdapter<Verification, NewFriendStateAdapter.ViewHolder> {


    public NewFriendStateAdapter(Context context, List<Verification> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_new_firend_state, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void BindViewHolder(ViewHolder holder, int position) {
        if (ListUtil.isEmpty(data) || data.get(position) == null) return;
        Verification verification = data.get(position);
        LoadImage.loadFromUserImageFace(mContext, holder.ivFace, String.valueOf(UrlConstant.BASE_FILE_URL + verification.getFriend_img_face()));

        holder.tvName.setText(verification.getFriend_name());
        holder.tvMessage.setText(verification.getMessage());

        setState(holder, verification);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(position);
            }
        });

        holder.tvVerification.setOnClickListener(v -> {
            if (onItemStateClickListener != null) {
                onItemStateClickListener.onClick(position);
            }
        });

    }

    private long user_id = Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME));
    private OnItemClickListener onItemClickListener;
    private OnItemStateClickListener onItemStateClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemStateClickListener(OnItemStateClickListener onItemStateClickListener) {
        this.onItemStateClickListener = onItemStateClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnItemStateClickListener {
        void onClick(int position);
    }

    /**
     * @param holder
     */
    private void setState(ViewHolder holder, Verification verification) {
        int m_state = verification.getM_state();
        long user_friend_id = verification.getUser_friend_id();
        switch (m_state) { // 1 发起添加朋友请求  2 通过朋友验证 3 拒绝添加
            case 1:
                if (user_id == user_friend_id) {// 我是发起人
                    holder.tvVerification.setVisibility(View.GONE);
                    holder.tvAdd.setVisibility(View.VISIBLE);
                    holder.tvAdd.setText("等待验证");
                } else {
                    holder.tvVerification.setVisibility(View.VISIBLE);
                    holder.tvAdd.setVisibility(View.GONE);
                    holder.tvVerification.setText("接受");
                }

                break;
            case 2:
                holder.tvVerification.setVisibility(View.GONE);
                holder.tvAdd.setVisibility(View.VISIBLE);
                holder.tvAdd.setText("已添加");
                break;
            case 3:
                holder.tvVerification.setVisibility(View.GONE);
                holder.tvAdd.setVisibility(View.VISIBLE);
                holder.tvAdd.setText("已拒绝");
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_face)
        ImageView ivFace;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.tv_verification)
        TextView tvVerification;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
