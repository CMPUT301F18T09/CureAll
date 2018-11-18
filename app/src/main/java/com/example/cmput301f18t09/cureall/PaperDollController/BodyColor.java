package com.example.cmput301f18t09.cureall.PaperDollController;

import java.util.ArrayList;
import java.util.Hashtable;

public class BodyColor {
    private Hashtable<ArrayList<Integer>,BodyPart> colorTable = new Hashtable<>();

    public BodyColor() {
        // init list of integer values

        ArrayList<Integer> head = new ArrayList<>();
        ArrayList<Integer> neck = new ArrayList<>();
        ArrayList<Integer> shoulder = new ArrayList<>();
        ArrayList<Integer> chest = new ArrayList<>();
        ArrayList<Integer> upper_arm = new ArrayList<>();
        ArrayList<Integer> lower_arm = new ArrayList<>();
        ArrayList<Integer> hand = new ArrayList<>();
        ArrayList<Integer> belt = new ArrayList<>();
        ArrayList<Integer> back = new ArrayList<>();
        ArrayList<Integer> hip = new ArrayList<>();
        ArrayList<Integer> thigh = new ArrayList<>();
        ArrayList<Integer> knee = new ArrayList<>();
        ArrayList<Integer> shank = new ArrayList<>();
        ArrayList<Integer> foot = new ArrayList<>();



        // define color values
        //head:-260100
        // neck: -5634052
        // shoulder: -16316420
        // chest: -9500676
        // upper-arm: -16282372
        // lower-arm: -16253703
        // hand: -16253703
        // belt: -260143
        // back:-260224
        // hip: -16253893
        // thigh: -1508345
        // knee: -210169
        // shank: -230905
        // foot: -238329
        head.add(-260100);
        neck.add(-5634052);
        shoulder.add(-16316420);
        chest.add(-9500676);
        upper_arm.add(-16282372);
        lower_arm.add(-16253703);
        hand.add(-16253703);
        belt.add(-260143);
        back.add(-260224);
        hip.add(-16253893);
        thigh.add(-1508345);
        knee.add(-210169);
        shank.add(-230905);
        foot.add(-238329);




        // define color values
        //head:-260100
        // neck: -5634052
        // shoulder: -16316420
        // chest: -9500676
        // upper-arm: -16282372
        // lower-arm: -16253703
        // hand: -16253703
        // belt: -260143
        // back:-260224
        // hip: -16253893
        // thigh: -1508345
        // knee: -210169
        // shank: -230905
        // foot: -238329

        // put value pairs in the color table
        colorTable.put(head, BodyPart.head);
        colorTable.put(neck, BodyPart.neck);
        colorTable.put(shoulder, BodyPart.shoulder);
        colorTable.put(chest, BodyPart.chest);
        colorTable.put(upper_arm, BodyPart.upper_arm);
        colorTable.put(lower_arm, BodyPart.lower_arm);
        colorTable.put(hand, BodyPart.hand);
        colorTable.put(belt, BodyPart.belt);
        colorTable.put(back, BodyPart.back);
        colorTable.put(hip, BodyPart.hip);
        colorTable.put(thigh, BodyPart.thigh);
        colorTable.put(knee, BodyPart.knee);
        colorTable.put(shank, BodyPart.shank);
        colorTable.put(foot, BodyPart.foot);

    }

    // take in color and return the body part if it exists.
    public BodyPart getBodyPart(int color) {
        for (ArrayList<Integer> colors : colorTable.keySet()) {
            if (colors.contains(color)) {
                return colorTable.get(colors);
            }
        }
        return BodyPart.NULL;
    }

}
