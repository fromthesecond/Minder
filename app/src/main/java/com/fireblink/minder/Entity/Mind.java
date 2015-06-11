package com.fireblink.minder.Entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "Minds")
public class Mind extends Model {
    @Column(name = "name")
    public String name;
    @Column(name = "body")
    public String body;
    @Column(name = "date")
    public Date date;

    public Mind () {
        super();
    }
    public Mind (String title, String body, Date date) {
        super();
        this.name = title;
        this.body = body;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
