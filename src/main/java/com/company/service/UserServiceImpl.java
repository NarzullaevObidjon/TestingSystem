package com.company.service;

import com.company.db.Database;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.util.Result;
import com.google.gson.Gson;

import java.io.*;

public class UserServiceImpl implements UserService {
    @Override
    public User checkAndGetUser(String phone, String password) {
        for (User user : Database.users) {
            if(user.getPhone().equals(phone)&& user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean doesHave(String phone) {
        for (User user : Database.users) {
            if(user.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }

    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User(Database.users.size()+1,userDTO.getName(), userDTO.getPhone(), userDTO.getPassword());
        Database.users.add(user);
        writeUsers();
        return user;
    }

    private void writeUsers() {
        File file = new File(Database.BASE_FOLDER, "users.json");
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new Gson();
            out.write(gson.toJson(Database.users));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
