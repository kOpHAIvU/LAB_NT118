package com.example.task_5;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {
    Activity context;
    int IdLayout;
    ArrayList<Food> myList;

    public FoodAdapter(Activity context, int idLayout, ArrayList<Food> myList) {
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

        Food food = myList.get(position);

        TextView tv = convertView.findViewById(R.id.name);
        ImageView img = convertView.findViewById(R.id.image);
        ImageView promotion = convertView.findViewById(R.id.promotion);

        tv.setText(food.getName());
        img.setImageResource(food.getThumbnail());
        if (!food.getPromotion())
            promotion.setVisibility(View.GONE);

        return convertView;
    }
}
