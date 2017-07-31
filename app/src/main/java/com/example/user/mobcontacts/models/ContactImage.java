package com.example.user.mobcontacts.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/28/2017.
 */

public class ContactImage {


    private String path;
    private String discription;
    private int contact_id;
    private int id;

    public ContactImage( String path) {
        this.path = path;
    }

    public ContactImage( String path, String discription) {
        this.path = path;
        this.discription = discription;

    }

    public ContactImage(int contact_id, String path, String discription) {
        this.path = path;
        this.discription = discription;
        this.contact_id = contact_id;
    }

    public ContactImage(int contact_id, int id, String path, String discription) {
        this.path = path;
        this.discription = discription;
        this.contact_id = contact_id;
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
