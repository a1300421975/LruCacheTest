package com.example.tkw.lrucachetest.GetData;

import android.content.Context;

import com.example.tkw.lrucachetest.CallBack.ContentCallBack;
import com.example.tkw.lrucachetest.CallBack.DateCallBack;
import com.example.tkw.lrucachetest.News.DateNews;
import com.example.tkw.lrucachetest.News.Stories;
import com.example.tkw.lrucachetest.SQL.DataBaseOperation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetStories {

    private static volatile GetStories getStories;

    private Context mContext;

    private GetStories(Context context){
        mContext = context;
    }

    public static GetStories ReturnStories(Context context){
        if(getStories == null){
            synchronized (GetStories.class){
                if(getStories == null){
                    getStories = new GetStories(context);
                }
            }
        }
        return getStories;
    }

    public void sendRequestWithHttpURLConnection(final String Url, final DateCallBack dateCallBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(Url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8 * 1000);
                    connection.setReadTimeout(8 * 1000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    parseJSONWithJSONObject(response.toString(), dateCallBack);
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(reader != null){
                        try{
                            reader.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 获取内容
     * @param Url
     * @param contentCallBack
     */
    private void sendRequestWithHttpURLConnectionToGetContent(final String Url, final ContentCallBack contentCallBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(Url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8 * 1000);
                    connection.setReadTimeout(8 * 1000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    parseJSONWithJSONObjectWithContent(response.toString(), contentCallBack);
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(reader != null){
                        try{
                            reader.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 解析内容的网址
     * @param jsonData
     * @param contentCallBack
     */
    private void parseJSONWithJSONObjectWithContent(String jsonData,ContentCallBack contentCallBack){
        try {
            JSONObject object = new JSONObject(jsonData);
            String storiesAddress = object.getString("share_url");
            contentCallBack.contentCallBack(storiesAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析数据
     * @param jsonData
     * @param dateCallBack
     */
    public void parseJSONWithJSONObject(String jsonData, DateCallBack dateCallBack){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date1 = new Date();
        int dates = Integer.parseInt(simpleDateFormat.format(date1));
        try {
            JSONObject object = new JSONObject(jsonData);
            int date = object.getInt("date");   //获取日期

            JSONArray storiesJSONArray = object.getJSONArray("stories");
            List<Stories>  storiesList = new ArrayList<>();   //stories集合
            //添加stories成员
            for(int i = 0; i < storiesJSONArray.length();i++){
                JSONObject storiesObject = storiesJSONArray.getJSONObject(i);
                final Stories stories = new Stories();

                int id = storiesObject.getInt("id");
                final String[] contentUrls = new String[1];
                String title = storiesObject.getString("title");
                String imagesAddress = "";
                sendRequestWithHttpURLConnectionToGetContent("https://news-at.zhihu.com/api/4/news/" + id, new ContentCallBack() {
                    @Override
                    public void contentCallBack(String contentUrl) {
                        contentUrls[0] = contentUrl;
                        stories.setStories_address(contentUrls[0]);
                    }
                });

                JSONArray imagesArray = storiesObject.getJSONArray("images");

                for(int j = 0;j < imagesArray.length();j++){
                    imagesAddress = imagesArray.getString(j);
                }
                if(dates != date){
                    DataBaseOperation.GetInstance(mContext).insert(id,imagesAddress,title,date,contentUrls[0]);
                }
                stories.setId(id);
                stories.setDate(date);
                stories.setImages(imagesAddress);
                stories.setTitle(title);
                storiesList.add(stories);
            }
            DateNews.ReturnDateNews().addStories(storiesList);

            if(dates == date) {
                JSONArray topStoriesJSONArray = object.getJSONArray("top_stories");
                List<Stories> topStoriesList = new ArrayList<>();  //top_stories集合
                //添加stories成员
                for (int i = 0; i < topStoriesJSONArray.length(); i++) {
                    JSONObject topStoriesObject = topStoriesJSONArray.getJSONObject(i);
                    final Stories topStories = new Stories();
                    int top_id = topStoriesObject.getInt("id");
                    topStories.setId(top_id);
                    sendRequestWithHttpURLConnectionToGetContent("https://news-at.zhihu.com/api/4/news/" + top_id, new ContentCallBack() {
                        @Override
                        public void contentCallBack(String contentUrl) {
                            topStories.setStories_address(contentUrl);
                        }
                    });
                    String topImageAddress = topStoriesObject.getString("image");
                    topStories.setImages(topImageAddress);
                    topStories.setTitle(topStoriesObject.getString("title"));
                    topStoriesList.add(topStories);
                }
                DateNews.ReturnDateNews().setTop_stories(topStoriesList);
            }

            dateCallBack.dateListCallBack(DateNews.ReturnDateNews());
            //BuildConfig.BUILD_TIMESTAMP;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
