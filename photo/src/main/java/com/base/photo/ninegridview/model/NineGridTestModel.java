package com.base.photo.ninegridview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : cuu
 * date    : 2019/4/16
 * desc    : model
 */
public class NineGridTestModel implements Serializable {
    private static final long serialVersionUID = 2189052605715370758L;

    public List<String> urlList = new ArrayList<>();

    public boolean isShowAll = false;
}
