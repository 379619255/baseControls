package network.base.com.network.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import network.base.com.basedialog.picker.AddressPickTask;
import network.base.com.basedialog.picker.DatePicker;
import network.base.com.basedialog.picker.DateTimePicker;

import network.base.com.basedialog.picker.SinglePicker;
import network.base.com.basedialog.picker.TimePicker;
import network.base.com.basedialog.picker.common.AddressPicker;
import network.base.com.basedialog.picker.common.entity.City;
import network.base.com.basedialog.picker.common.entity.County;
import network.base.com.basedialog.picker.common.entity.Province;
import network.base.com.basedialog.picker.common.util.ConvertUtils;
import network.base.com.network.R;
import network.base.com.network.bean.GoodsCategory;

/**
 * @author cuu
 */
public class PickerActivity extends AppCompatActivity {


    @BindView(R.id.year)
    Button year;
    @BindView(R.id.month)
    Button month;
    @BindView(R.id.year_time)
    Button year_time;
    @BindView(R.id.year_month)
    Button year_month;
    @BindView(R.id.time)
    Button time;
    @BindView(R.id.onSinglePicker)
    Button onSinglePicker;
    /*@BindView(R.id.onMultiplePicker)
    Button onMultiplePicker;*/
    @BindView(R.id.city)
    Button city;
    @BindView(R.id.city_two)
    Button city_two;
    @BindView(R.id.cityTwo)
    Button getCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.year, R.id.month,R.id.year_time,R.id.year_month,R.id.time,R.id.onSinglePicker,R.id.city,
    R.id.cityTwo,R.id.city_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.year://年月日
                onYearMonthDayPicker();
                break;
            case R.id.month://月日
                onMonthDayPicker();
                break;
            case R.id.year_time://年月日时间
                onYearMonthDayTimePicker();
                break;
            case R.id.year_month://年月
                onYearMonthPicker();
                break;
            case R.id.time://时间
                onTimePicker();
                break;
            case R.id.onSinglePicker://单个选择器
                onSinglePicker();
                break;
            /*case R.id.onMultiplePicker://多项选择
                onMultiplePicker();
                break;*/
            case R.id.city://多级联动（省地县）
                onAddressPicker();

                break;
            case R.id.city_two://多级联动（地、县）
                onAddress2Picker();

                break;
            case R.id.cityTwo://多级联动（省、地）

                onAddress3Picker();
                break;
        }
    }

    /*public void onMultiplePicker() {
        MultiplePicker picker = new MultiplePicker(this, new String[]{"111", "222", "333", "444","555", "666", "777", "888"});
        picker.setOnItemPickListener(new MultiplePicker.OnItemPickListener() {
            @Override
            public void onItemPicked(int count, List<String> items) {

                Toast.makeText(PickerActivity.this, "已选" + count + "项：" + items, Toast.LENGTH_SHORT).show();

            }
        });
        picker.show();
    }*/
    /**
     * 省 地
     */
    public void onAddress3Picker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {

                Toast.makeText(PickerActivity.this, "数据初始化失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {

                Toast.makeText(PickerActivity.this, province.getAreaName() + " " + city.getAreaName(), Toast.LENGTH_SHORT).show();
            }
        });
        task.execute("四川", "阿坝");
    }
    /**
     * 地县
     */
    public void onAddress2Picker() {
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(getAssets().open("city2.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker picker = new AddressPicker(this, data);
            picker.setShadowVisible(true);
            //
            picker.setTextSizeAutoFit(true);
            picker.setHideProvince(true);
            picker.setSelectedItem("贵州", "贵阳", "花溪");
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    Toast.makeText(PickerActivity.this, "province : " + province + ", city: " + city + ", county: " + county, Toast.LENGTH_SHORT).show();

                }
            });
            picker.show();
        } catch (Exception e) {
            Toast.makeText(PickerActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
    //省地县
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {

                Toast.makeText(PickerActivity.this, "数据初始化失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    Toast.makeText(PickerActivity.this, province.getAreaName() + city.getAreaName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PickerActivity.this, province.getAreaName() + city.getAreaName() + county.getAreaName(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        task.execute("贵州", "毕节", "纳雍");
    }
    //单项选择
    public void onSinglePicker() {
        List<GoodsCategory> data = new ArrayList<>();
        data.add(new GoodsCategory(1, "1111"));
        data.add(new GoodsCategory(2, "2222"));
        data.add(new GoodsCategory(3, "3333"));
        data.add(new GoodsCategory(4, "4444"));
        data.add(new GoodsCategory(5, "5555"));
        data.add(new GoodsCategory(6, "6666"));
        SinglePicker<GoodsCategory> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(false);
        picker.setTopLineColor(Color.BLACK);
        //默认选中
        picker.setSelectedIndex(1);
        //禁止循环
        picker.setCycleDisable(true);
        //是否禁止显示分割线
        /*picker.setDividerVisible(false);*/
        //分割线颜色
        /*picker.setDividerColor(0xFFFF0000);*/
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<GoodsCategory>() {
            @Override
            public void onItemPicked(int index, GoodsCategory item) {
                Toast.makeText(PickerActivity.this, "index=" + index + ", id=" + item.getId() + ", name=" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        picker.show();
    }
    /**
     * 时间
     */
    public void onTimePicker() {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setUseWeight(false);
        //是否循环禁用
        picker.setCycleDisable(true);
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.setTopLineVisible(false);
        //可用于设置每项的宽度，单位为dp
        picker.setTextPadding(ConvertUtils.toPx(this, 15));
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {

                Toast.makeText(PickerActivity.this, hour + ":" + minute, Toast.LENGTH_SHORT).show();

            }
        });
        picker.show();
    }
    /**
     * 年月
     */
    public void onYearMonthPicker() {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        //显示的位置
        picker.setGravity(Gravity.BOTTOM);
        //显示框架的宽度 如果显示全屏框参考onMonthDayPicker
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setRangeStart(2016, 10, 14);
        picker.setRangeEnd(2020, 11, 11);
        picker.setSelectedItem(2017, 9);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                Toast.makeText(PickerActivity.this, year + "-" + month, Toast.LENGTH_SHORT).show();

            }
        });
        picker.show();
    }

    /**
     * 年月日时分
     */
    public void onYearMonthDayTimePicker() {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        //开始的年
        picker.setDateRangeStart(1999, 1, 1);
        picker.setDateRangeEnd(2099, 11, 11);
        picker.setTimeRangeStart(0, 0);
        picker.setTimeRangeEnd(23, 30);
        //选中的时间点
        picker.setSelectedItem(2019,1,3,5,6);
        //顶部线条颜色
        picker.setTopLineColor(0x99FF0000);
        //选择的颜色
        picker.setLabelTextColor(0xFFFF0000);
        //分割线颜色
        picker.setDividerColor(0xFFFF0000);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {

                Toast.makeText(PickerActivity.this, year + "-" + month + "-" + day + " " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

            }
        });
        picker.show();
    }
    /**
     * 年月日
     */
    private void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        //点击外部可以取消
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        //日期结束
        picker.setRangeEnd(2111, 1, 11);
        //日期开始
        picker.setRangeStart(2016, 8, 29);
        //选中的日期
        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {

                Toast.makeText(PickerActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
    /**
     * 月日
     */
    public void onMonthDayPicker(){
        DatePicker picker = new DatePicker(this, DatePicker.MONTH_DAY);
        picker.setUseWeight(false);
        picker.setTextPadding(ConvertUtils.toPx(this, 15));//加宽显示项
        picker.setGravity(Gravity.BOTTOM);//弹框居下
        picker.setResetWhileWheel(false);
        //开始的时间
        picker.setRangeStart(1, 1);
        //结束的时间
        picker.setRangeEnd(12, 31);
        //默认选择的时间
        picker.setSelectedItem(10, 14);
        picker.setOnDatePickListener(new DatePicker.OnMonthDayPickListener() {
            @Override
            public void onDatePicked(String month, String day) {
                Toast.makeText(PickerActivity.this, month + "-" + day, Toast.LENGTH_SHORT).show();

            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText( month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


}
