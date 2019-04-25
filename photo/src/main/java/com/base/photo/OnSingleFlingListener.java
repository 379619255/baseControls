package com.base.photo;

import android.view.MotionEvent;

/**
 * @author : cuu
 * date    : 2019/4
 * desc    :
 */
public interface OnSingleFlingListener {
    //一个回调函数，用于接收用户在ImageView上的位置。如果用户在视图上的任何位置移动，您将收到一个回调。
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

}
