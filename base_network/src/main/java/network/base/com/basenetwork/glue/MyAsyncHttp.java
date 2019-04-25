package network.base.com.basenetwork.glue;

import android.content.Context;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import network.base.com.basenetwork.http.MyHttpFileResponseHandler;
import network.base.com.basenetwork.http.MyHttpJsonResponseHandler;
import network.base.com.basenetwork.http.httplib.LibAsyncHttp;

/**
 * android-async-http 胶水层
 * @author cuu
 */
public class MyAsyncHttp {
    /**
     * android-async-http框架的post请求
     * @param context  当前的context
     * @param url      连接
     * @param params   数据
     * @param responseHandler
     */
    public static void doLibAsyncHttpPost(Context context, String url, Map<String, String> params, final MyHttpJsonResponseHandler responseHandler){
        RequestParams rparams = new RequestParams(params);

        LibAsyncHttp.post(context, url, rparams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                responseHandler.onSuccess(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseHandler.onFailure(statusCode, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                responseHandler.onFailure(statusCode, errorResponse + "");
            }

            @Override
            public void onCancel() {
                responseHandler.onCancel();
            }
        });
    }

    /**
     * android-async-http框架的get请求
     * @param context
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void doLibAsyncHttpGet(Context context, String url, Map<String, String>params, final MyHttpJsonResponseHandler responseHandler) {
        //android-async-http
        RequestParams rparams = new RequestParams(params);

        LibAsyncHttp.get(context, url, rparams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                responseHandler.onSuccess(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseHandler.onFailure(statusCode, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                responseHandler.onFailure(statusCode, errorResponse + "");
            }

            @Override
            public void onCancel() {
                responseHandler.onCancel();
            }
        });
    }

    /**
     * 调用async-http 上传文件
     * @param context 当前context
     * @param url
     * @param files
     * @param responseHandler
     */
    public static void doLibAsyncHttpUpload(Context context, String url, Map<String, File>files, final MyHttpFileResponseHandler responseHandler) {
        RequestParams rparams = new RequestParams();

        try {
            if (files != null && files.size() > 0) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    rparams.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            responseHandler.onFailure(0, "FileNotFoundException");
            return;
        }

        LibAsyncHttp.post(context, url, rparams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                responseHandler.onSuccess(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseHandler.onFailure(statusCode, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                responseHandler.onFailure(statusCode, errorResponse + "");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                responseHandler.onProgress(bytesWritten, totalSize);
            }

            @Override
            public void onCancel() {
                responseHandler.onCancel();
            }
        });
    }

    /**
     * 调用async-http 下载文件
     * @param context 当前context
     * @param url
     * @param target 下载后存放file
     * @param responseHandler
     */
    public static void doLibAsyncHttpDownload(Context context, String url, File target, final MyHttpFileResponseHandler responseHandler) {
        LibAsyncHttp.get(context, url, null, new FileAsyncHttpResponseHandler(target) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                responseHandler.onFailure(statusCode, "download error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                responseHandler.onSuccess(statusCode);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                responseHandler.onProgress(bytesWritten, totalSize);
            }

            @Override
            public void onCancel() {
                responseHandler.onCancel();
            }
        });
    }

    /**
     * 取消当前context下发起的所有请求
     * @param context 当前context
     */
    public static void doLibAsyncHttpCacel(Context context) {
        LibAsyncHttp.cancelRequest(context);
    }
}
