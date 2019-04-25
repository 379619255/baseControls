package com.base.permissions;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : cuu
 * date    : 2019/4
 * desc    : 危险权限的请求类
 */
public final class DangerousPermissions {
    private Activity mActivity;
    private List<String> mPermissions;
    private boolean mConstant;
    /**构造*/
    private DangerousPermissions(Activity activity) {
        mActivity = activity;
    }

    /**
     * 设置请求对象
     */
    public static DangerousPermissions with(Activity activity){
        //生命私有有的类
        return new DangerousPermissions(activity);
    }

    /**
     * 设置权限组
     * @param permissions
     * @return
     */
    public DangerousPermissions permission(String... permissions){
        if (mPermissions == null){
            mPermissions = new ArrayList<>(permissions.length);
        }
        mPermissions.addAll(Arrays.asList(permissions));
        return this;
    }
    /**
     * 设置权限组
     */
    public DangerousPermissions permission(String[]... permissions){
        if (mPermissions == null) {
            int length = 0;
            for (String[] permission : permissions) {
                length += permission.length;
            }
            //开辟出相应大小的空间
            mPermissions = new ArrayList<>(length);
        }
        for (String[] group : permissions) {
            mPermissions.addAll(Arrays.asList(group));
        }
        return this;
    }

    /**
     * 设置权限组
     */
    public DangerousPermissions permission(List<String> permissions) {
        if (mPermissions == null) {
            mPermissions = permissions;
        }else {
            mPermissions.addAll(permissions);
        }
        return this;
    }
    /**
     * 被拒绝后继续申请，知道授权或者永久拒绝
     */
    public DangerousPermissions constantRequest(){
        mConstant = true;
        return this;
    }


    /**
     * 请求的权限
     * @param call
     */
    public void request(OnPermission call) {
        if (mPermissions == null || mPermissions.isEmpty()){
            mPermissions = PermissionUtils.getManifestPermissions(mActivity);
        }
        if (mPermissions == null || mPermissions.isEmpty()){
            throw new IllegalArgumentException("The requested permission cannot be empty");
        }
        if (mActivity == null) {
            throw new IllegalArgumentException("The activity is empty");
        }
        if (call == null) {
            throw new IllegalArgumentException("The permission request callback interface must be implemented");
        }
        //检测目前权限
        PermissionUtils.checkTargetSdkVersion(mActivity,mPermissions);
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(mActivity,mPermissions);

        if (failPermissions == null || failPermissions.isEmpty()) {
            // 证明权限已经全部授予过
            call.hasPermission(mPermissions, true);
        } else {
            // 检测权限有没有在清单文件中注册
            PermissionUtils.checkPermissions(mActivity, mPermissions);
            // 申请没有授予过的权限
            PermissionFragment.newInstance((new ArrayList<>(mPermissions)), mConstant).prepareRequest(mActivity, call);
        }
    }


    /**
     * 检查某些权限是否全部授予了
     *
     * @param context     上下文对象
     * @param permissions 需要请求的权限组
     */
    public static boolean isHasPermission(Context context, String... permissions) {
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(context, Arrays.asList(permissions));
        return failPermissions == null || failPermissions.isEmpty();
    }

    /**
     * 检查某些权限是否全部授予了
     *
     * @param context     上下文对象
     * @param permissions 需要请求的权限组
     */
    public static boolean isHasPermission(Context context, String[]... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : permissions) {
            permissionList.addAll(Arrays.asList(group));
        }
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(context, permissionList);
        return failPermissions == null || failPermissions.isEmpty();
    }


    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     */
    public static void gotoPermissionSettings(Context context) {
        PermissionSettingPage.start(context, false);
    }

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     * @param newTask 是否使用新的任务栈启动
     */
    public static void gotoPermissionSettings(Context context, boolean newTask) {
        PermissionSettingPage.start(context, newTask);
    }
}
