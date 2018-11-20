package com.example.cmput301f18t09.cureall;

import org.junit.Test;
import com.example.cmput301f18t09.cureall.user;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {
    @Test
    public void UserInfoTest(){
        String username = "u9";
        String password = "1";
        String phoneNum = "1";
        String email = "1";

        user user = new user (username,password,phoneNum,email);

        assertEquals( user.getUsername(), username );
        assertEquals( user.getPassword(), password );
        assertEquals( user.getPhone(), phoneNum );
        assertEquals( user.getEmail(), email );

    }
}
