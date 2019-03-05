package com.example.hp.fakewechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LingQian extends AppCompatActivity {

    private Button button;
//    Window window = this.getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(this.getResources().getColor(colorResId));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling_qian);
        Button qian = (Button) findViewById(R.id.lingqian);
        Intent intent=getIntent();
        qian.setText("¥"+intent.getStringExtra("data").toString());

    }
//    Button butn = (Button) findViewById(R.id.lingqian);

}
