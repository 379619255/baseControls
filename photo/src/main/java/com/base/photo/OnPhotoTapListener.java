package com.base.photo;

import android.widget.ImageView;

/**
 * @author : cuu
 * date    : 2019/4/10
 * desc    :
 */
public interface OnPhotoTapListener {
    /**
     * 一个回调函数，用于接收用户点击照片的位置。只有当用户点击实际照片时，你才会收到一个回调，点击“空白”将被忽略。
     * @param view
     * @param x
     * @param y
     */
    void onPhotoTap(ImageView view, float x, float y);
}
