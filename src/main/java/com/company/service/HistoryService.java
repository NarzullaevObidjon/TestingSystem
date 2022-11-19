package com.company.service;

import com.company.entity.History;

import java.util.List;

public interface HistoryService {
    void addHistory(History history);
    List<History> getHistories(int userId);
    boolean rewriteHistories();

}
