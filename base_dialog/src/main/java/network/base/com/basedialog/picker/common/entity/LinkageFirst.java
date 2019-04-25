package network.base.com.basedialog.picker.common.entity;

import java.util.List;

/**
 * @author : cuu
 * date    : 2019/4/8上午10:53
 * desc    : 用于联动选择器展示的第一级条目
 */
public interface LinkageFirst<Snd> extends LinkageItem {
    List<Snd> getSeconds();
}
