package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mUsername,mEmail,mPassword,mconfirm_password;
    Button mRegisterBtn;
    TextView mloginhere;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername           = findViewById(R.id.Username);
        mEmail              = findViewById(R.id.Email);
        mPassword           = findViewById(R.id.Password);
        mconfirm_password   = findViewById(R.id.confirm_password);
        mRegisterBtn        = findViewById(R.id.RegisterBtn);
        mloginhere          = findViewById(R.id.loginhere);

        fAuth               = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser()  !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirm_password = mconfirm_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Requaired.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Requaired.");
                    return;
                }

                if (TextUtils.isEmpty(confirm_password)){
                    mconfirm_password.setError("confirm_password is requaired.");
                    return;
                }





                if(password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 characters");
                    return;
                }

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        mloginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }
}