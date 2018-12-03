package com.example.cmput301f18t09.cureall.observer;

public class EditObserver extends Observer {

    public EditObserver(Subject subject) {
        this.subject = subject;
        this.subject.attachObserver((java.util.Observer) this);
    }
}