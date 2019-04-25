package network.base.com.basedialog.picker.common.entity;

import java.util.List;

/**
 * @author : cuu
 * date    : 2019/4/8上午10:55
 * desc    : 用于联动选择器展示的第二级条目
 */
public interface LinkageSecond<Trd> extends LinkageItem {
    List<Trd> getThirds();
}
