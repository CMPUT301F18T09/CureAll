package com.example.cmput301f18t09.cureall;
import java.io.Serializable;

public abstract class user implements Serializable{
    String Phone;
    String Email;
    String username;
    String password;

    public user(String username, String password, String Phone, String email){
        this.username = username;
        this.password = password;
        this.Phone = Phone;
        this.Email = email;
    }

    public String getPhone(){return this.Phone;}
    public String getEmail(){return this.Email;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}

    public void setPhone(String phone){this.Phone = phone;}
    public void setEmail(String email){this.Email = email;}
}
