package com.example.hp.fakewechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Edit_ZZ extends AppCompatActivity {
    private EditText Money;
    private EditText time1;
    private EditText time2;
    private Button yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_zhuangzhang);
        TextView T1=findViewById(R.id.textView8);
        TextView T2=findViewById(R.id.textView9);
        TextView T3=findViewById(R.id.textView10);
        TextView T4=findViewById(R.id.textView12);
        TextView T5=findViewById(R.id.textView13);

        Money=findViewById(R.id.editText_money);
        time1=findViewById(R.id.Time1);
        time2=findViewById(R.id.Time2);
        yes=(Button)findViewById(R.id.button_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Edit_ZZ.this,ZZ.class);
                intent.putExtra("M",Money.getText().toString());
                intent.putExtra("st",time1.getText().toString());
                intent.putExtra("et",time2.getText().toString());
                startActivity(intent);
            }
        });
    }

}
