package com.company;


import com.company.entity.User;

import com.company.ui.AuthUI;

public class App {
    public static User currUser;
    public static void main( String[] args ) {

        AuthUI.run();
    }
}
