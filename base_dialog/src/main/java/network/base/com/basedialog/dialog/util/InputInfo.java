package network.base.com.basedialog.dialog.util;

/**
 * @author : cuu
 * date    : 2019/4/9
 * desc    : 输入详细信息
 */
public class InputInfo {
    private int MAX_LENGTH;
    private int inputType;

    public int getMAX_LENGTH() {
        return MAX_LENGTH;
    }

    public InputInfo setMAX_LENGTH(int MAX_LENGTH) {
        this.MAX_LENGTH = MAX_LENGTH;
        return this;
    }

    public int getInputType() {
        return inputType;
    }


    public InputInfo setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }
}
