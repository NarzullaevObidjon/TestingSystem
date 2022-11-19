package com.company;


import com.company.entity.User;

import com.company.service.TestService;
import com.company.service.UserService;
import com.company.ui.AuthUI;

public class App {
    public static User currUser;
    public static void main( String[] args ) {
        if(TestService.uploadHistories().isResult()
        && TestService.uploadQuestions().isResult()
        && TestService.uploadSubjects().isResult()
        && UserService.uploadUsers().isResult()){
            AuthUI.run();
        }else {
            System.out.println("Something went wrong.");
        }
    }
}
