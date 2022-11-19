package com.company.ui;

import com.company.App;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.enums.UserType;
import com.company.service.UserService;
import com.company.service.UserServiceImpl;
import com.company.util.ScannerUtil;


public class AuthUI {
    static UserService userService= new UserServiceImpl();

    public static void run() {
        UserService.uploadUsers();
        while (true){
            logIn();
        }
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
            System.out.println("\033[1;34m"+"       * * * * Log In * * * *\n");
            System.out.println("\033[0m"+"Do you haven't an account?  \n1-> Create account  || 0->Exit");

            System.out.print("\nEnter phone number -> +998");
            String phone = ScannerUtil.textIn.nextLine();

            if(phone.equals("0")){
                System.exit(0);
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

                System.out.print("\033[0m"+"Enter password -> ");
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
                    System.out.println("\033[1;31m"+"\nUshbu ma'lumotlar orqali foydalanuvchi topilmadi\n");
                }
            }

        }
    }
    public static void signUp(){
        while (true){
            System.out.println("\033[1;34m"+"       * * * * Sign Up * * * *\n");
            System.out.print("\033[0m"+"0-> Exit  || Enter phone number -> +998");
            String phone = ScannerUtil.textIn.nextLine();

            if(phone.equals("0")){
                return;
            }

            if(userService.doesHave("+998"+phone)){
                System.out.println("\033[1;31m"+"\nThis number already exists. Is it you ? If so log in\n");
                return;
            }

            String result = checkPhone(phone);
            if(!result.equals("ok")){
                System.out.println("\033[1;31m"+result);
                continue;
            }

            String password;
            while (true){
                System.out.print("\033[0m"+"0-> Exit  || Enter password : ");
                password = ScannerUtil.textIn.nextLine();

                if (password.equals("0")) return;

                if(password.isBlank()){
                    System.out.println("\033[1;31m"+"\nPassword is required\n");
                    continue;
                }
                if (!password.matches("[a-zA-Z0-9]{8}+")){
                    System.out.println("\033[1;31m"+"\nIn password, must be at least 8 alphanumeric or underscore character\n");
                    continue;
                }
                break;
            }

            System.out.print("\033[0m"+"0-> Exit  || Enter name : ");
            String name = ScannerUtil.textIn.nextLine();

            if (name.equals("0")) return;

            UserDTO userDTO = new UserDTO(name,"+998"+phone,password);
            User user = userService.addUser(userDTO);
            if(user==null){
                System.out.println("\033[1;31m"+"\nSomething went wrong. Please try again\n");
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
