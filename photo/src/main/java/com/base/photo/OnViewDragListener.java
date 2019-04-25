package com.base.photo;

/**
 * @author : cuu
 * date    : 2019/4/10
 * desc    :
 */
public interface OnViewDragListener {
    //回调时，照片正在经历一个拖动事件。当用户缩放时，不能调用此函数。
    void onDrag(float dx, float dy);
}
