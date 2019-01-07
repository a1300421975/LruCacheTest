package com.example.tkw.lrucachetest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tkw.lrucachetest.GetData.GetImage;
import com.example.tkw.lrucachetest.CallBack.ImageCallBack;
import com.example.tkw.lrucachetest.News.Stories;
import com.example.tkw.lrucachetest.R;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<Stories> storiesList;

    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View storiesView;
        ImageView storiesImage;
        TextView storiesTitle;

        public ViewHolder(View view){
            super(view);
            storiesView = view;
            storiesImage = (ImageView) view.findViewById(R.id.title_image_view);
            storiesTitle = (TextView) view.findViewById(R.id.title_text_view);
        }
    }

    public StoriesAdapter(Context context){
        this.context = context;

    }

    public void setStoriesList(List<Stories> storiesList) {
        this.storiesList = storiesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stories_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.storiesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent("com.example.activitytest.ACTION_START");
                intent.putExtra("stories_url",storiesList.get(position).getStories_address());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        Stories stories = storiesList.get(position);
        viewHolder.storiesTitle.setText(stories.getTitle());
        GetImage.ReturnImage().sendRequestWithHttpURLConnection(stories.getImages(), new ImageCallBack() {
            @Override
            public void imageCallBack(final Bitmap bitmap) {
                viewHolder.storiesImage.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.storiesImage.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }
}
