package com.isherryforever.museum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NFC extends AppCompatActivity {
    private static final String TAG = "This Tag";
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private TextView mReadText,mTagIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        //this.getWindow().setBackgroundDrawableResource(R.drawable.c);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mReadText = findViewById(R.id.readBtnView);
        mTagIdText=findViewById(R.id.id_tv);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "该设备不支持nfc", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "请打开nfc开关", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(intent);

        }

        //创建PendingIntent对象，当检查到一个tag标签就会执行此Intent
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //取出标签
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String techList[] = tag.getTechList();
        byte[] bytesId = tag.getId();
        String tagId =bytesToHexString(bytesId);
//        Log.d(TAG, "这里是tagId:"+tagId);
        mTagIdText.setText(tagId);
        for (String tech : techList) {
            System.out.print(tech);
            Log.d(TAG, tech);
        }
        readNdeftag(tag);
//        readMifareUltralight(tag);

        String action=intent.getAction();


    }


    public String readMifareUltralight(Tag tag){
        MifareUltralight mifare=MifareUltralight.get(tag);
        try {
            mifare.connect();
            byte[] payload=mifare.readPages(4);
            String data= new String(payload, Charset.forName("GB2312"));
            Log.d(TAG, data);

        } catch (IOException e) {
            Log.e(TAG, "IOException while reading MifareUltralight message...", e);
        } finally {
            if (mifare!=null){
                try {
                    mifare.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing tag...", e);
                }
            }
        }
        return null;
    }

    private String readNdeftag(Tag tag) {
        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            //获取tag的type是第几
//        String tagType=ndef.getType();
            try {
                ndef.connect();
                NdefMessage ndefMessage = ndef.getNdefMessage();
                if (ndefMessage != null) {
                    mReadText.setText(parseTextRecord(ndefMessage.getRecords()[0]));
                    String s=parseTextRecord(ndefMessage.getRecords()[0]);
                    String[] ss = new String[20];
                    String name=null,passwd=null;
                    ss = s.split(";");
                    //Toast.makeText(NFC.this, ss[0], Toast.LENGTH_LONG).show();
                    name=ss[0];
                    passwd=ss[1];
                    String finalName = name;
                    AVUser.logIn(name, passwd).subscribe(new Observer<AVUser>() {
                        public void onSubscribe(Disposable disposable) {
                        }
                        public void onNext(AVUser user) {
                            Toast.makeText(NFC.this, finalName+"的家长,欢迎登录！", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(NFC.this, MainActivity.class);
                            startActivity(intent);
                        }

                        public void onError(Throwable throwable) {
                            Toast.makeText(NFC.this, "登录失败", Toast.LENGTH_LONG).show();
                            // 登录失败（可能是密码错误）
                        }
                        public void onComplete() {
                        }
                    });
                    //parseTextRecord(ndefMessage.getRecords()[0])  为读取的数据，字符串方式存储，对其进行分割判断登录即可
                    //Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                } else {
                    mReadText.setText("该标签为空标签");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            } finally {
                try {
                    ndef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            readMifareUltralight(tag);
        }
        return  null;
    }
    public static String parseTextRecord(NdefRecord ndefRecord) {
        //判断TNF
        if (ndefRecord.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
            return null;
        }
        //判断长度和类型
        if (!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
            return null;
        }
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 0X80) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0X3f;
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            String textRecord = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
            return textRecord;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }

    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit(src[i] >>> 4 & 0X0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0X0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}
