package com.company.service;

import com.company.db.Database;
import com.company.entity.History;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.util.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public interface TestService {

    boolean addSubject(String name, double price);

    void deleteQuestion(int id);

    void deleteSubject(int id);

    boolean getSubject();

    List<Subject> getSubjects();

    Subject getSub(int id);

    boolean doesHaveSub(String s);

    void addQuestion(Question question);

    Question checkAndGetQuestion(int id);

    List<Question> getQuestions();

    boolean rewriteQuestions();

    boolean rewriteSubjects();

    List<Question> getQuestionsBySubject(int subId);


    static Result uploadQuestions() {
        File file = new File(Database.BASE_FOLDER, "questions.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Question>>() {
            }.getType();
            Database.questions = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok", true);
    }

    static Result uploadSubjects() {
        File file = new File(Database.BASE_FOLDER, "subjects.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Subject>>() {
            }.getType();
            Database.subjects = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok", true);
    }

    static Result uploadHistories() {
        File file = new File(Database.BASE_FOLDER, "histories.json");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<History>>() {
            }.getType();
            Database.histories = gson.fromJson(in, type);
        } catch (IOException e) {
            return new Result(e.getMessage());
        }
        return new Result("ok", true);
    }

   boolean toExcel();
}
