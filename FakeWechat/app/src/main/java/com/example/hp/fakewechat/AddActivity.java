package com.example.hp.fakewechat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private EditText Talk;
    private ImageView header;
    private TextView Name;
    private Spinner spinner;
    private ArrayList<String> name_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_talk);
        Intent intent = getIntent();
        String person1 = intent.getStringExtra("person1");
        String person2 = intent.getStringExtra("person2");
        spinner = (Spinner) findViewById(R.id.spinner);
        name_list = new ArrayList<String>();
        name_list.add(person1);
        name_list.add(person2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_list);
        spinner.setAdapter(arrayAdapter);
        //初始化
        Button DataReturn=(Button)findViewById(R.id.button_return);
        Talk=(EditText) findViewById(R.id.editText);

        //final String imagepath=img.getpath();
        //图片的真实路径,假设数据库里有个方法

        DataReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                String inputText=Talk.getText().toString();
                int position = spinner.getSelectedItemPosition();
                intent.putExtra("text_return",inputText);
                //intent.putExtra("header_return",imagepath);
                intent.putExtra("name_return",name_list.get(position));
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
