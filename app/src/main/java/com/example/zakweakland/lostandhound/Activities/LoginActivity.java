package com.example.zakweakland.lostandhound.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zakweakland.lostandhound.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Button buttonLogin, forgotPassword, buttonRegister;
    private EditText loginEmail, loginPassword;
    private FirebaseAuth firebaseAuth;
    private Intent homeActivity, registerActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        // dialog
        progressDialog = new ProgressDialog(this);

        //buttons
        buttonLogin = findViewById(R.id.login);
        forgotPassword = findViewById(R.id.forgotPassword);
        buttonRegister = findViewById(R.id.registerFromLogin);

        // init button click
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        // text fields
        loginEmail = findViewById(R.id.emailLogin);
        loginPassword = findViewById(R.id.passwordLogin);

        // firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //activities
        homeActivity = new Intent(this, HomeActivity.class);
        registerActivity = new Intent(this, RegisterActivity.class);
    }


    @Override
    public void onClick(View v) {
        if (v == buttonLogin){
            // login into account - go to main page
            updateUser();
        }
        if (v == buttonRegister){
            startActivity(registerActivity);
        }
    }

    private void updateUser() {
        final String email = loginEmail.getText().toString();
        final String password = loginPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            showMessage("Please Fill Out All Fields");
        } else {
            signIn(email,password);
        }
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.setMessage("Registering... Please wait...");
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 1500);
                            updateUI();
                        } else {
                            showMessage(task.getException().getMessage());
                        }
                    }
                });
    }

    private void updateUI() {
        showMessage("Login Successful");
        startActivity(homeActivity);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            updateUI();
        }
    }
}
