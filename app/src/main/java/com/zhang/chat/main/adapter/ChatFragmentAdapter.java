package com.zhang.chat.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhang.chat.R;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.corelib.adapter.BaseReclyerViewAdapter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.LoadImage;

/**
 * Created by 张俨 on 2017/10/13.
 */

public class ChatFragmentAdapter extends BaseReclyerViewAdapter<MessageList, ChatFragmentAdapter.ViewHolder> {


    public ChatFragmentAdapter(Context context, List<MessageList> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chat_wx, parent, false));
    }

    @Override
    public void BindViewHolder(ViewHolder holder, int position) {
        if (ListUtil.isEmpty(data) || data.get(position) == null) return;

        MessageList message = data.get(position);
        LoadImage.loadFromUserImageFace(mContext, holder.ivImgFace, String.valueOf(UrlConstant.BASE_FILE_URL + message.getFriend_img_face()));

        holder.tvName.setText(message.getFriend_Name());
        holder.tvContent.setText(message.getM_PostMessages());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onCLick(position);
                }
            }
        });
        setDotNum(holder, message);
    }

    private void setDotNum(ViewHolder holder, MessageList message) {
        int newNumber = message.getNewNumber();
        if (newNumber < 0) {
            holder.llDotChat2.setVisibility(View.VISIBLE);
            holder.llDotChat.setVisibility(View.GONE);
        } else if (newNumber == 0) {
            holder.llDotChat2.setVisibility(View.GONE);
            holder.llDotChat.setVisibility(View.GONE);
        } else if (newNumber < 100) {
            holder.llDotChat2.setVisibility(View.GONE);
            holder.llDotChat.setVisibility(View.VISIBLE);
            holder.tvDotChat.setText(String.valueOf(newNumber));
        } else {
            holder.llDotChat2.setVisibility(View.GONE);
            holder.llDotChat.setVisibility(View.VISIBLE);
            holder.tvDotChat.setText(String.valueOf("99+"));
        }

    }

    private ContactAdapter.OnClickListener onClickListener;

    public void setOnClickListener(ContactAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onCLick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_img_face)
        ImageView ivImgFace;
        @BindView(R.id.tv_dot_chat)
        TextView tvDotChat;
        @BindView(R.id.ll_dot_chat)
        LinearLayout llDotChat;
        @BindView(R.id.tv_dot_chat_2)
        TextView tvDotChat2;
        @BindView(R.id.ll_dot_chat_2)
        LinearLayout llDotChat2;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            ButterKnife.bind(this, itemView);
        }
    }
}
