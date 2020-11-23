package com.rvtech.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashScreen extends AppCompatActivity {
    //Initialize variable
    ImageView ivTop,ivBottom,ivHeart,ivbeat;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay=200;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Asign Variable
        ivTop=findViewById(R.id.top_vector);
        ivBottom=findViewById(R.id.iv_bottom);
        ivHeart=findViewById(R.id.ic_heart);
        ivbeat=findViewById(R.id.iv_beats);
        textView=findViewById(R.id.textview);
        //Full window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize top animation
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.top_wave);
        ivTop.setAnimation(animation);
        //Initialize Object Animator
        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(ivHeart,
                PropertyValuesHolder.ofFloat("ScaleX",1.2f),
                PropertyValuesHolder.ofFloat("ScaleY",1.2f));
        //Set Duration
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //Set Repeat Mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //start Animation
        objectAnimator.start();
        //set animate text
        animatText("Heart Beat");
        //load gif
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b").asGif()
        .diskCacheStrategy(DiskCacheStrategy.ALL).into(ivbeat);

        //Initialize Bottom ANimation
        Animation animation1 =AnimationUtils.loadAnimation(this,R.anim.bottom);
        //start bottom animation
        ivBottom.setAnimation(animation1);

        //initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                //finish activity
                finish();
            }
        },4000);
            }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if (index <= charSequence.length())
            {
                handler.postDelayed(runnable,delay);
            }
        }
    };
    //Create Animated Text Method
    public void animatText(CharSequence cs){
        //set text
        charSequence= cs;
        //clear index
        index=0;
        //clear text
        textView.setText("");
        //remove callback
        handler.removeCallbacks(runnable);
        //run handler
        handler.postDelayed(runnable,delay);
    }
}