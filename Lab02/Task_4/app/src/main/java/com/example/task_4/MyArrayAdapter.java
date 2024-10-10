package com.example.task_4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Employee> {
    Activity context;
    int IdLayout;
    ArrayList<Employee> myList;

    public MyArrayAdapter(Activity context, int idLayout, ArrayList<Employee> myList) {
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
        Employee employee = myList.get(position);
        LinearLayout ln = convertView.findViewById(R.id.linear_layout);
        TextView fullName = convertView.findViewById(R.id.item_employee_tv_fullname);

        fullName.setText(employee.getName());
        if (employee.getPosition()) {
            ImageView img = convertView.findViewById(R.id.item_employee_iv_manager);
            img.setVisibility(View.VISIBLE);
        } else {
            TextView tv = convertView.findViewById(R.id.item_employee_iv_position);
            tv.setVisibility(View.VISIBLE);
        }
        if (position % 2 == 1)
            ln.setBackgroundColor(R.color.light_blue);

        return convertView;
    }
}
