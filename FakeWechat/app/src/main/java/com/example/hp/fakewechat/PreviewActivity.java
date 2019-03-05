package com.example.hp.fakewechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;


public class PreviewActivity extends AppCompatActivity {
    private ImageView chat_back;
    private ImageView chat_profile;

    private ListView msglistview;

    private ImageView voice;
    private ImageView emoji;
    private Button send;
    private ImageView Add;
    private TextView chater_name;
    private TextView chat_text;

    private List<TalkPerson> personList;
    private MsgAdapter adapter;
    private String me;

    public static Bitmap getBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_chat);
        //初始化
        chat_back=findViewById(R.id.activity_wechat_chat_back);
        chat_profile=findViewById(R.id.activity_wechat_chat_profile);
        msglistview=findViewById(R.id.activity_wechat_chat_rv);
        voice=findViewById(R.id.activity_wechat_chat_iv_voice);
        emoji=findViewById(R.id.activity_wechat_chat_iv_emoji);
        send=findViewById(R.id.activity_wechat_chat_btn_send);
        Add=findViewById(R.id.activity_wechat_chat_iv_add);
        chater_name=findViewById(R.id.activity_wechat_chat_tv_name);
        chat_text=findViewById(R.id.activity_wechat_chat_et_msg);

        Intent intent=getIntent();
        //对intent中数据解包，从头开始遍历（for 1：PersonList.size（））
        List<TalkPersonHelp> talkPersonHelpList = (List<TalkPersonHelp>) intent.getSerializableExtra("personList");
        List<Bitmap> bitmapList = new ArrayList<>();
        String imgPath1 = intent.getStringExtra("imgPath1");
        String imgPath2 = intent.getStringExtra("imgPath2");
        if(imgPath1.equals("")){
            bitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.stranger));
        } else {
            bitmapList.add(BitmapFactory.decodeFile(imgPath1));
        }
        if(imgPath2.equals("")){
            bitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.stranger));
        } else {
            bitmapList.add(BitmapFactory.decodeFile(imgPath2));
        }
        bitmapList.add(BitmapFactory.decodeFile(intent.getStringExtra("imgPath2")));
        personList = new ArrayList<>();
        Log.e("IMGERROR",String.valueOf(talkPersonHelpList.size()));
        for(int i = 0;i<talkPersonHelpList.size();++i){
            TalkPersonHelp talkPersonHelp = talkPersonHelpList.get(i);
            personList.add(new TalkPerson(talkPersonHelp.getMsg(),bitmapList.get(talkPersonHelp.getPosition()),talkPersonHelp.getPosition()));
        }
        chater_name.setText(intent.getStringExtra("name"));
        initview();
    }
    public void initview(){
        adapter=new MsgAdapter(PreviewActivity.this,R.layout.msg_item,personList);
        msglistview.setAdapter(adapter);
        msglistview.setDivider(null);
    }

}
