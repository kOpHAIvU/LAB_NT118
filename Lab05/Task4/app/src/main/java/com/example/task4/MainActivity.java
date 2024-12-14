package com.example.task4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static boolean isRunning;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";
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

        findViewsByIds();
        initVariables();
        handleOnclickListener();
    }

    private void findViewsByIds() {
        swAutoResponse = (Switch) findViewById(R.id.sw_auto_response);
        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        lvMessages = (ListView) findViewById(R.id.lv_messages);
        btnSafe = (Button) findViewById(R.id.btn_safe);
        btnMayday = (Button) findViewById(R.id.btn_mayday);
    }

    private void respond(String to, String response)
    {
        reentrantLock.lock();
        requesters.remove(to);
        adapter.notifyDataSetChanged();
        reentrantLock.unlock();

        //send message
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(to,null,response,null,null);
    }

    public  void respond(boolean ok)
    {
        String okString = getString(R.string.i_am_safe_and_well);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok ? okString : notOkString;
        ArrayList<String> requesterCopy = (ArrayList<String>) requesters.clone();
        for (String to : requesterCopy)
            respond(to,outputString);
    }

    public void processReceiveAddresses(ArrayList<String> addresses)
    {
        for (int i = 0; i < addresses.size(); i++)
        {
            if (!requesters.contains(addresses.get(i)))
            {
                reentrantLock.lock();
                requesters.add(addresses.get(i));
                adapter.notifyDataSetChanged();
                reentrantLock.unlock();
            }

            if (swAutoResponse.isChecked())
                respond(true);
        }
    }

    private void handleOnclickListener()
    {
        btnSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respond(true);
            }
        });
        btnMayday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respond(false);
            }
        });
        swAutoResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (swAutoResponse.isChecked())
                    llButtons.setVisibility(View.GONE);
                else
                    llButtons.setVisibility(View.VISIBLE);

                //save auto response setting
                editor.putBoolean(AUTO_RESPONSE, swAutoResponse.isChecked());
                editor.commit();
            }
        });
    }

    private void initBroadcastReceiver()
    {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //get arraylist addresses
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);

                // process these addresses
                processReceiveAddresses(addresses);

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

        if (broadcastReceiver == null)
            initBroadcastReceiver();

        // register broadcastReceiver
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;

        unregisterReceiver(broadcastReceiver);
    }

    private void initVariables()
    {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,requesters);
        lvMessages.setAdapter(adapter);

        //Load auto response setting
        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE,false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse)
            llButtons.setVisibility(View.GONE);

        initBroadcastReceiver();

        //Send addresses to broadcastReceiver
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            ArrayList<String> passed_addresses = extras.getStringArrayList(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
            if (passed_addresses != null)
                processReceiveAddresses(passed_addresses);
        }
    }
}