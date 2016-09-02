package com.example.administrator.android_qbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import org.xutils.x;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class Welcome extends AppCompatActivity{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    Handler hd = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        View view = LayoutInflater.from(this).inflate(R.layout.welcome,null);
        setContentView(view);
        setTitle("Android题库");
        pref = getSharedPreferences("data",MODE_PRIVATE);
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                init();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    private void init(){
        boolean f = pref.getBoolean("first",true);
        System.out.println(f+"============");
        if (f){
            editor = pref.edit();
            editor.putBoolean("first",false);
            editor.commit();
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Welcome.this,FragmentActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in,R.anim.out);
                    finish();
                }
            },1000);
        }else{
            boolean ff = pref.getBoolean("second",false);
            if (ff){
                Intent intent = new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }else{
                Intent intent = new Intent(Welcome.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }

        }
    }
}
