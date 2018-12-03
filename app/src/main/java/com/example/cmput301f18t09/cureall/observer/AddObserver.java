package com.example.cmput301f18t09.cureall.observer;

public class AddObserver extends Observer {

    public AddObserver(Subject subject){
        this.subject = subject;
        this.subject.attachObserver((java.util.Observer) this);
    }

}
