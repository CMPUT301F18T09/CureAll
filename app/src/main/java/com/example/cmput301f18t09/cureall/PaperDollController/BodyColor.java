package com.example.cmput301f18t09.cureall.PaperDollController;

import java.util.ArrayList;
import java.util.Hashtable;

public class BodyColor {
    private Hashtable<ArrayList<Integer>,BodyPart> colorTable = new Hashtable<>();

    public BodyColor() {
        // init list of integer values
        ArrayList<Integer> head = new ArrayList<>();
        ArrayList<Integer> shoulder = new ArrayList<>();
        ArrayList<Integer> forearm = new ArrayList<>();
        ArrayList<Integer> hand = new ArrayList<>();
        ArrayList<Integer> chest = new ArrayList<>();
        ArrayList<Integer> upper_back = new ArrayList<>();
        ArrayList<Integer> lower_back = new ArrayList<>();
        ArrayList<Integer> hip = new ArrayList<>();
        ArrayList<Integer> knee = new ArrayList<>();
        ArrayList<Integer> upper_leg = new ArrayList<>();
        ArrayList<Integer> lower_leg = new ArrayList<>();
        ArrayList<Integer> foot = new ArrayList<>();

        // define color values
        head.add(-16777216);
        head.add(-16744963);

        shoulder.add(-16777116);
        shoulder.add(-16777166);
        shoulder.add(-13449216);
        shoulder.add(-13436416);

        forearm.add(-16777016);
        forearm.add(-16777066);
        forearm.add(-13500316);
        forearm.add(-13500266);
        forearm.add(-16777016);
        forearm.add(-13500216);
        forearm.add(-13500166);

        hand.add(-16776966);
        hand.add(-13487616);
        hand.add(-13474766);
        hand.add(-13461966);

        chest.add(-13500416);

        upper_back.add(-13474816);
        upper_back.add(-13462016);

        lower_back.add(-13500366);

        hip.add(-10223616);
        hip.add(-13487566);

        knee.add(-393216);
        knee.add(-16764416);

        upper_leg.add(-6946816);
        upper_leg.add(-3670016);
        upper_leg.add(-13449166);
        upper_leg.add(-13436366);

        lower_leg.add(-13487516);
        lower_leg.add(-13487466);
        lower_leg.add(-13487416);
        lower_leg.add(-13487366);
        lower_leg.add(-16738816);
        lower_leg.add(-16751616);

        foot.add(-16713216);
        foot.add(-16726016);
        foot.add(-10210766);
        foot.add(-6933966);

        // put value pairs in the color table
        colorTable.put(head, BodyPart.HEAD);
        colorTable.put(shoulder, BodyPart.SHOULDER);
        colorTable.put(forearm, BodyPart.FOREARM);
        colorTable.put(hand, BodyPart.HAND);
        colorTable.put(chest, BodyPart.CHEST);
        colorTable.put(upper_back, BodyPart.UPPER_BACK);
        colorTable.put(lower_back, BodyPart.LOWER_BACK);
        colorTable.put(knee, BodyPart.KNEE);
        colorTable.put(hip, BodyPart.HIP);
        colorTable.put(upper_leg, BodyPart.UPPER_LEG);
        colorTable.put(lower_leg, BodyPart.LOWER_LEG);
        colorTable.put(foot, BodyPart.FOOT);
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
