package com.company.ui;

import com.company.App;
import com.company.db.Database;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.enums.UserType;
import com.company.service.UserService;
import com.company.service.UserServiceImpl;
import com.company.util.ScannerUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AuthUI {
    static UserService userService= new UserServiceImpl();

    public static void run() {
        UserService.uploadUsers();
        logIn();
    }
    public static void logIn(){

        if(App.currUser!=null){
            if(App.currUser.getType().equals(UserType.USER)){
                UserUI.userInterface();
            }else {
                UserUI.adminInterface();
            }
            App.currUser=null;
        }else while (true){
            System.out.println("       * * * * Log In * * * *\n");
            System.out.println("Do you haven't an account?  1-> Create account  || 0->Exit");

            System.out.print("Enter phone number -> +998");
            String phone = ScannerUtil.textIn.nextLine();

            if(phone.equals("0")){
                return;
            }


            if(phone.equals("1")){
                signUp();
                logIn();
                return;
            }
            else {
                String result =checkPhone(phone);
                if(!result.equals("ok")){
                    System.out.println(result);
                    continue;
                }

                System.out.print("Enter password -> ");
                String password = ScannerUtil.textIn.nextLine();
                User user = userService.checkAndGetUser("+998"+phone, password);
                if(user!=null){
                    App.currUser=user;
                    if(user.getType().equals(UserType.USER)){
                        UserUI.userInterface();
                    }else {
                        UserUI.adminInterface();
                    }
                    App.currUser=null;
                    return;
                }else{
                    System.out.println("Ushbu ma'lumotlar orqali foydalanuvchi topilmadi");
                }
            }

        }
    }
    public static void signUp(){
        while (true){
            System.out.println("       * * * * Sign Up * * * *\n");
            System.out.print("0-> Exit  || Enter phone number -> +998");
            String phone = ScannerUtil.textIn.nextLine();

            if(phone.equals("0")){
                return;
            }

            if(userService.doesHave("+998"+phone)){
                System.out.println("This number already exists. Is it you ? If so log in");
            }

            String result = checkPhone(phone);
            if(!result.equals("ok")){
                System.out.println(result);
                continue;
            }

            String password;
            while (true){
                System.out.print("0-> Exit  || Enter password : ");
                password = ScannerUtil.textIn.nextLine();

                if (password.equals("0")) return;

                if(password.isBlank()){
                    System.out.println("Password is required");
                    continue;
                }
                if (!password.matches("[a-zA-Z0-9]{8}+")){
                    System.out.println("In password, must be at least 8 alphanumeric or underscore character");
                    continue;
                }
                break;
            }

            System.out.print("0-> Exit  || Enter name : ");
            String name = ScannerUtil.textIn.nextLine();

            if (name.equals("0")) return;

            UserDTO userDTO = new UserDTO(name,"+998"+phone,password);
            User user = userService.addUser(userDTO);
            if(user==null){
                System.out.println("Something went wrong. Please try again");
                continue;
            }
            App.currUser=user;
            return;
        }
    }
    private  static String checkPhone(String phone){
        if( phone==null||phone.isBlank()){
            return "Phone number required";
        }

        if(!phone.matches("\\d{9}")){
            return "Wrong phone number format";
        }

        return "ok";
    }


}
