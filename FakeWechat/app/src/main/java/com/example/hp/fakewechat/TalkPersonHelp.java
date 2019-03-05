package com.example.hp.fakewechat;


import java.io.Serializable;

public class TalkPersonHelp implements Serializable {
    private int position;
    private String msg;

    TalkPersonHelp(int position,String msg){
        this.position=position;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getPosition() {
        return position;
    }
}