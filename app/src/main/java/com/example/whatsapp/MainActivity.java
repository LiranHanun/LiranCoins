package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public static Settings s=new Settings("5m","1%");
    EditText email;
    EditText Password;
    Button login;
    TextView singup;
    TextView ForgotpasswordTV;


    FirebaseAuth aauth= FirebaseAuth.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        login = findViewById(R.id.login);
        singup = findViewById(R.id.singup);
        ForgotpasswordTV=findViewById(R.id.ForgotpasswordTV);

        s.setTime("5m");
        s.setPercent("1%");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().matches("") || Password.getText().toString().matches("")) {
                    Toast.makeText(MainActivity.this, "Email or Password is empty", Toast.LENGTH_LONG).show();
                } else {
                    String uEmail = email.getText().toString();
                    String uPassword = Password.getText().toString();
                    aauth.signInWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Account Correct",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this,
                                        Homepage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "account not exist",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            }
        });
        ForgotpasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ActivityForgotPassword.class);
                startActivity(intent);
            }
        });




        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,MainActivitySingUp.class);
                startActivity(intent);
            }
        });



        }


    }
