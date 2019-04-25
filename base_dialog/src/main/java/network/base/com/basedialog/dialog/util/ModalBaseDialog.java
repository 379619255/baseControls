package network.base.com.basedialog.dialog.util;

import java.util.ArrayList;
import java.util.List;

import network.base.com.basedialog.dialog.BaseDialog;

/**
 * @author : cuu
 * date    : 2019/4/9
 * desc    :
 */
public abstract class ModalBaseDialog extends BaseDialog {

    protected static List<BaseDialog> modalDialogList = new ArrayList<>();         //对话框模态化队列

    protected static void showNextModalDialog(){

        modalDialogList.get(0).showDialog();
    }
}