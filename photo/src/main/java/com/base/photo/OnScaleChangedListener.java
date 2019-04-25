package com.base.photo;

/**
 * @author : cuu
 * date    : 2019/4/10
 * desc    :
 */
public interface OnScaleChangedListener {
    /***
     * 当缩放发生变化时的回调
     * @param scaleFactor
     * @param focusX
     * @param focusY
     */
    void onScaleChange(float scaleFactor, float focusX, float focusY);
}
