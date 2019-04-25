package network.base.com.basenetwork.http;

import org.json.JSONObject;

import network.base.com.basenetwork.tools.LogUtils;

/**
 * 回调  返回json
 *
 * @author cuu
 */
public abstract class MyHttpJsonResponseHandler {

    /**
     * 返回成功
     *
     * @param statusCode 状态码
     * @param jsonObject
     */
    public abstract void onSuccess(int statusCode, JSONObject jsonObject);

    /**
     * 返回失败
     *
     * @param statusCode
     * @param error_msg
     */
    public abstract void onFailure(int statusCode, String error_msg);

    /**
     * 请求被取消
     */
    public void onCancel() {
        LogUtils.d("MyHttpJsonResponseHandler onCancel");
    }
}
