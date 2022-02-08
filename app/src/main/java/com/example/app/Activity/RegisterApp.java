package com.example.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterApp extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser, reg_login_ima;
    private EditText editTextFullName, editTextPhone, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private ImageView reg_back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_app);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (TextView) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        reg_back = (ImageView) findViewById(R.id.reg_back);
        reg_back.setOnClickListener(this);
        reg_login_ima = (TextView) findViewById(R.id.reg_login_ima);
        reg_login_ima.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextPhone = (EditText) findViewById(R.id.phone);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_back:
                reg_back();  // function call back prevous Intent
                break;

            case R.id.reg_login_ima:
                reg_login_ima();
                break;

            case R.id.registerUser:
                registerUser();
                break;
        }
    }
    private void reg_login_ima(){
        Intent intent2 = new Intent(RegisterApp.this, MainActivity.class);
        startActivity(intent2);
        finish();
    }
    private void reg_back(){
        Intent intent = new Intent(RegisterApp.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void registerUser() {

        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (fullName.isEmpty()) {
            editTextFullName.setError("Name is reqired!");
            editTextFullName.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            editTextPhone.setError("Name is reqired!");
            editTextPhone.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is reqired!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Pass is reqired!");
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email is reqired!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min pass length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User user = new User(fullName, phone, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterApp.this, "User has been registered successfully, please verify your email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        Intent intent3 = new Intent(RegisterApp.this, MainActivity.class);
                                        startActivity(intent3);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterApp.this, "Failed to register User!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegisterApp.this, "Failed to register User !", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}
