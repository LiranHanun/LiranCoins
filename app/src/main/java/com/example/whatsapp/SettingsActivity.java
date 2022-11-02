package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivityBuyCoinsBinding;
import com.example.whatsapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends DrawerBaseActivity {


    ActivitySettingsBinding activitySettingsBinding;

    String[] timeSpinner={"Select","5m","10m","15m"};
    String[] percentSpinner={"Select","1%","2%","3%","4%","5%"};
    Spinner spinnerTime,spinnerPercent;
    TextView theTimeNow,thePercentNow;
    Button SaveBTN;
    Settings s=MainActivity.s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());
        allocateActivityTittle("Settings");



        spinnerTime=findViewById(R.id.spinnerTime);
        spinnerPercent=findViewById(R.id.spinnerPercent);
        theTimeNow=findViewById(R.id.theTimeNow);
        thePercentNow=findViewById(R.id.thePercentNow);
        SaveBTN=findViewById(R.id.SaveBTN);

        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, timeSpinner);
        spinnerTime.setAdapter(adapterTime);

        ArrayAdapter<String> adapterPercent = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, percentSpinner);
        spinnerPercent.setAdapter(adapterPercent);



        thePercentNow.setText(s.getPercent());
            theTimeNow.setText(s.getTime());


        
        SaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerPercent.getSelectedItem()!="Select" && spinnerTime.getSelectedItem()!="Select"){
                    s.setPercent(spinnerPercent.getSelectedItem().toString());
                    s.setTime(spinnerTime.getSelectedItem().toString());
                    theTimeNow.setText(s.getTime());
                    thePercentNow.setText(s.getPercent());
                }else{


                    Toast.makeText(SettingsActivity.this,"Select time/percent",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}