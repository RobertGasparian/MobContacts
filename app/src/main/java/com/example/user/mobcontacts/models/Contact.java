package com.example.user.mobcontacts.models;



public class Contact {


    private String name;
    private String phone;
    private int image;
    private int age;


    public Contact(String name, String phone,int age, int image) {
        this.name = name;
        this.phone = phone;
        this.age=age;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
