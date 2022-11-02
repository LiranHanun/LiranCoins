package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.whatsapp.databinding.ActivitySellCoinsAcivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SellCoinsAcivity extends DrawerBaseActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db;
    DatabaseReference creatorRef;

    ActivitySellCoinsAcivityBinding activitySellCoinsAcivityBinding;
    EditText coinValueET;
    Button sellCoin;

    Spinner spinnerr;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySellCoinsAcivityBinding=ActivitySellCoinsAcivityBinding.inflate(getLayoutInflater());
        setContentView(activitySellCoinsAcivityBinding.getRoot());
        allocateActivityTittle("SellCoins");


        coinValueET=findViewById(R.id.coinVlueET);
        sellCoin=findViewById(R.id.sellButton);

        spinnerr=findViewById(R.id.spinnerr);


        String[] userEmail = user.getEmail().split("@");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference creatorRef = db.child(String.valueOf(userEmail[0])).child("My Coins");
        list.add("Select Coin");
        creatorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp : snapshot.getChildren()) {
                    list.add(dsp.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(SellCoinsAcivity.this, android.R.layout.simple_list_item_activated_1, list);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerr.setAdapter(adapterr);


                sellCoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnerr.getSelectedItem().toString().equals("Select Coin") || coinValueET.getText().toString().equals("")) {
                            Toast.makeText(SellCoinsAcivity.this, "Please select coin/type Value", Toast.LENGTH_SHORT).show();

                        } else {
                            creatorRef.child(spinnerr.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    Map<String, String> map = (Map<String, String>) snapshot.getValue();
                                    double howMuchCoins = Double.parseDouble(map.get("HowMuchCoins"));
                                    double MoneySpend = Double.parseDouble(map.get("MoneySpend"));
                                    double coinValue = Double.parseDouble(coinValueET.getText().toString());
                                    double prof = coinValue * howMuchCoins - MoneySpend;
                                    DatabaseReference userDB = db.child(String.valueOf(userEmail[0]));
                                    userDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.hasChild("Profit")) {
                                                String profit = snapshot.child("Profit").getValue().toString();
                                                double newProfit = Double.parseDouble(profit) + prof;
                                                userDB.child("Profit").setValue(newProfit);
                                            } else {
                                                userDB.child("Profit").setValue(prof);
                                            }
                                            creatorRef.child(spinnerr.getSelectedItem().toString()).removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                    Toast.makeText(SellCoinsAcivity.this, "Coin Sold/Profit Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SellCoinsAcivity.this, Homepage.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });

    }

}