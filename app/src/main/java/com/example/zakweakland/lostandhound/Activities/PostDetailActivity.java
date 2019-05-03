package com.example.zakweakland.lostandhound.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zakweakland.lostandhound.Adapters.CommentAdapter;
import com.example.zakweakland.lostandhound.Models.Comment;
import com.example.zakweakland.lostandhound.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {

    ImageView imgPost;
    TextView postDogName, postDogBreed, postDogAge, postDesc, postDateName;
    EditText postComment;
    Button postCommentBtn;
    String postKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView rvComment;
    CommentAdapter commentAdapter;
    List<Comment> commentList;
    static String COMMENT_KEY = "Comment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // set statue bar to transparent
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        rvComment = findViewById(R.id.rv_comment);
        imgPost = findViewById(R.id.postDetailImage);
        postDogName = findViewById(R.id.postDetailDogName);
        postDogBreed = findViewById(R.id.postDetailBreed);
        postDogAge = findViewById(R.id.postDetailAge);
        postDateName = findViewById(R.id.postDetailTimestamp);
        postDesc = findViewById(R.id.postDetailDesc);
        postComment = findViewById(R.id.postDetailCommentText);
        postCommentBtn = findViewById(R.id.postDetailCommentBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // comment button listener
        postCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCommentBtn.setVisibility(View.INVISIBLE);
                DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey).push();
                String commentContent = postComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                Comment comment = new Comment(commentContent, uid, uname);

                commentRef.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("Comment Added");
                        postComment.setText("");
                        postCommentBtn.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Failed To Add Comment : "+e.getMessage());
                    }
                });
            }
        });

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
        postKey = getIntent().getExtras().getString("key");
        String postTimestamp = timestampToString(getIntent().getExtras().getLong("timestamp"));
        postDateName.setText(postTimestamp);

        // ini Comment RV
        iniRvComment();



    }

    private void iniRvComment() {

        rvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey);

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList = new ArrayList<>();
                for (DataSnapshot commentSnap : dataSnapshot.getChildren()){
                    Comment comment = commentSnap.getValue(Comment.class);
                    commentList.add(comment);
                }

                commentAdapter = new CommentAdapter(getApplicationContext(),commentList);
                rvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private String timestampToString(long time){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }

    private void showMessage(String text){

        Toast.makeText(this,text,Toast.LENGTH_LONG).show();

    }

}
