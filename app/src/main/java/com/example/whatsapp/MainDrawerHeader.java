package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainDrawerHeader extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView textViewEmail;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        drawerLayout=(DrawerLayout) getLayoutInflater().inflate(R.layout.main_drawer_header,null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_header);




        auth=FirebaseAuth.getInstance();

        textViewEmail=findViewById(R.id.textViewEmail);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        if(user!=null){
            System.out.println(email);
            textViewEmail.setText(email);
        }
    }
}