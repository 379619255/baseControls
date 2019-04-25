package network.base.com.network.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import network.base.com.basedialog.toast.BaseToast;
import network.base.com.basedialog.dialog.DialogSettings;
import network.base.com.basedialog.dialog.TipDialog;
import network.base.com.basedialog.dialog.WaitDialog;
import network.base.com.network.R;

public class ToastActivity extends AppCompatActivity implements View.OnClickListener {
    private Button no_message_toast;
    private Button error_message_toast;
    private Button success_message_toast;
    private Button info_message_toast;
    private Button warning_message_toast;
    private Button icon_message_toast;
    private Button show_loading;
    private Button no_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        no_message_toast = findViewById(R.id.no_message_toast);
        no_message_toast.setOnClickListener(this);

        error_message_toast = findViewById(R.id.error_message_toast);
        error_message_toast.setOnClickListener(this);
        success_message_toast = findViewById(R.id.success_message_toast);
        success_message_toast.setOnClickListener(this);
        info_message_toast = findViewById(R.id.info_message_toast);
        info_message_toast.setOnClickListener(this);
        warning_message_toast = findViewById(R.id.warning_message_toast);
        warning_message_toast.setOnClickListener(this);
        icon_message_toast = findViewById(R.id.icon_message_toast);
        icon_message_toast.setOnClickListener(this);
        show_loading = findViewById(R.id.show_loading);
        show_loading.setOnClickListener(this);
        no_loading = findViewById(R.id.no_loading);
        no_loading.setOnClickListener(this);

    }

    boolean isMiddle = false;
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (isMiddle){
            isMiddle = false;

        }else {
            isMiddle = true;

        }
        if (i == R.id.no_message_toast) {

            BaseToast.normal(ToastActivity.this, "这是一个普通的没有ICON的Toast",isMiddle).show();
            //BaseToast.showToast(ToastActivity.this, "111", 500);
        } else if (i == R.id.error_message_toast) {

            BaseToast.error(ToastActivity.this, "这是一个提示错误的Toast！", Toast.LENGTH_SHORT, true,isMiddle).show();
        } else if (i == R.id.success_message_toast) {

            BaseToast.success(ToastActivity.this, "这是一个提示错误的Toast！", Toast.LENGTH_SHORT, true,isMiddle).show();
        } else if (i == R.id.info_message_toast) {
            BaseToast.info(ToastActivity.this, "这是一个提示错误的Toast！", Toast.LENGTH_SHORT, true,isMiddle).show();
        } else if (i == R.id.warning_message_toast) {
            BaseToast.warning(ToastActivity.this, "这是一个提示错误的Toast！", Toast.LENGTH_SHORT, true,isMiddle).show();
        } else if (i == R.id.icon_message_toast) {
            Drawable icon = getResources().getDrawable(R.drawable.toast_frame);
            BaseToast.normal(ToastActivity.this, "这是一个普通的包含ICON的Toast", icon,isMiddle).show();
        }else if (i == R.id.show_loading){
            if (DialogSettings.tip_theme == 0){
                DialogSettings.tip_theme = 1;
            }else {
                DialogSettings.tip_theme = 0;
            }
            WaitDialog.show(ToastActivity.this, "请稍后");
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(3000);
                        WaitDialog.dismiss();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**
                     * 要执行的操作
                     */
                }
            }.start();
        }else if (i == R.id.no_loading){
            if (DialogSettings.tip_theme == 0){
                DialogSettings.tip_theme = 1;
            }else {
                DialogSettings.tip_theme = 0;
            }
            TipDialog.show(ToastActivity.this, "提示", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_FINISH);
        }
    }
}
