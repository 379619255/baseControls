package network.base.com.basenetwork.http.httplib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import network.base.com.basenetwork.GlobalApp;

/**
 * 对AndroidAsyncHttp库的使用封装
 * @author cuu
 */
public class LibAsyncHttp {
    private static AsyncHttpClient client;

    /**
     * post
     * @param context 当前所在context
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if(client == null) {
            client = new AsyncHttpClient();
            client.setUserAgent(getAgaent());
        }

        //cookie功能
        PersistentCookieStore cookieStore = new PersistentCookieStore(GlobalApp.getInstance().getContext());
        client.setCookieStore(cookieStore);

        if(context != null) {
            client.post(context, url, params, responseHandler);
        } else {
            client.post(url, params, responseHandler);
        }
    }

    /**
     * get
     * @param context 当前所在context
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if(client == null) {
            client = new AsyncHttpClient();
            client.setUserAgent(getAgaent());
        }

        if(context != null) {
            client.get(context, url, params, responseHandler);
        } else {
            client.get(url, params, responseHandler);
        }
    }

    /**
     * 取消当前context的所有请求
     * @param context 当前所在context
     */
    public static void cancelRequest(Context context) {

        if(client != null && context != null) {
            client.cancelRequests(context, true);
        }
    }


    private static String getAgaent() {
        String userAgent = "androidhttp/0";
        try {
            String packageName = GlobalApp.getInstance().getContext().getPackageName();
            PackageInfo info = GlobalApp.getInstance().getContext().getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return userAgent;
    }

}
