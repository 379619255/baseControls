package network.base.com.basedialog.picker.common.entity;

import android.text.TextUtils;

/**
 * @author : cuu
 * date    : 2019/4/8
 * desc    : 省市县抽象，为了使用FastJson及LiteOrm之类的库，本类及其子类不可混淆
 */
public abstract class Area extends JavaBean implements LinkageItem {
    private String areaId;
    private String areaName;

    public Area() {
        super();
    }

    public Area(String areaName) {
        this.areaId = "";
        this.areaName = areaName;
    }

    public Area(String areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public Object getId() {
        return areaId;
    }

    @Override
    public String getName() {
        return areaName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Area)) {
            return false;
        }
        Area obj1 = (Area) obj;
        if (!TextUtils.isEmpty(areaId)) {
            return areaId.equals(obj1.getAreaId());
        }
        return areaName.equals(obj1.getAreaName());
    }

    @Override
    public String toString() {
        return "areaId=" + areaId + ",areaName=" + areaName;
    }
}
