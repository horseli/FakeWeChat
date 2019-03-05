package com.example.hp.fakewechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends ArrayAdapter<TalkPerson> {
    private Bitmap bmp;
    private String name;
    private int resourceId;
    public MsgAdapter(Context context, int textviewResourceId, List<TalkPerson> objects) {
        super(context,textviewResourceId,objects);
        resourceId=textviewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TalkPerson talkPerson=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView header1=(ImageView)view.findViewById(R.id.item_wechat_msg_iv_sender_profile);
        ImageView header2=(ImageView)view.findViewById(R.id.item_wechat_msg_iv_receiver_profile);
        LinearLayout linearLayout = view.findViewById(R.id.left_layout);
        LinearLayout relativeLayout = view.findViewById(R.id.right_layout);
        TextView msg1=view.findViewById(R.id.right_msg);
        TextView msg2=view.findViewById(R.id.left_msg);
        header1.setImageBitmap(talkPerson.getImage());
        header2.setImageBitmap(talkPerson.getImage());
        msg1.setText(talkPerson.getText());
        msg2.setText(talkPerson.getText());
        if(talkPerson.getPosition()==0){
            linearLayout.setVisibility(view.GONE);
        }else{
            relativeLayout.setVisibility(view.GONE);
        }
        return view;
    }
}
