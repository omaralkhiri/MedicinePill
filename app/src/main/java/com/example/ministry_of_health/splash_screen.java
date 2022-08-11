package com.example.ministry_of_health;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       // ImageView imgHome=findViewById(R.id.imghome);
        TextView text1=findViewById(R.id.textView22);
        TextView text2=findViewById(R.id.textView35);

        //PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("rotation", 360f);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationY", -800f,0f);
       // PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("alpha", 1f,0f,1f);

        ObjectAnimator animation0 = ObjectAnimator.ofPropertyValuesHolder(text1, pvhX);
        animation0.setDuration(2000);
        animation0.start();
        ObjectAnimator animation1 = ObjectAnimator.ofPropertyValuesHolder(text2, pvhX);
        animation1.setDuration(2000);
        animation1.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
            }
        },3000);
    }
}