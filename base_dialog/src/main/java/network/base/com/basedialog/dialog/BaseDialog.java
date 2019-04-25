package network.base.com.basedialog.dialog;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static network.base.com.basedialog.dialog.DialogSettings.DEBUGMODE;

/**
 * @author : cuu
 * date    : 2019/4/2上午11:03
 * desc    : 基本的dialog
 */
public abstract class BaseDialog {
    //对话框队列
    protected static List<BaseDialog> dialogList = new ArrayList<>();

    public boolean isDialogShown = false;

    private DialogLifeCycleListener dialogLifeCycleListener;

    public void log(Object o) {
        if (DEBUGMODE) {
            Log.i("DialogSDK >>>", o.toString());
        }
    }

    public void setDialogLifeCycleListener(DialogLifeCycleListener listener) {
        dialogLifeCycleListener = listener;
    }

    public DialogLifeCycleListener getDialogLifeCycleListener() {
        return dialogLifeCycleListener;
    }

    public void cleanDialogLifeCycleListener() {
        dialogLifeCycleListener = null;
    }

    public abstract void showDialog();

    public abstract void doDismiss();

    public static void unloadAllDialog() {
        try {
            for (BaseDialog baseDialog : dialogList) {
                baseDialog.doDismiss();
            }
            dialogList = new ArrayList<>();
        } catch (Exception e) {
            if (DEBUGMODE) {
                e.printStackTrace();
            }
        }
    }
}
