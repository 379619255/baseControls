package network.base.com.basedialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.design.widget.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import network.base.com.basedialog.R;

import static network.base.com.basedialog.dialog.DialogSettings.dialogButtonTextInfo;
import static network.base.com.basedialog.dialog.DialogSettings.menuTextInfo;


/**
 * @author : cuu
 * date    : 2019/4/9
 * desc    :
 */
public class BottomMenu extends BaseDialog {
    private BottomMenu bottomMenu;
    private List<String> menuText;
    private AlertDialog alertDialog;
    private AppCompatActivity activity;
    private boolean isShowCancelButton = false;
    private OnMenuItemClickListener onMenuItemClickListener;
    private String title;
    private String cancelButtonCaption = "取消";

    private TextInfo customMenuTextInfo;
    private TextInfo customButtonTextInfo;

    private BottomMenu() {
    }

    //Fast Function
    public static BottomMenu show(AppCompatActivity activity, String[] menuText) {
        List<String> list = new ArrayList<>();
        for (String s : menuText) {
            list.add(s);
        }
        return show(activity, list, null, true, "取消");
    }

    public static BottomMenu show(AppCompatActivity activity, List<String> menuText) {
        return show(activity, menuText, null, true, "取消");
    }

    public static BottomMenu show(AppCompatActivity activity, String[] menuText, OnMenuItemClickListener onMenuItemClickListener) {
        List<String> list = new ArrayList<>();
        for (String s : menuText) {
            list.add(s);
        }
        return show(activity, list, onMenuItemClickListener, true, "取消");
    }

    public static BottomMenu show(AppCompatActivity activity, List<String> menuText, OnMenuItemClickListener onMenuItemClickListener) {
        return show(activity, menuText, onMenuItemClickListener, true, "取消");
    }

    public static BottomMenu show(AppCompatActivity activity, String[] menuText, OnMenuItemClickListener onMenuItemClickListener, boolean isShowCancelButton) {
        List<String> list = new ArrayList<>();
        for (String s : menuText) {
            list.add(s);
        }
        return show(activity, list, onMenuItemClickListener, isShowCancelButton, "取消");
    }

    public static BottomMenu show(AppCompatActivity activity, List<String> menuText, OnMenuItemClickListener onMenuItemClickListener, boolean isShowCancelButton) {
        return show(activity, menuText, onMenuItemClickListener, isShowCancelButton, "取消");
    }

    public static BottomMenu show(AppCompatActivity activity, String[] menuText, OnMenuItemClickListener onMenuItemClickListener, boolean isShowCancelButton, String cancelButtonCaption) {
        List<String> list = new ArrayList<>();
        for (String s : menuText) {
            list.add(s);
        }
        return show(activity, list, onMenuItemClickListener, isShowCancelButton, cancelButtonCaption);
    }

    public static BottomMenu show(AppCompatActivity activity, List<String> menuText, OnMenuItemClickListener onMenuItemClickListener, boolean isShowCancelButton, String cancelButtonCaption) {
        synchronized (BottomMenu.class) {
            BottomMenu bottomMenu = new BottomMenu();
            bottomMenu.cleanDialogLifeCycleListener();
            bottomMenu.activity = activity;
            bottomMenu.menuText = menuText;
            bottomMenu.isShowCancelButton = isShowCancelButton;
            bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
            bottomMenu.cancelButtonCaption = cancelButtonCaption;
            bottomMenu.title = "";
            if (menuText.isEmpty()) {

                return bottomMenu;
            }

            bottomMenu.showDialog();
            bottomMenu.bottomMenu = bottomMenu;
            return bottomMenu;
        }
    }

    private MyBottomSheetDialog bottomSheetDialog;
    private ArrayAdapter menuArrayAdapter;

    private TextView txtTitle;
    private ListView listMenu;
    private TextView btnCancel;
    private ViewGroup boxCancel;
    private ImageView splitLine;
    private RelativeLayout customView;


    private RelativeLayout boxList;

    @Override
    public void showDialog() {

        dialogList.add(bottomMenu);

        if (customMenuTextInfo == null) {
            customMenuTextInfo = menuTextInfo;
        }
        if (customButtonTextInfo == null) {
            customButtonTextInfo = dialogButtonTextInfo;
        }

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(activity, R.style.bottom_menu);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onCreate(alertDialog);
        }
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogList.remove(bottomMenu);
                if (customView != null) {
                    customView.removeAllViews();
                }
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                }
                isDialogShown = false;
                activity = null;
            }
        });
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.bottomMenuAnimStyle);

        int resId = R.layout.bottom_menu_ios;
        int item_resId = R.layout.item_bottom_menu_ios;
        window.setContentView(resId);

        listMenu = window.findViewById(R.id.list_menu);
        btnCancel = window.findViewById(R.id.btn_cancel);
        txtTitle = window.findViewById(R.id.title);
        splitLine = window.findViewById(R.id.title_split_line);
        customView = window.findViewById(R.id.box_custom);

        if (title != null && !title.trim().isEmpty()) {
            txtTitle.setText(title);
            txtTitle.setVisibility(View.VISIBLE);
            splitLine.setVisibility(View.VISIBLE);
        } else {
            txtTitle.setVisibility(View.GONE);
            splitLine.setVisibility(View.GONE);
        }

        if (customButtonTextInfo.getFontSize() > 0) {
            btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, customButtonTextInfo.getFontSize());
        }
        if (customButtonTextInfo.getGravity() != -1) {
            btnCancel.setGravity(customButtonTextInfo.getGravity());
        }
        if (customButtonTextInfo.getFontColor() != -1) {
            btnCancel.setTextColor(customButtonTextInfo.getFontColor());
        }
        btnCancel.getPaint().setFakeBoldText(customButtonTextInfo.isBold());

        btnCancel.setText(cancelButtonCaption);

        boxList = window.findViewById(R.id.box_list);
        boxCancel = (RelativeLayout) window.findViewById(R.id.box_cancel);

        boxList.setBackgroundResource(R.drawable.rect_button_bottom_menu_ios);
        boxCancel.setBackgroundResource(R.drawable.rect_button_bottom_menu_ios);

        if (isShowCancelButton) {
            if (boxCancel != null) {
                boxCancel.setVisibility(View.VISIBLE);
            }
        } else {
            if (boxCancel != null) {
                boxCancel.setVisibility(View.GONE);
            }
        }

        menuArrayAdapter = new IOSMenuArrayAdapter(activity, item_resId, menuText);
        listMenu.setAdapter(menuArrayAdapter);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onMenuItemClickListener != null) {
                    onMenuItemClickListener.onClick(menuText.get(position), position);
                }
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
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

    class NormalMenuArrayAdapter extends ArrayAdapter {
        public int resoureId;
        public List<String> objects;
        public Context context;

        public NormalMenuArrayAdapter(Context context, int resourceId, List<String> objects) {
            super(context, resourceId, objects);
            this.objects = objects;
            this.resoureId = resourceId;
            this.context = context;
        }

        public class ViewHolder {
            TextView textView;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public String getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(resoureId, null);
                viewHolder.textView = convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String text = objects.get(position);
            if (null != text) {
                viewHolder.textView.setText(text);
                if (customMenuTextInfo.getFontSize() > 0) {
                    viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, customMenuTextInfo.getFontSize());
                }
                if (customMenuTextInfo.getGravity() != -1) {
                    viewHolder.textView.setGravity(customMenuTextInfo.getGravity());
                }
                if (customMenuTextInfo.getFontColor() != -1) {
                    viewHolder.textView.setTextColor(customMenuTextInfo.getFontColor());
                }
                viewHolder.textView.getPaint().setFakeBoldText(customMenuTextInfo.isBold());
            }

            return convertView;
        }
    }

    class IOSMenuArrayAdapter extends NormalMenuArrayAdapter {

        public IOSMenuArrayAdapter(Context context, int resourceId, List<String> objects) {
            super(context, resourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(resoureId, null);
                viewHolder.textView = convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String text = objects.get(position);
            if (null != text) {
                viewHolder.textView.setText(text);

                if (customMenuTextInfo.getFontSize() > 0) {
                    viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, customMenuTextInfo.getFontSize());
                }
                if (customMenuTextInfo.getGravity() != -1) {
                    viewHolder.textView.setGravity(customMenuTextInfo.getGravity());
                }
                if (customMenuTextInfo.getFontColor() != -1) {
                    viewHolder.textView.setTextColor(customMenuTextInfo.getFontColor());
                }
                viewHolder.textView.getPaint().setFakeBoldText(customMenuTextInfo.isBold());

                if (objects.size() == 1) {
                    if (title != null && !title.trim().isEmpty()) {
                        viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom);
                    } else {
                        if (customView.getVisibility() == View.VISIBLE) {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_all);
                        } else {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_all);
                        }
                    }
                } else {
                    if (position == 0) {
                        if (title != null && !title.trim().isEmpty()) {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_middle);
                        } else {
                            if (customView.getVisibility() == View.VISIBLE) {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_middle);
                            } else {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_top);
                            }
                        }
                    } else if (position == objects.size() - 1) {
                        viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom);
                    } else {
                        viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_middle);
                    }
                }
            }

            return convertView;
        }
    }

    public String getTitle() {
        return title;
    }

    //设置标题
    public BottomMenu setTitle(String title) {
        this.title = title;
        if (alertDialog != null && txtTitle != null) {
            if (title != null && !title.trim().isEmpty()) {
                txtTitle.setText(title);
                txtTitle.setVisibility(View.VISIBLE);
                splitLine.setVisibility(View.VISIBLE);
            } else {
                txtTitle.setVisibility(View.GONE);
            }
        }
        if (menuArrayAdapter != null) {
            menuArrayAdapter.notifyDataSetChanged();
        }
        return this;
    }

    class MyBottomSheetDialog extends BottomSheetDialog {

        public MyBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public MyBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        protected MyBottomSheetDialog(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int screenHeight = getScreenHeight(getContext());
            int statusBarHeight = getStatusBarHeight(getContext());
            int dialogHeight = screenHeight;//- statusBarHeight;
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }

        public int getStatusBarHeight(Context context) {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        public int getScreenHeight(Context context) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            return height;
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public BottomMenu setCustomView(View view) {
        if (alertDialog != null && view != null) {
            customView.setVisibility(View.VISIBLE);
            splitLine.setVisibility(View.VISIBLE);
            customView.addView(view);
            menuArrayAdapter.notifyDataSetChanged();
        }
        return this;
    }

    public BottomMenu setMenuTextInfo(TextInfo textInfo) {
        customMenuTextInfo = textInfo;
        return this;
    }

    public BottomMenu setButtonTextInfo(TextInfo textInfo) {
        customButtonTextInfo = textInfo;
        return this;
    }

}
