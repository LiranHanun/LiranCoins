package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

public class MainActivitySingUp extends AppCompatActivity {

    EditText Namesingup;
    EditText emailsingup;
    EditText Passwordsingup;
    Button singupsingup;
    TextView Login;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sing_up);


        emailsingup = findViewById(R.id.emailsingup);
        Passwordsingup = findViewById(R.id.Passwordsingup);
        singupsingup = findViewById(R.id.singupsingup);
        auth=FirebaseAuth.getInstance();

        Login=findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySingUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        singupsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailsingup.getText().toString().matches("") || Passwordsingup.getText().toString().matches("")) {
                    Toast.makeText(MainActivitySingUp.this,"Email or Password empty",
                            Toast.LENGTH_LONG).show();


                }else{
                    String userEmail = emailsingup.getText().toString();
                    String userPassword = Passwordsingup.getText().toString();
                    singUpFirebase(userEmail,userPassword);
                }





            }
        });
    }
        public void singUpFirebase(String userEmail,String userPassword) {

            auth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(MainActivitySingUp.this,"Accuont created",
                                Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(MainActivitySingUp.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivitySingUp.this,"There is a problem",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                        }

        }



