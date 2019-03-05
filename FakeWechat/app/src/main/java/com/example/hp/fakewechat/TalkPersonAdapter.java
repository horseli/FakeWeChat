package com.example.hp.fakewechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TalkPersonAdapter extends ArrayAdapter<TalkPerson> {
    private final int resourceId;

    public TalkPersonAdapter(Context context, int textViewResourceId, List<TalkPerson> people){
        super(context,textViewResourceId,people);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TalkPerson talkPerson=(TalkPerson) getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView talkpersonImage=(ImageView) view.findViewById(R.id.talkperson_image);
        TextView talkpersonText=(TextView) view.findViewById(R.id.talkperson_text);
        //图片加载
        Bitmap bmp=talkPerson.getImage();
        talkpersonImage.setImageBitmap(bmp);
        //文本加载
        talkpersonText.setText(talkPerson.getText());
        return view;
    }
}
