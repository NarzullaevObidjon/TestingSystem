package com.company.service;

import com.company.db.Database;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestServiceImpl implements TestService{
    @Override
    public boolean addSubject(String name, double price) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setPrice(price);
        Database.subjects.add(subject);
        return rewriteSubjects();
    }

    @Override
    public List<Subject> getSubjects() {
        return  Database.subjects;
    }

    @Override
    public Subject getSub(int id) {
        for (Subject subject : Database.subjects) {
            if (subject.getId()==id){
                return subject;
            }
        }
        return null;
    }

    @Override
    public boolean doesHaveSub(String s) {
        if(Database.subjects==null || Database.subjects.isEmpty()){
            return false;
        }
        for (Subject subject : Database.subjects) {
            if (subject.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addQuestion(Question question) {
        Database.questions.add(question);
        rewriteQuestions();
    }

    @Override
    public Question checkAndGetQuestion(int id) {
        if(Database.questions==null || Database.questions.isEmpty()){
            return null;
        }
        for (Question question : Database.questions) {
            if (question.getId()==id) {
                return question;
            }
        }
        return null;
    }

    @Override
    public List<Question> getQuestions() {
        return  Database.questions;
    }


    @Override
    public void deleteQuestion(int id) {
        for (Iterator<Question> iterator = Database.questions.iterator(); iterator.hasNext();) {
            Question question = iterator.next();
            if(question.getId()==id) {
                iterator.remove();
            }
        }
        rewriteQuestions();
    }


    @Override
    public void deleteSubject(int id) {

        for (Iterator<Subject> iterator = Database.subjects.iterator(); iterator.hasNext();) {
            Subject subject = iterator.next();
            if(subject.getId()==id) {
                iterator.remove();
            }
        }
        for (Iterator<Question> iterator = Database.questions.iterator(); iterator.hasNext();) {
            Question question = iterator.next();
            if(question.getSubjectId()==id) {
                iterator.remove();
            }
        }
        rewriteSubjects();
        rewriteQuestions();
    }

    public boolean rewriteQuestions(){
        Gson gson = new Gson();
        File file = new File(Database.BASE_FOLDER,"questions.json");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(gson.toJson(Database.questions));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean rewriteSubjects() {
        Gson gson = new Gson();
        File file = new File(Database.BASE_FOLDER,"subjects.json");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(gson.toJson(Database.subjects));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Question> getQuestionsBySubject(int subId) {
        List<Question> questions = new ArrayList<>();
        if(Database.questions==null || Database.questions.isEmpty()){
            return null;
        }
        for (Question question : Database.questions) {
            if (question.getSubjectId()==subId){
                questions.add(question);
            }
        }
        return questions;
    }
}
