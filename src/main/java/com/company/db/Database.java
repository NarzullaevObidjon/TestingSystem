package com.company.db;

import com.company.entity.History;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.entity.User;
import com.company.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static String BASE_FOLDER="src/main/resources";
    public static List<User> users=new ArrayList<>();
    public static List<History> histories=new ArrayList<>();
    public static List<Question> questions = new ArrayList<>();
    public static List<Subject> subjects = new ArrayList<>();
}
