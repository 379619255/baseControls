package network.base.com.basenetwork.http;

import android.content.Context;

import java.io.File;
import java.util.Map;

import network.base.com.basenetwork.glue.MyAsyncHttp;
import network.base.com.basenetwork.glue.MyVolley;

/**
 * 网络请求的统一调用
 *
 * @author cuu
 */
public class MyHttp {
    /**
     * post 文本参数  返回json
     *
     * @param context         当前context
     * @param url             连接
     * @param params          参数
     * @param responseHandler
     */
    public static void doPost(Context context, String url, Map<String, String> params, final MyHttpJsonResponseHandler responseHandler) {
        MyVolley.doLibVolleyPost(context, url, params, responseHandler);
        //android-async-http
        //MyAsyncHttp.doLibAsyncHttpPost(context, url, params, responseHandler);
    }

    /**
     * get请求 返回json
     *
     * @param context         当前context
     * @param url             网络连接
     * @param params          参数
     * @param responseHandler
     */
    public static void doGet(Context context, String url, Map<String, String> params, final MyHttpJsonResponseHandler responseHandler) {
        MyVolley.doLibVolleyGet(context, url, params, responseHandler);
        //android-async-http
        //MyAsyncHttp.doLibAsyncHttpGet(context, url, params, responseHandler);
    }

    /**
     * 上传文件
     *
     * @param context         当前context
     * @param url             网络连接
     * @param files           key-file方式
     * @param responseHandler
     */
    public static void doUpload(Context context, String url, Map<String, File> files, final MyHttpFileResponseHandler responseHandler) {
        //android-async-http
        MyAsyncHttp.doLibAsyncHttpUpload(context, url, files, responseHandler);
    }

    /**
     * 下载文件
     *
     * @param context         当前context
     * @param url             网络连接
     * @param target          下载的file
     * @param responseHandler
     */
    public static void doDownload(Context context, String url, File target, final MyHttpFileResponseHandler responseHandler) {
        //android-async-http
        MyAsyncHttp.doLibAsyncHttpDownload(context, url, target, responseHandler);
    }

    /**
     * 取消当前context的所有请求
     *
     * @param context 当前的context
     */
    public static void cancelRequest(Context context) {
        MyVolley.doLibVolleyCancel(context);

        //android-async-http
        MyAsyncHttp.doLibAsyncHttpCacel(context);
    }
}
