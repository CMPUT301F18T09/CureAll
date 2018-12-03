package com.example.cmput301f18t09.cureall.observer;

public class DeleteObserver extends Observer {

    public DeleteObserver(Subject subject) {
        this.subject = subject;
        this.subject.attachObserver((java.util.Observer) this);
    }
}


