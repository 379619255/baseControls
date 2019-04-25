package network.base.com.network.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.base.permissions.DangerousPermissions;
import com.base.permissions.OnPermission;
import com.base.permissions.Permission;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import network.base.com.network.R;

public class PermissionActivity extends AppCompatActivity {

    @BindView(R.id.getRequestPermission)
    Button getRequestPermission;
    @BindView(R.id.isHasPermission)
    Button isHasPermission;
    @BindView(R.id.gotoPermissionSettings)
    Button gotoPermissionSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.getRequestPermission, R.id.isHasPermission, R.id.gotoPermissionSettings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getRequestPermission:
                requestPermission();
                break;
            case R.id.isHasPermission:
                isHasPermission();
                break;
            case R.id.gotoPermissionSettings:
                gotoPermissionSettings();
                break;
        }
    }

    public void requestPermission() {
        DangerousPermissions.with(this)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                /**不指定权限则自动获取清单中的危险权限  添加了存储/日历权限 */
                .permission(Permission.Group.STORAGE, Permission.Group.CALENDAR)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            Toast.makeText(PermissionActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(PermissionActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(PermissionActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();

                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            DangerousPermissions.gotoPermissionSettings(PermissionActivity.this);
                        } else {

                            Toast.makeText(PermissionActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void isHasPermission() {
        //if (DangerousPermissions.isHasPermission(PermissionActivity.this, Permission.Group.STORAGE))
        if (DangerousPermissions.isHasPermission(PermissionActivity.this, Permission.Group.STORAGE,Permission.Group.CALENDAR)) {

            Toast.makeText(PermissionActivity.this, "已经获取到权限，不需要再次申请了", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(PermissionActivity.this, "还没有获取到权限或者部分权限未授予", Toast.LENGTH_SHORT).show();


        }
    }

    public void gotoPermissionSettings() {
        DangerousPermissions.gotoPermissionSettings(PermissionActivity.this);
    }
}
