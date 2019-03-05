package com.example.hp.fakewechat;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.Serializable;

public class Person implements Serializable {
    private int id;

    private String name;

    private String img;

    public Person(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

}
