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
import com.zhang.chat.bean.Friend;
import com.zhang.chat.corelib.adapter.BaseReclyerViewAdapter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.LoadImage;
import com.zhang.chat.utils.StrUtil;

/**
 * Created by 张俨 on 2017/9/12.
 */

public class ContactAdapter extends BaseReclyerViewAdapter<Friend, ContactAdapter.ViewHolder> {

    private final Context context;
    private List<Friend> userList;


    public ContactAdapter(Context context, List<Friend> data) {
        super(context, data);
        this.context = context;
        userList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void BindViewHolder(ViewHolder holder, int position) {
        if (ListUtil.isEmpty(userList)) return;
        Friend user = userList.get(position);
        if (user == null) return;
        holder.tvName.setText(StrUtil.isBlank(user.getUser_name()) ? "" : user.getUser_name());
        LoadImage.loadFromUserImageFace(context, holder.ivFace, UrlConstant.BASE_FILE_URL + user.getUser_img_face_path());
        int section = getFirstPositionByChar(user.getHeadLetter());
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == section) {
            holder.tvLvItemTag.setVisibility(View.VISIBLE);
            holder.tvLvItemTag.setText(userList.get(position).getHeadLetter() + "");
        } else {
            holder.tvLvItemTag.setVisibility(View.GONE);
        }

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                onClickListener.onCLick(position);
            });
        }
    }

    public int getFirstPositionByChar(char sign) {
        if (sign == '↑' || sign == '☆') {
            return 0;
        }
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getHeadLetter() == sign) {
                return i;
            }
        }
        return -1;
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onCLick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_face)
        ImageView ivFace;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_lv_item_tag)
        TextView tvLvItemTag;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
