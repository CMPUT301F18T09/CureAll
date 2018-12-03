package com.example.cmput301f18t09.cureall.observer;

public abstract class Observer {
    protected Subject subject;

   public static void update(String object) {
       if (object.equals("patient")){
           System.out.print("patient updated");
       }else if (object.equals("careProvider")){
           System.out.print("careProvider updated");
       }else if (object.equals("problem")){
           System.out.print("problem updated");
       }else if (object.equals("record")) {
           System.out.print("record updated");
       }
   }
}
