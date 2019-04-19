package com.hkucs.cookbook.activities.slideshow;

import java.io.Serializable;

public class Procedure implements Serializable {
    private int step;
    private int id;
    private String description;
    private String imgPath;

    public Procedure() {
    }

    public Procedure(int id, int step, String description, String imgPath) {
        this.step = step;
        this.id = id;
        this.description = description;
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public int getStep() {
        return step;
    }

    public String getDescription() {
        return description;
    }

    public String getImgPath() {
        return imgPath;
    }
}
