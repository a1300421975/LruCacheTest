package com.example.tkw.lrucachetest.View;

import android.graphics.Bitmap;
import android.view.View;

import com.example.tkw.lrucachetest.News.Stories;

import java.util.List;

public interface IGetData extends BaseView{

    void getStores(List<Stories> storiesList);

    void getBeforeStores(List<Stories> storiesList);

    void getRotationView(List<View> rotationViewList);

    void onFailed(Exception e);
}


