package com.example.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register, login, forgotPassword;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) this.findViewById(R.id.register);
        login = (TextView) this.findViewById(R.id.login);
        forgotPassword = (TextView) this.findViewById(R.id.forgotPassword);
        editTextEmail = (EditText) this.findViewById(R.id.email);
        editTextPassword = (EditText) this.findViewById(R.id.password);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                Intent intent_login = new Intent(MainActivity.this, RegisterApp.class);

                intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_login);
                finish();
                break;
            case R.id.login:
                funcLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                break;

        }
    }

    private void funcLogin() {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is reqired!");
            editTextEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            editTextPassword.setError("Pass is reqired!");
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email is reqired!");
            editTextEmail.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            editTextPassword.setError("Min pass length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user.isEmailVerified()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);


                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    user.sendEmailVerification();
                                    Toast.makeText(MainActivity.this, "Please let verify user!", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}