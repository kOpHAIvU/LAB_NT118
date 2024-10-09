package com.example.task_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task_3.Class.Employee;
import com.example.task_3.Class.EmployeeFullTime;
import com.example.task_3.Class.EmployeeParttime;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Employee> myList;
    MyArrayAdapter myArrayAdapter;
    ListView lv;
    Button button;
    EditText employeeId, employeeName;
    RadioButton fulltime, parttime;
    RadioGroup employType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv = findViewById(R.id.lv);
        employeeId = findViewById(R.id.employId);
        employeeName = findViewById(R.id.employName);
        button = findViewById(R.id.button);
        fulltime = findViewById(R.id.fulltimeEmploy);
        parttime = findViewById(R.id.parttimeEmploy);
        employType = findViewById(R.id.employType);

        myList = new ArrayList<Employee>();

        myArrayAdapter = new MyArrayAdapter(MainActivity.this, R.layout.list_view_item, myList);
        lv.setAdapter(myArrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmployee();
            }
        });
    }

    public void addNewEmployee() {
        Employee employee;
        if (employeeId.getText().toString().isEmpty()
                || employeeName.getText().toString().isEmpty()
                || (!fulltime.isChecked() && !parttime.isChecked())) {
            Toast.makeText(MainActivity.this, "Hãy nhập đủ các trường dữ liệu!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (parttime.isChecked())
            employee = new EmployeeParttime();
        else
            employee = new EmployeeFullTime();

        employee.setEmployId(employeeId.getText().toString());
        employee.setEmployName(employeeName.getText().toString());

        myList.add(employee);

        myArrayAdapter.notifyDataSetChanged();

        // Clear data
        employeeId.setText("");
        employeeName.setText("");
        employType.clearCheck();
    }
}