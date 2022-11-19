package com.company.service;

import com.company.db.Database;
import com.company.dto.UserDTO;
import com.company.entity.History;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.entity.User;
import com.company.util.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.List;


public interface UserService {

    static Result uploadUsers(){
        File file = new File(Database.BASE_FOLDER,"users.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file)) ){
            Gson gson = new Gson();
            Type type = new TypeToken<List<User>>() {
            }.getType();
            Database.users = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok",true);
    }

    User checkAndGetUser(String phone, String password);

    User addUser(UserDTO userDTO);

    boolean doesHave(String phone);

    boolean fillBalance(double money, int id);
}
