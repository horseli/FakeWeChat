package com.example.hp.fakewechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditQian extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_qian);
        editText=(EditText)findViewById(R.id.edit) ;
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=editText.getText().toString();
                Intent intent=new Intent(EditQian.this,LingQian.class);
                intent.putExtra("data",input);
                //Log.d("actiiii",input);
                startActivity(intent);
            }
        });
    }
}
