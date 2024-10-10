package com.example.task_5;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {
    Activity context;
    int IdLayout;
    ArrayList<Thumbnail> myList;

    public ThumbnailAdapter(Activity context, int idLayout, ArrayList<Thumbnail> myList) {
        super(context, idLayout, myList);
        this.context = context;
        IdLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = context.getLayoutInflater();
        convertView = myInflater.inflate(IdLayout, null);

        Thumbnail thumb = myList.get(position);

        TextView tv = convertView.findViewById(R.id.thumb_name);
        ImageView img =convertView.findViewById(R.id.thumb_pic);

        tv.setText(thumb.getThumbNum());
        img.setImageResource(thumb.getThumbPic());

        return convertView;
    }
}
