package com.company.service;

import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.util.Result;

public class TestServiceImpl implements TestService{
    @Override
    public boolean addSubject(Subject subject) {
        return false;
    }

    @Override
    public Result editQuestion(Question newQuestion) {
        return null;
    }

    @Override
    public Result deleteQuestion(Question question, int subjectId) {
        return null;
    }

    @Override
    public Question getQuestion(int id) {
        return null;
    }

    @Override
    public Result deleteSubject(int id) {
        return null;
    }
}
