package com.base.photo;

import android.view.View;

/**
 * @author : cuu
 * date    : 2019/4/10
 * desc    :
 */
public interface OnViewTapListener {
    //一个回调函数，用于接收用户点击ImageView的位置。如果用户点击视图上的任何地方，你将收到一个回调，点击“空白”将不会被忽略。
    void onViewTap(View view, float x, float y);
}
