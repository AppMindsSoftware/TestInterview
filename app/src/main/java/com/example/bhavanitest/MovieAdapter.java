package com.example.bhavanitest;

import android.content.Context;
;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by ankit on 27/10/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Model> list;

    public MovieAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model movie = list.get(position);

        holder.textTitle.setText(movie.getMovie_title());
     // holder.image.setText(String.valueOf(movie.getMovie_title()));


        String imageUri = movie.getMovie_thumbnail_image();

        Picasso.with(context).load(imageUri).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }
    }

}