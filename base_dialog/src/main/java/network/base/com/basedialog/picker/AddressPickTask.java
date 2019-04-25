package network.base.com.basedialog.picker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import network.base.com.basedialog.picker.common.AddressPicker;
import network.base.com.basedialog.picker.common.entity.Province;
import network.base.com.basedialog.picker.common.util.ConvertUtils;

/**
 * @author : cuu
 * date    : 2019/4/8
 * desc    : 获取地址数据选择器
 */
public class AddressPickTask extends AsyncTask<String, Void, ArrayList<Province>> {
    private WeakReference<Activity> activityReference;
    private ProgressDialog dialog;
    private Callback callback;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideProvince = false;
    private boolean hideCounty = false;

    public AddressPickTask(Activity activity) {
        this.activityReference = new WeakReference<>(activity);
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    //这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框
    @Override
    protected void onPreExecute() {
        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }
    //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用
    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    selectedCounty = params[2];
                    break;
                default:
                    break;
            }
        }
        ArrayList<Province> data = new ArrayList<>();
        try {
            Activity activity = activityReference.get();
            if (activity != null) {
                String json = ConvertUtils.toString(activity.getAssets().open("city.json"));
                data.addAll(JSON.parseArray(json, Province.class));
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground 得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
    @Override
    protected void onPostExecute(ArrayList<Province> result) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (result.size() > 0) {
            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }
            AddressPicker picker = new AddressPicker(activity, result);
            picker.setHideProvince(hideProvince);
            picker.setHideCounty(hideCounty);
            if (hideCounty) {
                //将屏幕分为3份，省级和地级的比例为1:2
                picker.setColumnWeight(1 / 3.0f, 2 / 3.0f);
            } else {
                //省级、地级和县级的比例为2:3:3
                picker.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f);
            }
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            picker.setOnAddressPickListener(callback);
            picker.show();
        } else {
            callback.onAddressInitFailed();
        }
    }

    public interface Callback extends AddressPicker.OnAddressPickListener {

        void onAddressInitFailed();

    }
}
