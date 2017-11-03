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
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.adapter.BaseReclyerViewAdapter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.LoadImage;

/**
 * Created by 张俨 on 2017/9/27.
 */

public class SearchFriendAdapter extends BaseReclyerViewAdapter<User, SearchFriendAdapter.ViewHolder> {


    public SearchFriendAdapter(Context context, List<User> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_firend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void BindViewHolder(ViewHolder holder, int position) {
        if (ListUtil.isEmpty(data) || data.get(position) == null) return;
        User user = data.get(position);
        holder.tvName.setText(user.getUser_name());
        LoadImage.loadFromUserImageFace(mContext, holder.ivFace, String.valueOf(UrlConstant.BASE_FILE_URL + user.getUser_img_face_path()));

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                onClickListener.onClick(position);
            });
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_face)
        ImageView ivFace;
        @BindView(R.id.tv_name)
        TextView tvName;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
