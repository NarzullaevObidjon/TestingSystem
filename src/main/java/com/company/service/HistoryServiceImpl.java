package com.company.service;

import com.company.db.Database;
import com.company.entity.History;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    @Override
    public void addHistory(History history) {
        Database.histories.add(history);
        rewriteHistories();
    }

    @Override
    public List<History> getHistories(int userId) {
        if (Database.histories==null || Database.histories.isEmpty()) {
            return null;
        }
        List<History> histories1 = new ArrayList<>();
        for (History history : Database.histories) {
            if (history.getUserId()==userId){
                histories1.add(history);
            }
        }
        return histories1;
    }
    @Override
    public boolean rewriteHistories(){
        Gson gson = new Gson();
        File file = new File(Database.BASE_FOLDER,"histories.json");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(gson.toJson(Database.histories));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }
}
