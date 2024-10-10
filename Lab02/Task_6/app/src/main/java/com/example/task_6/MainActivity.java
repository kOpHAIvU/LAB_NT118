package com.example.task_6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    MyArrayAdapter myArrayAdapter;
    ArrayList<Employee> myArrayList;
    Button add;
    EditText id, name;
    CheckBox isMana;
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

        id = findViewById(R.id.employ_id);
        name = findViewById(R.id.employ_name);
        isMana = findViewById(R.id.employ_type);
        rv = findViewById(R.id.rv);

        myArrayList = new ArrayList<>();
        myArrayAdapter = new MyArrayAdapter(myArrayList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myArrayAdapter);

        add = findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hãy nhập đủ các trường dữ liệu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee emp = new Employee(id.getText().toString(), name.getText().toString(), isMana.isChecked());
                myArrayList.add(emp);
                myArrayAdapter.notifyDataSetChanged();

                id.setText("");
                name.setText("");
                isMana.setChecked(false);
            }
        });
    }
}