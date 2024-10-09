package com.example.task_2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String name[] = {"Tèo", "Tý", "Bin", "Bo"};
    ArrayList<String> myList;
    MyArrayAdapter myArrayAdapter;
    ListView lv;
    TextView text_view;
    Button button;
    EditText edit_text;
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
        text_view = findViewById(R.id.text_view);
        edit_text = findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        myList = new ArrayList<>();
        for (int i = 0; i < name.length; i++)
            myList.add(name[i]);
        myArrayAdapter = new MyArrayAdapter(MainActivity.this, R.layout.list_view_item, myList);

        lv.setAdapter(myArrayAdapter);

        lv.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                text_view.setText("Lê Hoàng Vũ - 22521691\nposition: " + position + ", value = " + myList.get(position));
            }
        }));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_text.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hãy nhập tên vào",Toast.LENGTH_SHORT).show();
                    return;
                }
                myList.add(edit_text.getText().toString());
                edit_text.setText("");
                myArrayAdapter.notifyDataSetChanged();
            }
        });

    }
}
