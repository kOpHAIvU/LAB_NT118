package com.example.task_5;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String thumbNum[] = {"" ,"Thumbnail 1", "Thumbnail 2", "Thumbnail 3", "Thumbnail 4"};
    int thumbPic[] = {0 ,R.drawable.first_thumbnail, R.drawable.second_thumbnail, R.drawable.third_thumbnail, R.drawable.forth_thumbnail};
    EditText foodName;
    Spinner thumbnail;
    Button addDish;
    CheckBox promotion;
    GridView gv;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;

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

        foodName = findViewById(R.id.food_name);
        thumbnail = findViewById(R.id.thumbnail);
        promotion = findViewById(R.id.promotion);
        gv = findViewById(R.id.gv);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, thumbNum);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thumbnail.setAdapter(spinnerAdapter);

        thumbnail.setSelection(0);

        thumbnail.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openThumbnailDialog();
                return true; // Ngăn ngừa sự kiện click tiếp theo
            }
            return false;
        });

        addDish = findViewById(R.id.add_dish);
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(MainActivity.this, R.layout.dish_item, foodList);
        gv.setAdapter(foodAdapter);
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodName.getText().toString().isEmpty() || thumbnail.getSelectedItemId() == 0) {
                    Toast.makeText(MainActivity.this, "Hãy nhập đủ các trường dữ liệu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Food newFood = new Food(foodName.getText().toString(), thumbPic[(int)thumbnail.getSelectedItemId()], promotion.isChecked());
                foodList.add(newFood);
                foodAdapter.notifyDataSetChanged();

                promotion.setChecked(false);
                thumbnail.setSelection(0);
                foodName.setText("");

            }
        });

    }

    private void openThumbnailDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_thumbnail_dialog);

        Window window = dialog.getWindow();
        if (window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        ListView thumbListView = dialog.findViewById(R.id.thumb_list);
        ThumbnailAdapter thumbnailAdapter;
        ArrayList<Thumbnail> arrayList = new ArrayList<>();

        for (int i = 0; i < thumbNum.length; i++) {
            arrayList.add(new Thumbnail(thumbNum[i], thumbPic[i]));
        }

        thumbnailAdapter = new ThumbnailAdapter(MainActivity.this, R.layout.thumbnail_item, arrayList);
        thumbListView.setAdapter(thumbnailAdapter);

        thumbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thumbnail selectedThumbnail = arrayList.get(position); // Lấy thumbnail đã chọn
                thumbnail.setSelection(position); // Cập nhật Spinner với vị trí đã chọn
                dialog.dismiss(); // Đóng dialog
            }
        });

        dialog.show();
    }
}