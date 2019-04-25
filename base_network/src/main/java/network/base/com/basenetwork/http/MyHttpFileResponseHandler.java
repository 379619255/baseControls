package network.base.com.basenetwork.http;

import network.base.com.basenetwork.tools.LogUtils;

/**
 * 网络请求文件下载 Response
 *
 * @author cuu
 */
public abstract class MyHttpFileResponseHandler {
    /**
     * 成功返回
     *
     * @param statusCode 状态码
     */
    public abstract void onSuccess(int statusCode);

    /**
     * 返回失败
     *
     * @param statusCode 状态码
     * @param error_msg  错误信息
     */
    public abstract void onFailure(int statusCode, String error_msg);

    /**
     * 下载进度
     *
     * @param bytesWritten 已经长传字节
     * @param totalSize    总字节
     */
    public abstract void onProgress(long bytesWritten, long totalSize);

    public void onCancel() {
        LogUtils.d("MyHttpFileResponseHandler onCancel");
    }
}
