package com.example.zakweakland.lostandhound.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Button buttonRegister;
    private Button alreadyRegisterLogin;
    private EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // dialog
        progressDialog = new ProgressDialog(this);

        // buttons
        buttonRegister = findViewById(R.id.registerButton);
        alreadyRegisterLogin = findViewById(R.id.alreadylogin);

        // text fields
        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        // init button click
        buttonRegister.setOnClickListener(this);
        alreadyRegisterLogin.setOnClickListener(this);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();


    }


    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String firstName = editTextFirstName.getText().toString();
        final String lastName = editTextLastName.getText().toString();

        if (TextUtils.isEmpty(firstName) || (TextUtils.isEmpty(lastName))){
            Toast.makeText(this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            // email is empty
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registering... Please wait...");
        progressDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 1500);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            updateUserInfo(firstName, lastName, firebaseAuth.getCurrentUser());

                        }
                        if(!task.isSuccessful()){
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    // update user first name and last name
    private void updateUserInfo(String firstName, String lastName, FirebaseUser currentUser) {

//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child()

        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(firstName)
                .build();

        currentUser.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister){
            registerUser();
        }
        if (v == alreadyRegisterLogin){
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

    }
}

