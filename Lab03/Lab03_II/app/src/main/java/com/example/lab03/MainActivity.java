package com.example.lab03;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView contactListView;
    private ArrayAdapter<String> adapter;
    private List<Contact> contactList;
    private DatabaseHandler db;
    private List<String> contactDisplayList;

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

        contactListView = findViewById(R.id.contact_list_view);
        db = new DatabaseHandler(this);

        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Load and display contacts
        loadContacts();

        // Set up long-click listener for deleting contacts
        contactListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Contact contact = contactList.get(position);
            db.deleteContact(contact); // Xóa contact khỏi database
            Toast.makeText(MainActivity.this, "Deleted: " + contact.getName(), Toast.LENGTH_SHORT).show();
            loadContacts(); // Tải lại danh sách sau khi xóa
            return true;
        });
    }

    private void loadContacts() {
        // Lấy tất cả các liên hệ từ cơ sở dữ liệu
        contactList = db.getAllContacts();
        contactDisplayList = new ArrayList<>();

        Log.e("Reading: ", "Reading all contacts..");
        // Chuẩn bị dữ liệu để hiển thị trong ListView
        for (Contact cn : contactList) {
            String contactInfo = "Name: " + cn.getName() + "\nPhone: " + cn.getPhoneNumber();
            contactDisplayList.add(contactInfo);

            // Ghi từng liên hệ vào log
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() + ", Phone: " + cn.getPhoneNumber();
            Log.e("Name: ", log);
        }

        // Thiết lập adapter và gán vào ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactDisplayList);
        contactListView.setAdapter(adapter);
    }
}