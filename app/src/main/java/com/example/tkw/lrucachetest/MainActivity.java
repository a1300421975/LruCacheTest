/*
package com.example.tkw.lrucachetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

package com.example.tkw.lrucachetest;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tkw.lrucachetest.Adapter.ViewPagerAdapter;
import com.example.tkw.lrucachetest.CallBack.DateCallBack;
import com.example.tkw.lrucachetest.CallBack.ImageCallBack;
import com.example.tkw.lrucachetest.GetData.GetImage;
import com.example.tkw.lrucachetest.News.DateNews;
import com.example.tkw.lrucachetest.GetData.GetStories;
import com.example.tkw.lrucachetest.News.Stories;
import com.example.tkw.lrucachetest.Adapter.StoriesAdapter;
import com.example.tkw.lrucachetest.Presenter.MainPresenter;
import com.example.tkw.lrucachetest.View.IGetData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements IGetData {

    RecyclerView storiesRecyclerView = null;

    StoriesAdapter storiesAdapter = null;

    final String Url = "https://news-at.zhihu.com/api/4/news/";

    LinearLayoutManager layoutManager = null;

    android.text.format.Time time = new android.text.format.Time("GTM+8");

    boolean canChange = true;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this,this);

        init();
        ScrollBottom();
    }

    /**
     * 初始化数据
     */
    public void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        presenter.getStore(Url + "latest");
        storiesRecyclerView = (RecyclerView) findViewById(R.id.stories_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        storiesRecyclerView.setLayoutManager(layoutManager);
        storiesAdapter = new StoriesAdapter(this);

    }
    /**
     * 滑动到底部，加载数据
     */
    private void ScrollBottom(){
        storiesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!storiesRecyclerView.canScrollVertically(1)) {
                        presenter.getBeforeStories();
                    }
                }
            }
        });
    }

    @Override
    public void getStores(final List<Stories> storiesList) {
        if (canChange) {
            canChange = false;
            storiesAdapter.setStoriesList(storiesList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    storiesRecyclerView.setAdapter(storiesAdapter);
                }
            });
        }
    }

    @Override
    public void getBeforeStores(final List<Stories> storiesList) {
        if(!canChange){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    storiesAdapter.notifyItemChanged(storiesList.size() - 1);
                }
            });
        }
    }

    @Override
    public void getRotationView(final List<View> rotationViewList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(rotationViewList);
                viewPager.setAdapter(viewPagerAdapter);
            }
        });
    }

    @Override
    public void onFailed(Exception e) {

    }
}

