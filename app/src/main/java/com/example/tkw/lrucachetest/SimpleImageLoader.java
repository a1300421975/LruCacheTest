/*
package com.example.tkw.lrucachetest;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

public class SimpleImageLoader {

    private static SimpleImageLoader simpleImageLoader;

    private LruCache<String, Bitmap> lruCache;

    public static SimpleImageLoader getInstance(){
        if(simpleImageLoader == null){
            synchronized (SimpleImageLoader.class){
                if(simpleImageLoader == null){
                    simpleImageLoader = new SimpleImageLoader();

                }
            }
        }
        return simpleImageLoader;
    }


    private SimpleImageLoader(){
        int maxMamery = (int) Runtime.getRuntime().maxMemory() / 1024;
        int cacheSize = maxMamery / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }


    public void displayImage(ImageView imageView, String url){
        Bitmap bitmap =

    }




}
*/
