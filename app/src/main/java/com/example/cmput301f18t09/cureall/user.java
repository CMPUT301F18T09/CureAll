/**
 * Class name: user
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall;
import java.io.Serializable;
/**
 * Model class for recording CareProvider
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
public abstract class user implements Serializable{
    String Phone;
    String Email;
    String username;
    String password;

    /**
     * Init for user
     * @param username      username
     * @param Phone         corresponding Phone number
     * @param email         corresponding email address
     */
    public user(String username, String Phone, String email){
        this.username = username;
        this.Phone = Phone;
        this.Email = email;
    }

    /**
     * getter for Phone
     * @return Phone
     */
    public String getPhone(){return this.Phone;}
    /**
     * getter for Email
     * @return Email
     */
    public String getEmail(){return this.Email;}
    /**
     * getter for username
     * @return username
     */
    public String getUsername(){return this.username;}
    /**
     * getter for password
     * @return password
     */
    public String getPassword(){return this.password;}

    /**
     * setter for phone
     */
    public void setPhone(String phone){this.Phone = phone;}
    /**
     * setter for email
     */
    public void setEmail(String email){this.Email = email;}
}
