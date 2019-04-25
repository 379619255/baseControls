package com.base.photo;

/**
 * @author : cuu
 * date    : 2019/4
 * desc    : 手势监听
 */
public interface OnGestureListener {
    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);
}
