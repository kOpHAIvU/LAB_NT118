package com.example.lab03_iii;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentDetailActivity extends AppCompatActivity {
    private TextView tvDetailName, tvDetailAddress, tvDetailAge;
    private Button btnEdit, btnDelete;
    private DatabaseHelper db;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailAddress = findViewById(R.id.tvDetailAddress);
        tvDetailAge = findViewById(R.id.tvDetailAge);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        db = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("student_id", -1);
        Student sinhVien = db.getStudentById(studentId);
        if (sinhVien != null) {
            tvDetailName.setText(sinhVien.getName());
            tvDetailAddress.setText(sinhVien.getAddress());
            tvDetailAge.setText(String.valueOf(sinhVien.getAge()));
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDetailActivity.this, AddEditStudentActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            db.deleteStudent(studentId);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Student sinhVien = db.getStudentById(studentId);
        if (sinhVien != null) {
            tvDetailName.setText(sinhVien.getName());
            tvDetailAddress.setText(sinhVien.getAddress());
            tvDetailAge.setText(String.valueOf(sinhVien.getAge()));
        }
    }
}