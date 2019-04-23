package com.example.zakweakland.lostandhound.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.zakweakland.lostandhound.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSetupActivity extends AppCompatActivity {

    private EditText firstName, email, location;
    private Button saveUserInformation;
    private CircleImageView profileImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setup);

        firstName = findViewById(R.id.setupFirstName);
        email = findViewById(R.id.setupEmail);
        location = findViewById(R.id.setupEmail);

        saveUserInformation = findViewById(R.id.setupSave);

        profileImage = findViewById(R.id.profile_image);

    }

}
