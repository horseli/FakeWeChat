package com.example.hp.fakewechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ZZ extends AppCompatActivity {
    private TextView Money;
    private TextView T1;
    private TextView T2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuang_zhang);
        Money=findViewById(R.id.textView7);
        T1=findViewById(R.id.textView14);
        T2=findViewById(R.id.textView11);
        Intent intent=getIntent();
        Money.setText("¥"+intent.getStringExtra("M"));
        T1.setText("转账时间："+intent.getStringExtra("st"));
        T2.setText("收钱时间："+intent.getStringExtra("et"));
    }
}
