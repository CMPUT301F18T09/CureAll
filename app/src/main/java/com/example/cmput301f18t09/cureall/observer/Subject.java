package com.example.cmput301f18t09.cureall.observer;


import com.example.cmput301f18t09.cureall.model.CareProvider;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.model.Record;
import com.example.cmput301f18t09.cureall.observer.AddObserver;
import com.example.cmput301f18t09.cureall.observer.DeleteObserver;
import com.example.cmput301f18t09.cureall.observer.EditObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Subject {
    private List<Observer> observerList = new ArrayList<Observer>();
    private Problem problem;
    private Record record;
    private Patient patient;
    private CareProvider careProvider;
    private String action;
    private String object;

    public void attachObserver(Observer observer){
        this.observerList.add(observer);
    }

    public void removeObserver(Observer observer){
        this.observerList.remove(observer);
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
        notifyAllObservers();
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
        notifyAllObservers();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        notifyAllObservers();
    }

    public CareProvider getCareProvider() {
        return careProvider;
    }

    public void setCareProvider(CareProvider careProvider) {
        this.careProvider = careProvider;
        notifyAllObservers();
    }

    public void notifyAllObservers(){
//        for (Observer observer : observerList) {
//            Subject subject;
//            observer.update();
//        }

        if (action.equals("add")){
            AddObserver.update(object);
        }
        else if(action.equals("delete")){
            DeleteObserver.update(object);
        }
        else if(action.equals("edit")){
            EditObserver.update(object);
        }
    }
}
