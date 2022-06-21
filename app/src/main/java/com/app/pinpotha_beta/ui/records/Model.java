package com.app.pinpotha_beta.ui.records;

public class Model {

    String id,title,subtitle,decription,date;

    public Model(){}

    public Model(String id, String title, String subtitle, String decription, String date) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.decription = decription;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
