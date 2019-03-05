package com.example.hp.fakewechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//    getWindow().setStatusBarColor(this.getResources().getColor(R.color.lightblue));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));
//        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
//                R.mipmap.qun_liao);
//        TextView tv = (TextView)findViewById(R.id.text1);
//        TextPaint tp = tv.getPaint();
//        tp.setFakeBoldText(true);
        Button lqian=(Button)findViewById(R.id.button5);
        Button singletalk=(Button)findViewById(R.id.button1);
        Button zhuangzhang=(Button)findViewById(R.id.button3);
        lqian.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,EditQian.class);
                startActivity(intent);
            }
        });
        singletalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SingleTalkActivity.class);
                startActivity(intent);
            }
        });
        zhuangzhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Edit_ZZ.class);
                startActivity(intent);
            }
        });

    }
}
