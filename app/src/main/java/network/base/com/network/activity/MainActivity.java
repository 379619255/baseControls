package network.base.com.network.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.base.photo.ninegridview.RecyclerViewExampleActivity;
import com.base.photo.ninegridview.model.NineGridTestModel;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.base.com.basenetwork.http.MyHttp;
import network.base.com.basenetwork.http.MyHttpFileResponseHandler;
import network.base.com.basenetwork.http.MyHttpJsonResponseHandler;
import network.base.com.network.App;
import network.base.com.network.LoginUtil;
import network.base.com.network.MethodNet;
import network.base.com.network.R;

/**
 * 网络测试
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private Button do_post;
    private Button do_get;
    private Button doUpload;
    private Button doDownload;
    private Button user_grade;
    private Button toast;
    private Button picker;
    private Button alert;
    private Button permission;
    private Button photo;
    private Button nine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        do_post = findViewById(R.id.do_post);
        do_post.setOnClickListener(this);
        do_get = findViewById(R.id.do_get);
        do_get.setOnClickListener(this);
        doUpload = findViewById(R.id.doUpload);
        doUpload.setOnClickListener(this);
        doDownload = findViewById(R.id.doDownload);
        doDownload.setOnClickListener(this);
        toast = findViewById(R.id.toast);
        toast.setOnClickListener(this);
        picker = findViewById(R.id.picker);
        picker.setOnClickListener(this);
        alert = findViewById(R.id.alert);
        alert.setOnClickListener(this);
        permission = findViewById(R.id.permission);
        permission.setOnClickListener(this);
        photo = findViewById(R.id.photo);
        photo.setOnClickListener(this);
        nine = findViewById(R.id.nine);
        nine.setOnClickListener(this);
        user_grade = findViewById(R.id.user_grade);
        user_grade.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.do_post) {
            testData(0);
        } else if (i == R.id.do_get) {
            testData(1);
        } else if (i == R.id.doUpload) {
            doUpload();
        } else if (i == R.id.doDownload) {
            doDownload();
        }else if (i == R.id.toast) {
            startActivity(new Intent(MainActivity.this, ToastActivity.class));
        } else if (i == R.id.picker) {
            startActivity(new Intent(MainActivity.this, PickerActivity.class));
        }else if (i == R.id.alert){
            startActivity(new Intent(MainActivity.this,AlertActivity.class));
        }else if (i == R.id.permission){
            startActivity(new Intent(MainActivity.this,PermissionActivity.class));
        }else if (i == R.id.photo){
            startActivity(new Intent(MainActivity.this,PhotoViewZoom.class));
        }else if (i == R.id.nine){
            startActivity(new Intent(MainActivity.this,NineGirdActivity.class));

        }else if (i == R.id.user_grade){
            startActivity(new Intent(MainActivity.this,RoundedImageActvity.class));
        }
    }


    //页面销毁关闭请求 防止crash 建议放到BaseActivity BaseFragrament里面
    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyHttp.cancelRequest(this);     //页面退出 关闭所有请求
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void testData(int state) {
        //根据实际情况定
        String account = "账号@xxx";
        String passowrd = "密码";
        String user = account;
        if (!account.endsWith("@yixin.im")) {
            String[] s = account.split("@");
            user = s[0];
        }


        try {

            String data = LoginUtil.getEncyData(user, passowrd);

            Map<String, String> paramSys = MethodNet.loginData(account, data, "pwd", null);
            if (state == 0) {
                doPostHttp(MainActivity.this, App.Url, paramSys);
            } else if (state == 1) {
                doGetHttp(MainActivity.this, App.Url, paramSys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //post请求 http/https 测试ok
    private void doPostHttp(Context context, String url, Map<String, String> paramSys) {
        //文本参数
        Map<String, String> params = new HashMap<String, String>();
        params = paramSys;
        /*params.put("uid", "111");*/

        //post
        MyHttp.doPost(context, url, params, new MyHttpJsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                Log.i(TAG, "onSuccess status code=" + statusCode + " response=" + response);

                Toast.makeText(MainActivity.this, response+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.i(TAG, "onFailure status code=" + statusCode + " error_msg=" + error_msg);
                Toast.makeText(MainActivity.this, error_msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "request on cancel");
            }
        });
    }

    //get请求 http/https 测试ok
    private void doGetHttp(Context context, String url, Map<String, String> paramSys) {
        //文本参数
        Map<String, String> params = new HashMap<String, String>();
        /*params.put("uid", "111");*/
        params = paramSys;

        //get
        MyHttp.doGet(context, url, params, new MyHttpJsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                Log.i(TAG, "onSuccess status code=" + statusCode + " response=" + response);
                Toast.makeText(MainActivity.this, response+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.i(TAG, "onFailure status code=" + statusCode + " error_msg=" + error_msg);
                Toast.makeText(MainActivity.this, error_msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "request on cancel");
            }
        });
    }

    //上传文件 未测试
    private void doUpload() {
        //文件参数
        File file = new File(Environment.getExternalStorageDirectory() + "/girls/head/output_tmp2.jpg");
        Map<String, File> files = new HashMap<String, File>();
        files.put("avatar", file);

        //upload
        MyHttp.doUpload(this, "http://192.168.3.1/test_post.php", files, new MyHttpFileResponseHandler() {
            @Override
            public void onSuccess(int statusCode) {
                Log.i(TAG, "onSuccess status code=" + statusCode);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.i(TAG, "onFailure status code=" + statusCode + " error_msg=" + error_msg);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                Log.i(TAG, String.format("Progress %d from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "request on cancel");
            }
        });
    }

    //下载文件  未测试
    private void doDownload() {
        File file = new File(Environment.getExternalStorageDirectory() + "/girls/head/output_tmp2.jpg");    //下载后存储的file位置
        MyHttp.doDownload(this, "http://192.168.3.1/head.jpg", file, new MyHttpFileResponseHandler() {
            @Override
            public void onSuccess(int statusCode) {
                Log.i(TAG, "onSuccess status code=" + statusCode);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.i(TAG, "onFailure status code=" + statusCode);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                Log.i(TAG, String.format("Progress %d from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "request on cancel");
            }
        });
    }


}
