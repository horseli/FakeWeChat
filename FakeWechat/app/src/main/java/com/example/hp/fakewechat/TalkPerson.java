package com.example.hp.fakewechat;

import android.graphics.Bitmap;

public class TalkPerson {
    private String text;
    private Bitmap image;
    private int position;

    public TalkPerson(String text, Bitmap bitmap, int position){
        this.text=text;
        this.image=bitmap;
        this.position=position;
    }

    public String getText(){
        return text;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getPosition() {
        return position;
    }
}
