package com.zhang.chat.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;

import com.zhang.chat.R;

/**
 * Created by junjun on 2017/7/25 0025.
 * Glide 加载图片
 */

public class LoadImage {
    /**
     * 加载网络正常图片
     *
     * @param imageView
     * @param url
     */
    public static void loadFromUserImageFace(Context context,ImageView imageView, Object url) {
        Glide.with(context).load(url) .asBitmap()
               .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.img_face).error(R.drawable.img_face).into(imageView);

    }

    public static void loadImageWidth(Activity activity ,ImageView imageView,String url){
        Glide.with(activity).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = DensityUtil.getScreenWidth(activity) * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                para.width = DensityUtil.getScreenWidth(activity);
                imageView.setImageBitmap(resource);
            }
        });
    }
    /**
     * 加载本地图片
     *
     * @param imageView
     * @param path
     */
    public static void loadFromLocal(Context context,ImageView imageView, String  path) {
        File file = new File(path);
        Glide.with(context).load(file).error(R.drawable.img_default).into(imageView);

    }
}
