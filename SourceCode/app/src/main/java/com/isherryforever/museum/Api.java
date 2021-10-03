package com.isherryforever.museum;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
    static OkHttpClient client = new OkHttpClient();
    //将下列两个URL更换即可正常使用
    public static String Url1 = "https://isherryforever.github.io/api1";//轨迹绘制API
    public static String Url2 = "https://isherryforever.github.io/api2";//场馆人流量API
    private static String requestUrl;
    private static HashMap<String, Object> mParams;
    public static Context AppContext;
    public static Api api = new Api();
    public static String  cookie;


    public static void postRequest2(final myCallback callback) {
        Request request = new Request.Builder()
                .url(Url2)
                //.post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("Failure", e.getMessage());
                Log.e("Failure", "COOKIE调用失败");
                callback.onFailure(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                cookie = response.header("Set-Cookie");
                callback.onSuccess(cookie==null?"":cookie);
                Log.e("Failure", cookie==null?"COOKIE空的":cookie);
            }
        });
    }

    public static void getRequest2(final myCallback callback) {

        Request request = new Request.Builder()
//               .url(Url+"mychart")
                .url(Url2)
                //.addHeader("Cookie",cookie==null?"":cookie)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(result);
            }
        });
    }

    public static void getRequest1(final myCallback callback) {

        Request request = new Request.Builder()
//               .url(Url+"mychart")
                .url(Url1)
                //.addHeader("Cookie",cookie==null?"":cookie)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(result);
            }
        });
    }

}
