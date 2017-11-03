package com.zhang.chat.corelib.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import java.util.List;

import com.zhang.chat.corelib.animation.AlphaInAnimation;
import com.zhang.chat.corelib.animation.BaseAnimation;
import com.zhang.chat.corelib.bean.PageBean;

/**
 * des:基础ReclyerView适配器
 * Created by xsf
 * on 2016.04.17:03
 */
public abstract class BaseReclyerViewAdapter<T,M extends ViewHolder> extends RecyclerView.Adapter<M> {

    protected Context mContext;
    protected List<T> data;
    //动画
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();

    protected PageBean pageBean;

    public BaseReclyerViewAdapter(Context context, List<T> data) {
        mContext = context;
        this.data = data;
        pageBean = new PageBean();
    }

    public void setData(List<T> d) {
        this.data = d;
    }

    @Override
    public abstract M onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void BindViewHolder(M holder, int position);

    @Override
    public void onBindViewHolder(M holder, int position) {
        //添加动画
        addAnimation((ViewHolder)holder);
        BindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 添加动画
     *
     * @param holder
     */
    public void addAnimation(ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    /**
     * 开始动画
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * 设置动画
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

    /**
     * 关闭动画
     */
    public void closeLoadAnimation() {
        this.mOpenAnimationEnable = false;
    }

    //--------- 封装基础数据操作 Begin -----------------------------------------------------


    public List<T> getData() {
        return data;
    }

    public T get(int position) {
        return data.get(position);
    }

    /**
     * 添加
     *
     * @param elem
     */
    public void add(T elem) {
        if (elem == null) return;
        data.add(elem);
        notifyItemChanged(data.size() - 1);
    }

    /**
     * 添加
     *
     * @param location
     * @param elem
     */
    public void add(int location, T elem) {
        if (elem == null) return;
        data.add(location, elem);
        notifyDataSetChanged();

    }

    /**
     * 重置数据源
     *
     * @param elems
     */
    public void reset(List<T> elems) {
        if (elems == null) return;
        data.clear();
        data.addAll(elems);
        notifyDataSetChanged();
    }

    /**
     * 插入列表
     *
     * @param location
     * @param elems
     */
    public void addAll(int location, List<T> elems) {
        if (elems == null) return;
        data.addAll(location, elems);
        notifyItemRangeChanged(location, elems.size());
    }

    /**
     * 添加列表
     *
     * @param elems
     */
    public void addAll(List<T> elems) {
        if (elems == null) return;
        int positionStart = data.size();
        data.addAll(elems);
        notifyItemRangeChanged(positionStart + 1, elems.size());
    }

    /**
     * 移除数据
     *
     * @param elem
     */
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    /**
     * 移除数据
     *
     * @param position
     */
    public void remove(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 清理列表
     */
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * 数据大小
     *
     * @return
     */
    public int size() {
        return data == null ? 0 : data.size();
    }

    /**
     * 数据为空
     *
     * @return
     */
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    /**
     * 分页
     *
     * @return
     */
    public PageBean getPageBean() {
        return pageBean;
    }


}
