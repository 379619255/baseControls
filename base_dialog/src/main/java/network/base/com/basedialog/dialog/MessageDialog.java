package network.base.com.basedialog.dialog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import network.base.com.basedialog.R;
import network.base.com.basedialog.dialog.util.ModalBaseDialog;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static network.base.com.basedialog.dialog.DialogSettings.THEME_DARK;
import static network.base.com.basedialog.dialog.DialogSettings.TYPE_IOS;

import static network.base.com.basedialog.dialog.DialogSettings.TYPE_MATERIAL;
import static network.base.com.basedialog.dialog.DialogSettings.blur_alpha;
import static network.base.com.basedialog.dialog.DialogSettings.dialogButtonTextInfo;
import static network.base.com.basedialog.dialog.DialogSettings.dialogContentTextInfo;
import static network.base.com.basedialog.dialog.DialogSettings.dialogTitleTextInfo;
import static network.base.com.basedialog.dialog.DialogSettings.dialog_background_color;
import static network.base.com.basedialog.dialog.DialogSettings.dialog_cancelable_default;
import static network.base.com.basedialog.dialog.DialogSettings.dialog_theme;
import static network.base.com.basedialog.dialog.DialogSettings.type;

/**
 * @author : cuu
 * date    : 2019/4/9
 * desc    : 确定框
 */
public class MessageDialog extends ModalBaseDialog {
    private MessageDialog messageDialog;
    private AlertDialog alertDialog;
    private boolean isCanCancel = true;

    private Context context;
    private String title;
    private String message;
    private String buttonCaption = "确定";
    private DialogInterface.OnClickListener onOkButtonClickListener;

    private TextInfo customTitleTextInfo;
    private TextInfo customContentTextInfo;
    private TextInfo customOkButtonTextInfo;

    private MessageDialog() {
    }

    //Fast Function
    public static MessageDialog show(Context context, String title, String message) {
        MessageDialog messageDialog = build(context, title, message, "确定", null);
        messageDialog.showDialog();
        return messageDialog;
    }

    public static MessageDialog show(Context context, String title, String message, String buttonCaption, DialogInterface.OnClickListener onOkButtonClickListener) {
        MessageDialog messageDialog = build(context, title, message, buttonCaption, onOkButtonClickListener);
        messageDialog.showDialog();
        return messageDialog;
    }

    public static MessageDialog build(Context context, String title, String message, String buttonCaption, DialogInterface.OnClickListener onOkButtonClickListener) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = new MessageDialog();
            messageDialog.cleanDialogLifeCycleListener();
            messageDialog.alertDialog = null;
            messageDialog.context = context;
            messageDialog.title = title;
            messageDialog.buttonCaption = buttonCaption;
            messageDialog.message = message;
            messageDialog.onOkButtonClickListener = onOkButtonClickListener;
            messageDialog.isCanCancel = dialog_cancelable_default;

            messageDialog.messageDialog = messageDialog;
            modalDialogList.add(messageDialog);
            return messageDialog;
        }
    }


    private ViewGroup bkg;
    private TextView txtDialogTitle;
    private TextView txtDialogTip;
    private EditText txtInput;
    private ImageView splitHorizontal;
    private TextView btnSelectNegative;
    private ImageView splitVertical;
    private TextView btnSelectPositive;
    private RelativeLayout customView;

    int blur_front_color;

    @Override
    @SuppressLint("WrongViewCast")
    public void showDialog() {
        if (customTitleTextInfo == null) {
            customTitleTextInfo = dialogTitleTextInfo;
        }
        if (customContentTextInfo == null) {
            customContentTextInfo = dialogContentTextInfo;
        }
        if (customOkButtonTextInfo == null) {
            customOkButtonTextInfo = dialogButtonTextInfo;
        }


        dialogList.add(messageDialog);
        modalDialogList.remove(messageDialog);

        AlertDialog.Builder builder;
        switch (type) {
            case TYPE_IOS:
                switch (dialog_theme) {
                    case THEME_DARK:
                        builder = new AlertDialog.Builder(context, R.style.darkMode);
                        break;
                    default:
                        builder = new AlertDialog.Builder(context, R.style.lightMode);
                        break;
                }
                break;

            default:
                builder = new AlertDialog.Builder(context);
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

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogList.remove(messageDialog);
                if (bkg != null) {
                    bkg.removeAllViews();
                }
                if (customView != null) {
                    customView.removeAllViews();
                }
                customView = null;
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                }
                isDialogShown = false;
                context = null;

                if (!modalDialogList.isEmpty()) {
                    showNextModalDialog();
                }
            }
        });

        Window window = alertDialog.getWindow();
        switch (type) {
            case TYPE_IOS:
                window.setWindowAnimations(R.style.iOSAnimStyle);
                alertDialog.show();
                window.setContentView(R.layout.dialog_select_ios);

                bkg = (RelativeLayout) window.findViewById(R.id.bkg);
                txtDialogTitle = window.findViewById(R.id.txt_dialog_title);
                txtDialogTip = window.findViewById(R.id.txt_dialog_tip);
                txtInput = window.findViewById(R.id.txt_input);
                splitHorizontal = window.findViewById(R.id.split_horizontal);
                btnSelectNegative = window.findViewById(R.id.btn_selectNegative);
                splitVertical = window.findViewById(R.id.split_vertical);
                btnSelectPositive = window.findViewById(R.id.btn_selectPositive);
                customView = window.findViewById(R.id.box_custom);

                if (isNull(title)) {
                    txtDialogTitle.setVisibility(View.GONE);
                } else {
                    txtDialogTitle.setVisibility(View.VISIBLE);
                    txtDialogTitle.setText(title);
                }
                if (isNull(message)) {
                    txtDialogTip.setVisibility(View.GONE);
                } else {
                    txtDialogTip.setVisibility(View.VISIBLE);
                    txtDialogTip.setText(message);
                }

                btnSelectNegative.setVisibility(View.GONE);
                splitVertical.setVisibility(View.GONE);
                btnSelectPositive.setText(buttonCaption);
                btnSelectPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        if (onOkButtonClickListener != null) {
                            onOkButtonClickListener.onClick(alertDialog, BUTTON_POSITIVE);
                        }
                    }
                });

                int bkgResId;
                if (dialog_theme == THEME_DARK) {
                    splitHorizontal.setBackgroundResource(R.color.ios_dialog_split_dark);
                    splitVertical.setBackgroundResource(R.color.ios_dialog_split_dark);
                    btnSelectPositive.setBackgroundResource(R.drawable.button_dialog_one_dark);
                    bkgResId = R.drawable.rect_dlg_dark;
                    blur_front_color = Color.argb(blur_alpha, 0, 0, 0);
                } else {
                    btnSelectPositive.setBackgroundResource(R.drawable.button_dialog_one);
                    bkgResId = R.drawable.rect_light;
                    blur_front_color = Color.argb(blur_alpha, 255, 255, 255);      //白
                }

                bkg.setBackgroundResource(bkgResId);
                useTextInfo(txtDialogTitle, customTitleTextInfo);
                useTextInfo(txtDialogTip, customContentTextInfo);
                useTextInfo(btnSelectPositive, customOkButtonTextInfo);

                if (dialog_background_color != -1) {
                    bkg.setBackgroundResource(dialog_background_color);
                }

                break;
        }
        isDialogShown = true;
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onShow(alertDialog);
        }
    }

    private void useTextInfo(TextView textView, TextInfo textInfo) {
        if (textInfo.getFontSize() > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textInfo.getFontSize());
        }
        if (textInfo.getFontColor() != -1) {
            textView.setTextColor(textInfo.getFontColor());
        }
        if (textInfo.getGravity() != -1) {
            textView.setGravity(textInfo.getGravity());
        }
        textView.getPaint().setFakeBoldText(textInfo.isBold());
    }

    @Override
    public void doDismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public MessageDialog setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        if (alertDialog != null) {
            alertDialog.setCancelable(canCancel);
        }
        return this;
    }

    public MessageDialog setCustomView(View view) {
        if (type == TYPE_MATERIAL) {
            customView = new RelativeLayout(context);
            customView.addView(view);
        } else {
            if (alertDialog != null && view != null) {
                customView.setVisibility(View.VISIBLE);
                customView.addView(view);
            }
        }
        return this;
    }

    private boolean isNull(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("null")) {
            return true;
        }
        return false;
    }

    public MessageDialog setTitleTextInfo(TextInfo textInfo) {
        this.customTitleTextInfo = textInfo;
        return this;
    }

    public MessageDialog setContentTextInfo(TextInfo textInfo) {
        this.customContentTextInfo = textInfo;
        return this;
    }

    public MessageDialog setOkButtonTextInfo(TextInfo textInfo) {
        this.customOkButtonTextInfo = textInfo;
        return this;
    }
}
