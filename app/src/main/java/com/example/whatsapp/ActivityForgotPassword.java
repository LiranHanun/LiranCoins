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
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {

    EditText emailInput;
    Button sendEmail;
    TextView CreateAccountTV,loginTV;

    FirebaseAuth aauth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput=findViewById(R.id.emailInput);
        sendEmail=findViewById(R.id.sendEmail);
        CreateAccountTV=findViewById(R.id.CreateAccountTV);
        loginTV=findViewById(R.id.loginTV);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailInput.getText().toString().matches("")){
                    Toast.makeText(ActivityForgotPassword.this, "Email is empty", Toast.LENGTH_LONG).show();
                }else {
                    aauth.sendPasswordResetEmail(emailInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ActivityForgotPassword.this, "Email Send", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ActivityForgotPassword.this,
                                        MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ActivityForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        CreateAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityForgotPassword.this,
                        MainActivitySingUp.class);
            }
        });
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityForgotPassword.this,
                        MainActivity.class);
            }
        });
    }
}