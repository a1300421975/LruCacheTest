package com.example.tkw.lrucachetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tkw.lrucachetest.Presenter.BasePresenter;

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.detachView();
        }
    }
}
