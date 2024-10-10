package com.example.task_6;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.MyViewHolder> {
    private ArrayList<Employee> myList;

    public MyArrayAdapter(ArrayList<Employee> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Employee employee = myList.get(position);
        holder.fullName.setText(employee.getName());

        if (employee.getPosition()) {
            holder.img.setVisibility(View.VISIBLE);
            holder.positionText.setVisibility(View.GONE);
        } else {
            holder.img.setVisibility(View.GONE);
            holder.positionText.setVisibility(View.VISIBLE);
        }

        // Đổi màu nền cho các item
        if (position % 2 == 1) {
            holder.linearLayout.setBackgroundColor(holder.linearLayout.getContext().getResources().getColor(R.color.light_blue));
        } else {
            holder.linearLayout.setBackgroundColor(holder.linearLayout.getContext().getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        ImageView img;
        TextView positionText;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.item_employee_tv_fullname);
            img = itemView.findViewById(R.id.item_employee_iv_manager);
            positionText = itemView.findViewById(R.id.item_employee_iv_position);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
