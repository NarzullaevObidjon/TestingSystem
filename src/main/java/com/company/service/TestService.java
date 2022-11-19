package com.company.service;

import com.company.db.Database;
import com.company.entity.History;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.util.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public interface TestService {

    boolean addSubject(Subject subject);
    Result editQuestion(Question newQuestion);
    Result deleteQuestion(Question question,int subjectId);
    Question getQuestion(int id);
    Result deleteSubject(int id);

    static Result uploadQuestions(){
        File file = new File(Database.BASE_FOLDER,"questions.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file)) ){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Question>>() {
            }.getType();
            Database.questions = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok",true);
    }

    static Result uploadSubjects(){
        File file = new File(Database.BASE_FOLDER,"subjects.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file)) ){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Question>>() {
            }.getType();
            Database.subjects = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok",true);
    }

    static Result uploadHistories(){
        File file = new File(Database.BASE_FOLDER,"histories.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file)) ){
            Gson gson = new Gson();
            Type type = new TypeToken<List<History>>() {
            }.getType();
            Database.histories = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok",true);
    }
}
