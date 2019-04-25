package network.base.com.basenetwork.http.httplib;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import network.base.com.basenetwork.GlobalApp;
import network.base.com.basenetwork.tools.HTTPSTrustManager;

/**
 * volley封装
 *
 * @author cuu
 */
public class LibVolley {
    private static RequestQueue mRequestQueue;

    /**
     * post 方法
     *
     * @param context      当前context
     * @param url          连接
     * @param headers      头
     * @param params       数据
     * @param listener
     * @param err_listener
     */
    public static void post(Context context, String url, Map<String, String> headers, Map<String, String> params, Response.Listener<LibVolleyResponseModel> listener, Response.ErrorListener err_listener) {
        if (mRequestQueue == null) {
            //允许https的ssl
            HTTPSTrustManager.allowAllSSL();
            mRequestQueue = Volley.newRequestQueue(GlobalApp.getInstance().getContext());
        }

        LibVolleyJSONObjectRequest request = new LibVolleyJSONObjectRequest(Request.Method.POST, url
                , headers, params, listener, err_listener);

        request.setTag(context);
        mRequestQueue.add(request);
    }

    /**
     * get方法
     *
     * @param context      当前的context
     * @param url          url
     * @param headers      头
     * @param params       数据
     * @param listener
     * @param err_listener
     */
    public static void get(Context context, String url, Map<String, String> headers, Map<String, String> params, Response.Listener<LibVolleyResponseModel> listener, Response.ErrorListener err_listener) {
        if (mRequestQueue == null) {
            HTTPSTrustManager.allowAllSSL();
            mRequestQueue = Volley.newRequestQueue(GlobalApp.getInstance().getContext());
        }

        LibVolleyJSONObjectRequest request = new LibVolleyJSONObjectRequest(Request.Method.GET, url
                , headers, params, listener, err_listener);

        request.setTag(context);
        mRequestQueue.add(request);
    }

    /**
     * 取消当前context的所有请求
     *
     * @param context 当前所在context
     */
    public static void cancelRequest(Context context) {
        if (mRequestQueue != null && context != null) {
            mRequestQueue.cancelAll(context);
        }
    }
}
