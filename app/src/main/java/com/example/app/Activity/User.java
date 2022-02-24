package com.example.app.Activity;

public class User {
    private String fullName,phone,email,password;

    public User() {
    }

    public User (String fullName, String phone, String email)
    {
        this.fullName = fullName;
        this.phone=phone;
        this.email=email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return fullName;
    }

    public void setUsername(String username) {
        this.fullName = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
