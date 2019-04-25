package network.base.com.network.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.base.photo.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import network.base.com.network.R;

public class PhotoViewZoom extends AppCompatActivity {

    private PhotoView iv_photo;
    private ViewPager view_pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        iv_photo = findViewById(R.id.iv_photo);
        view_pager = findViewById(R.id.view_pager);
        //singlePhoto();
        viewPage();
        //glideShow();

    }

    //使用三方glide来显示
    private void glideShow(){
        view_pager.setVisibility(View.GONE);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(PhotoViewZoom.this)
                .load("http://img2.imgtn.bdimg.com/it/u=234634259,4236876085&fm=26&gp=0.jpg")//网络图片
                //.load(R.mipmap.ic_launcher)//本地图片
                .apply(options)
                .into(iv_photo);
    }

    //显示page
    private void viewPage(){
        iv_photo.setVisibility(View.GONE);
        view_pager.setAdapter(new SamplePagerAdapter());
    }

    //显示一张图片
    private void singlePhoto(){

        view_pager = findViewById(R.id.view_pager);
        //本地图片
        Drawable bitmap = ContextCompat.getDrawable(this, R.drawable.test1);
        iv_photo.setImageDrawable(bitmap);
    }



    static class SamplePagerAdapter extends PagerAdapter {

        private static final int[] sDrawables = {R.drawable.test1, R.drawable.test1, R.drawable.test1,
                R.drawable.test1, R.drawable.test1, R.drawable.test1};

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(sDrawables[position]);
            // Now just add PhotoView to ViewPager and return it
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
