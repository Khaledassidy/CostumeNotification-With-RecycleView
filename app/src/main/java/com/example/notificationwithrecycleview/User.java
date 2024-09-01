package com.example.notificationwithrecycleview;

public class User {

    private int image;
    private String name;
    private String email;
    private double salary;

    public User(int image, String name, String email, double salary) {
        this.image = image;
        this.name = name;
        this.email = email;
        this.salary = salary;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
