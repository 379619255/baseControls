package network.base.com.network.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import network.base.com.basedialog.dialog.BottomMenu;
import network.base.com.basedialog.dialog.InputDialog;
import network.base.com.basedialog.dialog.InputDialogOkButtonClickListener;
import network.base.com.basedialog.dialog.MessageDialog;
import network.base.com.basedialog.dialog.OnMenuItemClickListener;
import network.base.com.basedialog.dialog.SelectDialog;
import network.base.com.network.R;

import static network.base.com.basedialog.dialog.TipDialog.SHOW_TIME_SHORT;

public class AlertActivity extends AppCompatActivity {

    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.ok_cancel)
    Button ok_cancel;
    @BindView(R.id.input)
    Button input;
    @BindView(R.id.menu)
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ok, R.id.ok_cancel,R.id.input,R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ok:
                MessageDialog.show(AlertActivity.this, "消息提示框", "用于提示一些消息", "知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertActivity.this, "111", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ok_cancel:
                SelectDialog.show(AlertActivity.this, "提示", "请做出你的选择", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertActivity.this, "您点击了确定按钮", Toast.LENGTH_SHORT).show();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertActivity.this, "您点击了取消按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.input:
                InputDialog.show(AlertActivity.this, "标题", "请输入内容", "确定", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        Toast.makeText(AlertActivity.this, "您输入了：" + inputText, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case R.id.menu:
                List<String> list = new ArrayList<>();
                list.add("菜单1");
                list.add("菜单2");
                list.add("菜单3");
                BottomMenu.show(AlertActivity.this, list, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        Toast.makeText(AlertActivity.this,"菜单 " + text + " 被点击了",SHOW_TIME_SHORT).show();
                    }
                },true).setTitle("标题");
                break;
        }
    }
}
