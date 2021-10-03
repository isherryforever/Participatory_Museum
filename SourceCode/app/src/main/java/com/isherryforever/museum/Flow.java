package com.isherryforever.museum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Flow extends AppCompatActivity {
    TextView room1,room2,room3,room4;
    int cnt1=0,cnt2=0,cnt3=0,cnt4=0;
    public Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        room1=findViewById(R.id.cnt_room1);
        room2=findViewById(R.id.cnt_room2);
        room3=findViewById(R.id.cnt_room3);
        room4=findViewById(R.id.cnt_room4);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(true)
                {
                    Api.getRequest2(new myCallback() {
                        @Override
                        public void onSuccess(final String res) {
                            Log.e("GetSuccess", res);
                            Room data = new Gson().fromJson(res, Room.class);
                            cnt1=data.getRoom1();
                            cnt2=data.getRoom2();
                            cnt3=data.getRoom3();
                            cnt4=data.getRoom4();
                            room1.setText("展厅1："+String.valueOf(cnt1)+"人");
                            room2.setText("展厅2："+String.valueOf(cnt2)+"人");
                            room3.setText("展厅3："+String.valueOf(cnt3)+"人");
                            room4.setText("展厅4："+String.valueOf(cnt4)+"人");
                        }
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(Flow.this, "网络连接失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        };
        timer.schedule(timerTask,500,5000);
        myContext=this;



    }
}