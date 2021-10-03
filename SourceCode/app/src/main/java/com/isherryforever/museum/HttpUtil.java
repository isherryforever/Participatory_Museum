package com.isherryforever.museum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    private final static int SUCCESS = 1;
    private final static int FAIL = 0;
    private static Context AppContext;

    public static void GetRequest(Context context, String url){
        AppContext = context;
        Thread thread = new Thread(){
            @Override
            public void run() {
                getResponseBody(url);
                super.run();
            }
        };

        thread.start();
    }


    public static void getResponseBody(String url){

        final Message msg = handler.obtainMessage();

        OkHttpClient okHttpClient = new OkHttpClient();
        Bundle bundle = new Bundle();

        Request okRequest = new Request.Builder()
                .url(url)
                .build();

        okHttpClient
                .newCall(okRequest)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        msg.what = FAIL;
                        String error = e.getMessage();
                        bundle.putString("error",error);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        msg.what = SUCCESS;
                        String data = response.body().string();
                        bundle.putString("data",data);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                });
    }


    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    String response = msg.getData().getString("data");
                    List<com.isherryforever.museum.Sensor> sensorList = JSON.parseArray(response, com.isherryforever.museum.Sensor.class);
                    for (com.isherryforever.museum.Sensor sensor:sensorList) {
                        //Toast.makeText(AppContext, "温度:"+ sensor.getTemperature() +"  湿度="+sensor.getHumidity(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case FAIL:
                    Toast.makeText(AppContext, msg.getData().getString("error"),Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
}
