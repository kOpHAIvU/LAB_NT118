package com.example.lab03_iii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> sinhVienList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public StudentAdapter(List<Student> sinhVienList, Context context, OnItemClickListener onItemClickListener) {
        this.sinhVienList = sinhVienList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onViewDetail(Student sinhVien);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student sinhVien = sinhVienList.get(position);
        holder.tvName.setText(sinhVien.getName());
        holder.tvAddress.setText(sinhVien.getAddress());
        holder.btnViewDetail.setOnClickListener(v -> onItemClickListener.onViewDetail(sinhVien));
    }

    @Override
    public int getItemCount() {
        return sinhVienList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;
        Button btnViewDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnViewDetail = itemView.findViewById(R.id.btnViewDetail);
        }
    }
}
