package com.base.photo;

import android.graphics.RectF;

/**
 * @author : cuu
 * date    : 2019/4
 * desc    : 矩阵变换监听器
 */
public interface OnMatrixChangedListener {
    /**
     * 矩阵的改变
     * @param rect
     */
    void onMatrixChanged(RectF rect);

}
