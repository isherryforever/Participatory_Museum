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
//TextView nfc;
//有两个需要跳转的界面，界面一显示当前孩子的位置，界面二显示挡墙客流量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.getWindow().setBackgroundDrawableResource(R.drawable.a);
        quit = findViewById(R.id.btn_quit);
        //nfc = findViewById(R.id.btn_nfc);
        /*nfc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NFC.class);
                startActivity(intent);
            }
        });*/
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