package com.example.user.mobcontacts.models;


public class Contact {


    private String name;
    private String phone;
    private int gender;
    private int age;
    private int id;


    public Contact(String name, String phone, int age, int gender) {

        this.name = name;
        this.phone = phone;
        this.age = age;
        this.gender = gender;

    }

    public Contact(int id, String name, String phone, int age, int gender) {

        this.name = name;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.id = id;

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
