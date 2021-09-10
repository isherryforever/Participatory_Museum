package com.isherryforever.museum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quit = findViewById(R.id.btn8);
        quit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //MediaPlayer.create(MyApplication.getAppContext(),R.raw.water).start();
                SharedPreferences shared;
                shared = MyApplication.getAppContext().getSharedPreferences("spfRecord",0);
                int version = shared.getInt("version", 0);
                SharedPreferences.Editor edit = shared.edit();
                edit.putString("account",null);
                edit.putString("password",null);
                edit.putBoolean("isRemember",true);
                edit.putBoolean("isLogin",false);
                edit.apply();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }
}