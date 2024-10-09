package com.example.task_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<String> {
    Activity context;
    int IdLayout;
    ArrayList<String> myList;

    public MyArrayAdapter(Activity context, int idLayout, ArrayList<String> myList) {
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
        String name = myList.get(position);
        TextView tv = convertView.findViewById(R.id.text_view);
        tv.setText(name);
        return convertView;
    }
}
