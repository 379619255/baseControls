package network.base.com.basedialog.dialog;

import android.app.Dialog;

/**
 * @author : cuu
 * date    : 2019/4/2上午11:06
 * desc    :
 */
public interface DialogLifeCycleListener {
    void onCreate(Dialog alertDialog);

    void onShow(Dialog alertDialog);

    void onDismiss();
}
