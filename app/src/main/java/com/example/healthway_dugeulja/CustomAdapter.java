package com.example.healthway_dugeulja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private ArrayList<Video> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<Video> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_video, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getThumbnail())
                .into(holder.iv_thumbnail);
        holder.tv_title.setText(arrayList.get(position).getVideo_name());
        holder.tv_url.setText(arrayList.get(position).getVideo_name());


    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_thumbnail;
        TextView tv_title;
        TextView tv_url;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_thumbnail = itemView.findViewById(R.id.iv_thumbnail);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_title = itemView.findViewById(R.id.tv_url);

        }
    }
}
