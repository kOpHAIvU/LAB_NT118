package com.example.task1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    private static final int REQUEST_SMS_PERMISSION = 1;
    private PowerStateChangeReceiver powerStateChangeReceiver;

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

        checkSmsPermission();

        initBroadcastReceiver();

        powerStateChangeReceiver = new PowerStateChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(powerStateChangeReceiver, filter);
    }

    private void checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền nếu chưa được cấp
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    REQUEST_SMS_PERMISSION);
        }
    }

    private void initBroadcastReceiver() {
        // Tạo bộ lọc để lắng nghe các tin nhắn đến
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        // Tạo BroadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Xử lý khi có tin nhắn đến
                processReceive(context, intent);
            }
        };
    }

    private void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();

        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        // Lấy dữ liệu tin nhắn
        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
        if (messages == null) return;

        StringBuilder sms = new StringBuilder();

        SmsMessage smsMsg;
        for (Object message : messages) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                smsMsg = SmsMessage.createFromPdu((byte[]) message, "3gpp");
            } else {
                smsMsg = SmsMessage.createFromPdu((byte[]) message);
            }

            // Lấy nội dung tin nhắn
            String msgBody = smsMsg.getMessageBody();

            // Lấy địa chỉ (số điện thoại) gửi tin nhắn
            String address = smsMsg.getDisplayOriginatingAddress();

            sms.append(address).append(":\n").append(msgBody).append("\n");
        }

        // Hiển thị tin nhắn trong TextView
        tvContent.setText(sms.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Kiểm tra và khởi tạo BroadcastReceiver nếu chưa có
        if (broadcastReceiver == null) initBroadcastReceiver();

        // Đăng ký BroadcastReceiver
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Hủy đăng ký BroadcastReceiver
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted to receive SMS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied to receive SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký receiver khi Activity bị hủy
        if (powerStateChangeReceiver != null) {
            unregisterReceiver(powerStateChangeReceiver);
        }
    }
}