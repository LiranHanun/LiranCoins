package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whatsapp.databinding.ActivityBuyCoinsBinding;
import com.example.whatsapp.databinding.ActivityHomepage3Binding;
import com.example.whatsapp.databinding.ActivityMyAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityMyAccount extends DrawerBaseActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ActivityMyAccountBinding activityMyAccountBinding;

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<MyCoins> listlist;
    MyCoinsAdapter myCoinsAdapter;

    TextView myEmailInput,myProfitInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyAccountBinding = ActivityMyAccountBinding.inflate(getLayoutInflater());
        setContentView(activityMyAccountBinding.getRoot());
        allocateActivityTittle("My Account");

       myEmailInput=findViewById(R.id.myEmailInput);
       myProfitInput=findViewById(R.id.myProfitInput);

       myEmailInput.setText(user.getEmail());




        recyclerView=findViewById(R.id.RVmyCoins);

        listlist=new ArrayList<>();
        myCoinsAdapter=new MyCoinsAdapter(listlist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myCoinsAdapter);


        String[] userEmail = user.getEmail().split("@");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference creatorRef = databaseReference.child(String.valueOf(userEmail[0])).child("My Coins");
        creatorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String nameCoins=dataSnapshot.getKey();
                    String howMuchCoins=snapshot.child(dataSnapshot.getKey()).child("HowMuchCoins").getValue().toString();
                    String value=snapshot.child(dataSnapshot.getKey()).child("Value").getValue().toString();
                    listlist.add(new MyCoins(nameCoins,howMuchCoins,value));
                }
                myCoinsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference userDB = databaseReference.child(String.valueOf(userEmail[0]));
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Profit")) {
                    if(Double.parseDouble(snapshot.child("Profit").getValue().toString())<0){
                        myProfitInput.setTextColor(Color.parseColor("#FF0000"));
                    }else{
                        myProfitInput.setTextColor(Color.parseColor("#008000"));
                    }
                    if(snapshot.child("Profit").getValue().toString().length()>9) {
                        myProfitInput.setText("$" + snapshot.child("Profit").getValue().toString().substring(0, 9));
                    }else{
                        myProfitInput.setText("$" + snapshot.child("Profit").getValue().toString());
                    }
                } else {
                    myProfitInput.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}