package network.base.com.basedialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import network.base.com.basedialog.R;

import static network.base.com.basedialog.dialog.DialogSettings.THEME_LIGHT;
import static network.base.com.basedialog.dialog.DialogSettings.blur_alpha;
import static network.base.com.basedialog.dialog.DialogSettings.tipTextInfo;
import static network.base.com.basedialog.dialog.DialogSettings.tip_theme;

/**
 * @author : cuu
 * date    : 2019/4/2上午11:14
 * desc    :
 */
public class WaitDialog extends BaseDialog {

    private OnBackPressListener onBackPressListener;
    private AlertDialog alertDialog;
    private WaitDialog waitDialog;
    private boolean isCanCancel = false;

    private View customView;
    private TextInfo customTextInfo;

    private Context context;
    private String tip;

    public WaitDialog() {
    }

    /**
     *
     * @param context  上下文
     * @param tip       提示
     * @return
     */
    public static WaitDialog show(Context context, String tip) {
        synchronized (WaitDialog.class) {
            WaitDialog waitDialog = new WaitDialog();
            waitDialog.cleanDialogLifeCycleListener();
            waitDialog.context = context;
            waitDialog.tip = tip;
            waitDialog.log("装载等待对话框 -> " + tip);
            waitDialog.waitDialog = waitDialog;
            waitDialog.showDialog();
            return waitDialog;
        }
    }

    public static WaitDialog show(Context context, String tip, View customView) {
        synchronized (WaitDialog.class) {
            WaitDialog waitDialog = new WaitDialog();
            waitDialog.cleanDialogLifeCycleListener();
            waitDialog.context = context;
            waitDialog.tip = tip;
            waitDialog.log("装载等待对话框 -> " + tip);
            waitDialog.waitDialog = waitDialog;
            waitDialog.customView = customView;
            waitDialog.showDialog();
            return waitDialog;
        }
    }

    /**
     *
     * @param context  上下文
     * @param tip      提示内容
     * @param textInfo  字体大小
     * @return
     */
    public static WaitDialog show(Context context, String tip, TextInfo textInfo) {
        synchronized (WaitDialog.class) {
            WaitDialog waitDialog = new WaitDialog();
            waitDialog.cleanDialogLifeCycleListener();
            waitDialog.context = context;
            waitDialog.tip = tip;
            waitDialog.log("装载等待对话框 -> " + tip);
            waitDialog.waitDialog = waitDialog;
            waitDialog.customTextInfo = textInfo;
            waitDialog.showDialog();
            return waitDialog;
        }
    }

    public static WaitDialog show(Context context, String tip, View customView, TextInfo textInfo) {
        synchronized (WaitDialog.class) {
            WaitDialog waitDialog = new WaitDialog();
            waitDialog.cleanDialogLifeCycleListener();
            waitDialog.context = context;
            waitDialog.tip = tip;
            waitDialog.log("装载等待对话框 -> " + tip);
            waitDialog.waitDialog = waitDialog;
            waitDialog.customView = customView;
            waitDialog.customTextInfo = textInfo;
            waitDialog.showDialog();
            return waitDialog;
        }
    }

    private int blur_front_color;

    private RelativeLayout boxInfo;
    private RelativeLayout boxBkg;
    private RelativeLayout boxProgress;
    private ProgressView progress;

    private TextView txtInfo;

    @Override
    public void showDialog() {
        if (customTextInfo == null) {
            customTextInfo = tipTextInfo;
        }

        dialogList.add(waitDialog);


        AlertDialog.Builder builder;
        int bkgResId;
        switch (tip_theme) {
            case THEME_LIGHT:
                builder = new AlertDialog.Builder(context, R.style.lightMode);
                bkgResId = R.drawable.rect_light;
                blur_front_color = Color.argb(blur_alpha - 50, 255, 255, 255);
                break;
            default:
                builder = new AlertDialog.Builder(context, R.style.darkMode);
                bkgResId = R.drawable.rect_dark;
                blur_front_color = Color.argb(blur_alpha, 0, 0, 0);
                break;
        }
        builder.setCancelable(isCanCancel);

        alertDialog = builder.create();

        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onCreate(alertDialog);
        }
        if (isCanCancel) {
            alertDialog.setCanceledOnTouchOutside(true);
        }
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_wait);

        boxInfo = window.findViewById(R.id.box_info);
        boxBkg = window.findViewById(R.id.box_bkg);
        boxProgress = window.findViewById(R.id.box_progress);
        progress = window.findViewById(R.id.progress);
        txtInfo = window.findViewById(R.id.txt_info);

        if (customView!=null){
            progress.setVisibility(View.GONE);
            boxProgress.removeAllViews();
            boxProgress.addView(customView);
        }

        if (tip_theme == THEME_LIGHT) {
            progress.setStrokeColors(new int[]{Color.rgb(227, 40, 45)});
        } else {
            progress.setStrokeColors(new int[]{Color.rgb(255, 255, 255)});
        }

        boxBkg.setBackgroundResource(bkgResId);
        if (!tip.isEmpty()) {
            boxInfo.setVisibility(View.VISIBLE);
            if (tip_theme == THEME_LIGHT) {
                txtInfo.setTextColor(Color.rgb(0, 0, 0));
            } else {
                txtInfo.setTextColor(Color.rgb(255, 255, 255));
            }

            txtInfo.setText(tip);
        } else {
            boxInfo.setVisibility(View.GONE);
        }

        if (customTextInfo.getFontSize() > 0) {
            txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, customTextInfo.getFontSize());
        }
        if (customTextInfo.getFontColor() != -1) {
            txtInfo.setTextColor(customTextInfo.getFontColor());
        }
        if (customTextInfo.getGravity() != -1) {
            txtInfo.setGravity(customTextInfo.getGravity());
        }
        txtInfo.getPaint().setFakeBoldText(customTextInfo.isBold());

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogList.remove(waitDialog);
                if (boxProgress!=null) {
                    boxProgress.removeAllViews();
                }
                if (boxBkg != null) {
                    boxBkg.removeAllViews();
                }
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                    alertDialog = null;
                }
            }
        });
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (onBackPressListener != null) {
                            onBackPressListener.OnBackPress(alertDialog);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        alertDialog.show();
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onShow(alertDialog);
        }
    }

    @Override
    public void doDismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public WaitDialog setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        if (alertDialog != null) {
            alertDialog.setCancelable(canCancel);
        }
        return this;
    }

    public WaitDialog setOnBackPressListener(OnBackPressListener onBackPressListener) {
        this.onBackPressListener = onBackPressListener;
        return this;
    }

    public static void dismiss() {
        for (BaseDialog dialog : dialogList) {
            if (dialog instanceof WaitDialog) {
                dialog.doDismiss();
            }
        }
    }
}
