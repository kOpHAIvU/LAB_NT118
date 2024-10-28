package com.example.lab03_iii;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEditStudentActivity extends AppCompatActivity {
    private EditText etName, etAddress, etAge;
    private Button btnSave;
    private DatabaseHelper db;
    private int studentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etAge = findViewById(R.id.etAge);
        btnSave = findViewById(R.id.btnSave);
        db = new DatabaseHelper(this);

        // Kiểm tra nếu là chỉnh sửa sinh viên
        Intent intent = getIntent();
        if (intent.hasExtra("student_id")) {
            studentId = intent.getIntExtra("student_id", -1);
            Student sinhVien = db.getStudentById(studentId);  // Tạo phương thức getStudentById trong DatabaseHelper
            if (sinhVien != null) {
                etName.setText(sinhVien.getName());
                etAddress.setText(sinhVien.getAddress());
                etAge.setText(String.valueOf(sinhVien.getAge()));
            }
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            int age = Integer.parseInt(etAge.getText().toString().trim());

            Student sinhVien = new Student(studentId, name, address, age);
            if (studentId == -1) {
                db.addStudent(sinhVien); // Thêm mới
            } else {
                db.updateStudent(sinhVien); // Cập nhật
            }
            finish();
        });
    }
}