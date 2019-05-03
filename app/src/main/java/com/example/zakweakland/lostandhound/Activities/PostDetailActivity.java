package com.example.zakweakland.lostandhound.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zakweakland.lostandhound.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {

    ImageView imgPost;
    TextView postDogName, postDogBreed, postDogAge, postDesc, postDateName;
    EditText postComment;
    Button postCommentBtn;
    String postKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // set statue bar to transparent
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        imgPost = findViewById(R.id.postDetailImage);
        postDogName = findViewById(R.id.postDetailDogName);
        postDogBreed = findViewById(R.id.postDetailBreed);
        postDogAge = findViewById(R.id.postDetailAge);
        postDateName = findViewById(R.id.postDetailTimestamp);
        postDesc = findViewById(R.id.postDetailDesc);
        postComment = findViewById(R.id.postDetailCommentText);
        postCommentBtn = findViewById(R.id.postDetailCommentBtn);

        // bind all data into views
        // get post data

        String postImage = getIntent().getExtras().getString("image");
        Glide.with(this).load(postImage).into(imgPost);
        String postName = getIntent().getExtras().getString("name");
        postDogName.setText(postName);
        String postBreed = getIntent().getExtras().getString("breed");
        postDogBreed.setText(postBreed);
        String postAge = getIntent().getExtras().getString("age");
        postDogAge.setText(getResources().getString(R.string.dogAge, postAge));
        String postAdditionalInfo = getIntent().getExtras().getString("desc");
        postDesc.setText(postAdditionalInfo);
        String postKey = getIntent().getExtras().getString("key");
        String postTimestamp = getIntent().getExtras().getString("timestamp");
        postDateName.setText(postTimestamp);



    }

    private String timestampToString(long time){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }

}
