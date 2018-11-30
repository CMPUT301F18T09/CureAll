package com.example.cmput301f18t09.cureall;

import android.support.annotation.NonNull;

public class Encryption {

    public static String encrypt(String input){
        byte[] letters = input.getBytes();
        byte[] encrypted = new byte[letters.length];

        for (int i = 0; i < letters.length; i++){
            encrypted[i] = (byte)(letters[i] - 3);
        }
        return new String(encrypted);
    }

    public static String decrypt(String input){
        byte[] letters = input.getBytes();
        byte[] decrypted = new byte[letters.length];

        for (int i = 0; i < letters.length; i++){
            decrypted[i] = (byte)(letters[i] + 3);
        }
        return new String(decrypted);
    }
}
