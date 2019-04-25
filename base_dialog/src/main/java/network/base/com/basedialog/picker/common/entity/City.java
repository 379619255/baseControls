package network.base.com.basedialog.picker.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : cuu
 * date    : 2019/4/8上午10:54
 * desc    :
 */
public class City extends Area implements LinkageSecond<County> {
    private String provinceId;
    private List<County> counties = new ArrayList<>();

    public City() {
        super();
    }

    public City(String areaName) {
        super(areaName);
    }

    public City(String areaId, String areaName) {
        super(areaId, areaName);
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    @Override
    public List<County> getThirds() {
        return counties;
    }
}
