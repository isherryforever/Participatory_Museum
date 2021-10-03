package com.isherryforever.museum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Position extends AppCompatActivity {
    public Context myContext;
    TextView pos,name;
    String weizhi="XXXXX",xingming="XXXXX";
    private Button draw;
    private ImageView iv_canvas;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private Bitmap kids;
    boolean flag=false;

    float lastx[]=new float[2000];
    float lasty[]=new float[2000];
    int num=0;

//在此基础上，实现用小孩子取代画笔，输入一个坐标后，就把两者之间连接一条直线轨迹
//要想直线变得圆滑，增加数据发送频率，减小每次的移动距离
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        this.getWindow().setBackgroundDrawableResource(R.drawable.a);
        pos=findViewById(R.id.position);
        name=findViewById(R.id.name);
        // 初始化一个画笔，笔触宽度为5，颜色为红色
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);

        iv_canvas = (ImageView) findViewById(R.id.iv_canvas);
        draw=findViewById(R.id.btn_draw);
        initCanvas();
        kids = BitmapFactory.decodeResource(this.getResources(), R.drawable.kids);
        lastx[0]=0;
        lasty[0]=0;

        draw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                flag=true;
                name.setText("姓名："+xingming);
                pos.setText("位置："+weizhi);
            }
        });
        @SuppressLint("HandlerLeak")
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 0:

                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(flag)
                {
                    num=0;
                    Api.getRequest1(new myCallback() {
                        @Override
                        public void onSuccess(final String res) {
                            Log.e("GetSuccess", res);
                            Sensor data = new Gson().fromJson(res, Sensor.class);
                            List<Sensor.UserBean> record = data.getRecords();
                            xingming=data.getName();
                            weizhi=data.getText();
                            int n=data.getTotal();
                            for(int i=0;i<n;i++)
                            {
                                lastx[++num]=record.get(i).getLocx()*3+30;
                                lasty[num]=record.get(i).getLocy()*3+30;//num加一次就够了
                            }
                            name.setText("姓名："+xingming);
                            pos.setText("位置："+weizhi);
                            canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清空路径
                            canvas.drawARGB(255,237,230,230);
                            for(int i=1;i<=num;i++)
                                canvas.drawLine(lastx[i-1], lasty[i-1], lastx[i], lasty[i], paint);//重新绘制之前保存的轨迹
                            canvas.drawBitmap(kids, lastx[num]-10, lasty[num]-10, null);//绘制小孩头像
                            mHandler.sendEmptyMessage(0);
                        }
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(Position.this, "网络连接失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                System.out.println(flag);
            }
        };
        timer.schedule(timerTask,500,5000);//刷新频率
        myContext=this;

    }

    private void initCanvas()
    {
        baseBitmap = Bitmap.createBitmap(360,
                360, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(baseBitmap);
        canvas.drawARGB(255,237,230,230);
        //"#4DB3B3"
        iv_canvas.setImageBitmap(baseBitmap);
    }
}