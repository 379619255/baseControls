package network.base.com.basedialog.picker.common.entity;

import network.base.com.basedialog.picker.common.widget.WheelItem;

/**
 * @author : cuu
 * date    : 2019/4/8上午10:35
 * desc    : 用于联动选择器展示的条目
 */
public interface LinkageItem extends WheelItem {
    /**
     * 唯一标识，用于判断两个条目是否相同
     */
    Object getId();
}
