package com.example.hp.fakewechat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SingleTalkActivity extends AppCompatActivity {

    private Button Add;
    private Button Preview;
    private Button Clean;
    TextView textView1;
    TextView textView2;
    ImageView imageView1,imageView2;
    private List<TalkPerson> PersonList=new ArrayList<TalkPerson>();
    private ListView msgview;
    private TalkPersonAdapter adapter;
    private final int ADD_MSG = 1;
    private final int SELECT_PERSON_1 = 2;
    private final int SELECT_PERSON_2 = 3;
    private final int PRE = 4;
    String imgPath1;
    String imgPath2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singletalk_set);
        imgPath1 = "";
        imgPath2 = "";
        Add=(Button) findViewById(R.id.button_add);
        Preview=(Button)findViewById(R.id.button_preview);
        Clean=(Button)findViewById(R.id.button_clean);
                msgview=(ListView)findViewById(R.id.msg_view);
        textView1 = (TextView)findViewById(R.id.textView5);
        textView2 = (TextView)findViewById(R.id.textView6);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SingleTalkActivity.this,AddActivity.class);
                intent.putExtra("person1",textView1.getText().toString());
                intent.putExtra("person2",textView2.getText().toString());
                startActivityForResult(intent,ADD_MSG);
            }
        });



        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preview.setEnabled(false);
                Intent intent=new Intent(SingleTalkActivity.this,PreviewActivity.class);
                //把PersonList的数据存入intent
                List<TalkPersonHelp> list = new ArrayList<>();
                for(int i = 0;i<PersonList.size();++i){
                    int position = PersonList.get(i).getPosition();
                    String msg = PersonList.get(i).getText();
                    list.add(new TalkPersonHelp(position,msg));
                }
                intent.putExtra("personList",(Serializable)list);
                intent.putExtra("name",textView2.getText().toString());
                intent.putExtra("imgPath1",imgPath1);
                intent.putExtra("imgPath2",imgPath2);
                startActivity(intent);
                Preview.setEnabled(true);
            }
        });

        Clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonList=new ArrayList<>();
                adapter=new TalkPersonAdapter(SingleTalkActivity.this,R.layout.talkperson_item,PersonList);
                msgview.setAdapter(adapter);
            }
        });

        imageView1 = (ImageView) findViewById(R.id.imageView2);
        imageView2 = (ImageView) findViewById(R.id.imageView6);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SingleTalkActivity.this,PeopleActivity.class);
                startActivityForResult(intent,SELECT_PERSON_1);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SingleTalkActivity.this,PeopleActivity.class);
                startActivityForResult(intent,SELECT_PERSON_2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case ADD_MSG:
                if(resultCode==RESULT_OK){
                    String returnedText=data.getStringExtra("text_return");
                    int position = data.getIntExtra("position",0);
                    Bitmap bitmap;
                    if(position==0){
                        bitmap = ((BitmapDrawable) ((ImageView) imageView1).getDrawable()).getBitmap();
                    }else{
                        bitmap = ((BitmapDrawable) ((ImageView) imageView2).getDrawable()).getBitmap();
                    }
                    TalkPerson T= new TalkPerson(returnedText,bitmap,position);
                    PersonList.add(T);
                    adapter=new TalkPersonAdapter(SingleTalkActivity.this,R.layout.talkperson_item,PersonList);
                    msgview.setAdapter(adapter);
                }
                break;
            case SELECT_PERSON_1:
                if(resultCode==RESULT_OK){
                    String name = data.getStringExtra("name");
                    String img_path = data.getStringExtra("img_path");
                    textView1.setText(name);
                    imageView1.setImageBitmap(BitmapFactory.decodeFile(img_path));
                    imgPath1 = img_path;
                }
                break;
            case SELECT_PERSON_2:
                if(resultCode==RESULT_OK){
                    String name = data.getStringExtra("name");
                    String img_path = data.getStringExtra("img_path");
                    textView2.setText(name);
                    imageView2.setImageBitmap(BitmapFactory.decodeFile(img_path));
                    imgPath2 = img_path;
                }
                break;
        }
    }
}
