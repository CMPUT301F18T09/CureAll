package com.example.cmput301f18t09.cureall.designPattern;

public class MessagePasser {
    private String message;
    public void StoreMessage(String yourMessage){
        this.message = yourMessage;
    }
    public String passMassage(){
        return this.message;
    }
}
