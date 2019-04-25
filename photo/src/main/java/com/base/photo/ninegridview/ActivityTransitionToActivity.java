package com.base.photo.ninegridview;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.base.photo.PhotoView;
import com.base.photo.R;
import com.base.photo.ninegridview.util.ImageLoaderUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ActivityTransitionToActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private ArrayList<String> urls = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_to);
        view_pager = findViewById(R.id.iv_photo);
        urls = getIntent().getStringArrayListExtra("urls");
        if (urls.size() > 0) {
            viewPage(urls);
        }
    }



    //显示page
    private void viewPage(ArrayList<String> urls) {

        view_pager.setAdapter(new SamplePagerAdapter(urls));
    }


    class SamplePagerAdapter extends PagerAdapter {
        RequestOptions options;
        private ArrayList<String> urls;

        SamplePagerAdapter(ArrayList<String> urls) {
            options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher);
            this.urls = urls;

        }

        @Override
        public int getCount() {
            return urls == null ? 0 : urls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            Uri uri = Uri.parse(urls.get(position));
            PhotoView photoView = new PhotoView(container.getContext());

            Glide.with(ActivityTransitionToActivity.this)
                    .load(uri)
                    .apply(options)
                    .into(photoView);

            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
