package com.example.tkw.lrucachetest.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tkw.lrucachetest.CallBack.DateCallBack;
import com.example.tkw.lrucachetest.CallBack.ImageCallBack;
import com.example.tkw.lrucachetest.GetData.GetImage;
import com.example.tkw.lrucachetest.GetData.GetStories;
import com.example.tkw.lrucachetest.News.DateNews;
import com.example.tkw.lrucachetest.News.Stories;
import com.example.tkw.lrucachetest.R;
import com.example.tkw.lrucachetest.View.IGetData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPresenter extends BasePresenter<IGetData> {

    private static int date = 0;

    private boolean isFirst = true;

    int time = 0;

    final String Url = "https://news-at.zhihu.com/api/4/news/";

    public MainPresenter(Context context, IGetData iGetData) {
        super(context, iGetData);
    }

    private void getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        this.date = Integer.parseInt(simpleDateFormat.format(date));
    }

    public void getBeforeStories(){
        if(isFirst){
            getDate();
            isFirst = false;
        }
        time++;
        GetStories.ReturnStories().sendRequestWithHttpURLConnection(Url + "before/" + (--date), new DateCallBack() {
            @Override
            public void dateListCallBack(DateNews dateNews) {
                view.getBeforeStores(dateNews.getStories());
            }
        });

    }

    public void getStore(String url) {
        GetStories.ReturnStories().sendRequestWithHttpURLConnection(url, new DateCallBack() {
            @Override
            public void dateListCallBack(DateNews dateNews) {
                final List<View> rotationViewList = new ArrayList<>();
                for(int i = 0;i < dateNews.getTop_stories().size();i++){
                    View view = LayoutInflater.from(mContext).inflate(R.layout.rotation_image,null);
                    final ImageView imageView = (ImageView) view.findViewById(R.id.rotation_image_view);
                    TextView textView = (TextView) view.findViewById(R.id.rotation_text_view);
                    textView.setText(dateNews.getTop_stories().get(i).getTitle());
                    GetImage.ReturnImage().sendRequestWithHttpURLConnection(dateNews.getTop_stories().get(i).getImages(), new ImageCallBack() {
                        @Override
                        public void imageCallBack(final Bitmap bitmap) {
                            imageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    });
                    rotationViewList.add(view);
                }
                view.getRotationView(rotationViewList);
                view.getStores(dateNews.getStories());
            }
        });
    }
}
