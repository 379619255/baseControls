package com.base.photo.ninegridview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.base.photo.R;
import com.base.photo.ninegridview.adapter.NineGridTest2Adapter;
import com.base.photo.ninegridview.model.NineGridTestModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.Serializable;
import java.util.List;

/**
 * 九宫格入口
 */
public class RecyclerViewExampleActivity extends AppCompatActivity {
    private static final String  ARG_LIST = "list";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridTest2Adapter mAdapter;

    private List<NineGridTestModel> mList;
    public static void startActivity(Context context, List<NineGridTestModel> list) {
        Intent intent = new Intent(context, RecyclerViewExampleActivity.class);
        intent.putExtra(ARG_LIST, (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_example);
        initImageLoader();
        getIntentData();
        initView();
    }
    private void getIntentData() {
        mList = (List<NineGridTestModel>) getIntent().getSerializableExtra(ARG_LIST);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NineGridTest2Adapter(this);
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);
    }
    //初始化图片加载器 可以放到 application中初始加载
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
}
