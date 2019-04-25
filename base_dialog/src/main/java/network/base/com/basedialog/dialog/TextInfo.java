package network.base.com.basedialog.dialog;

/**
 * @author : cuu
 * date    : 2019/4/2上午11:05
 * desc    :
 */
public class TextInfo {
    private int fontSize = -1;      //字号大小，值为-1时使用默认样式，单位：dp
    private int gravity = -1;       //对齐方式，值为-1时使用默认样式，取值可使用Gravity.CENTER等对齐方式
    private int fontColor = -1;     //文字颜色，值为-1时使用默认样式，取值可以用Color.rgb(r,g,b)等方式获取
    private boolean bold = false;   //是否粗体

    public int getFontSize() {
        return fontSize;
    }

    public TextInfo setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    public TextInfo setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public int getFontColor() {
        return fontColor;
    }

    public TextInfo setFontColor(int fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    public TextInfo setBold(boolean bold) {
        this.bold = bold;
        return this;
    }
}
