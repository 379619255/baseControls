package network.base.com.basenetwork.glue;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import network.base.com.basenetwork.GlobalApp;
import network.base.com.basenetwork.http.MyHttpJsonResponseHandler;
import network.base.com.basenetwork.http.httplib.LibVolley;
import network.base.com.basenetwork.http.httplib.LibVolleyResponseModel;
import network.base.com.basenetwork.tools.SharedPreferenceUtils;

/**
 * volley调用的中间层
 * @author cuu
 */
public class MyVolley {
    /**
     * post 请求
     * @param context 当前上下文
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void doLibVolleyPost(Context context, String url, Map<String, String> params, final MyHttpJsonResponseHandler responseHandler){
        Map<String, String> headers = new HashMap<String, String>();

        //读取cookie放入header
        String cookie = SharedPreferenceUtils.readSharedPreferences(GlobalApp.getInstance().getContext(), "cookie");
        headers.put("Cookie", cookie);

        LibVolley.post(context, url, headers, params, new Response.Listener<LibVolleyResponseModel>() {
            @Override
            public void onResponse(LibVolleyResponseModel response) {
                //将cookie保存
                String cookie = response.headers.get("Set-Cookie");
                SharedPreferenceUtils.saveSharedPreferences(GlobalApp.getInstance().getContext(), "cookie", cookie);
                responseHandler.onSuccess(response.status, response.data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseHandler.onFailure(0, error.getMessage());
            }
        });
    }

    /**
     * get 请求中间层
     * @param context
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void doLibVolleyGet(Context context, String url, Map<String, String> params, final MyHttpJsonResponseHandler responseHandler){
        Map<String, String> headers = new HashMap<String, String>();

        LibVolley.get(context, url, headers, params, new Response.Listener<LibVolleyResponseModel>() {
            @Override
            public void onResponse(LibVolleyResponseModel response) {
                responseHandler.onSuccess(response.status, response.data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseHandler.onFailure(0, error.getMessage());
            }
        });
    }

    /**
     * 取消请求
     * @param context 当前上下文
     */
    public static void doLibVolleyCancel(Context context) {
        LibVolley.cancelRequest(context);
    }
}
