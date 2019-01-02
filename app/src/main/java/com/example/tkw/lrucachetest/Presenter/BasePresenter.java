package com.example.tkw.lrucachetest.Presenter;

import android.content.Context;

import com.example.tkw.lrucachetest.View.BaseView;

public class BasePresenter<T extends BaseView>{

    protected T view;

    protected Context mContext;


    public BasePresenter(Context context,T t){
        attachView(context,t);
    }

    public void attachView(Context context,T t){
        this.view = t;
        this.mContext = context;
    }

    public void detachView(){
        this.view = null;
        this.mContext = null;
    }



}
