package com.example.zakweakland.lostandhound.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zakweakland.lostandhound.R;


public class Splash_Screen_Activity extends AppCompatActivity {

    private static int SPLASH_TIME = 3000;
    TextView welcome;
    ImageView logo;
    Animation fromBottom, fromTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        welcome = (TextView) findViewById(R.id.splashWelcome);
        logo = (ImageView) findViewById(R.id.splashLogo);

        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        logo.setAnimation(fromTop);
        welcome.setAnimation(fromBottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(Splash_Screen_Activity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_TIME);
    }
}
