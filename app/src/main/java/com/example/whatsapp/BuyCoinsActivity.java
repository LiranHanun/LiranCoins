package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.whatsapp.databinding.ActivityBuyCoinsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BuyCoinsActivity extends DrawerBaseActivity {



    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    ActivityBuyCoinsBinding activityBuyCoinsBinding;

    Spinner spinner;
    List<String> list = new ArrayList<String>();

    CurrencyRVModal currencyRVModal;
    ArrayList<CurrencyRVModal> array=currencyRVModal.getArray();

    EditText moneySpendInput,coinValueInput;
    Button addButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBuyCoinsBinding = ActivityBuyCoinsBinding.inflate(getLayoutInflater());
        setContentView(activityBuyCoinsBinding.getRoot());
        allocateActivityTittle("BuyCoins");

        moneySpendInput = findViewById(R.id.moneySpendInput);
        coinValueInput = findViewById(R.id.coinValueInput);
        addButton = findViewById(R.id.addButton);


        spinner = findViewById(R.id.spinner);
        list.add("Select Coin");

        for (CurrencyRVModal item : array) {
            list.add(item.getSymbol());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list);
        spinner.setAdapter(adapter);
        String[] userEmail = user.getEmail().split("@");

        db = FirebaseDatabase.getInstance();
        reference = db.getReference(String.valueOf(userEmail[0]));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem().toString().equals("Select Coin") || moneySpendInput.getText().toString().equals("") || coinValueInput.getText().toString().equals("")) {
                    Toast.makeText(BuyCoinsActivity.this, "Please select coin/type Value", Toast.LENGTH_SHORT).show();

                } else {
                    String SymbalAdd = spinner.getSelectedItem().toString();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Value", coinValueInput.getText().toString());
                    map.put("MoneySpend", moneySpendInput.getText().toString());
                    double HowMuchCoins = Double.parseDouble(moneySpendInput.getText().toString()) / Double.parseDouble(coinValueInput.getText().toString());
                    map.put("HowMuchCoins", String.valueOf(HowMuchCoins));

                    reference.child("My Coins").child(SymbalAdd).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(BuyCoinsActivity.this, "Coin added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BuyCoinsActivity.this, Homepage.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });


    }


}
