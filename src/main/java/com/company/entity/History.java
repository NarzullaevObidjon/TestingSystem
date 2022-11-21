package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class History {
    private int id;
    private int userId;
    private String subject;
    private int quantity;
    private int corrects;
    private String startedAt;
    private String finishedAt;
    static int count = 0;

    {
        id = ++count;
    }

    public History(int userId, String subject, int quantity, int corrects, String startedAt, String finishedAt) {
        this.userId = userId;
        this.subject = subject;
        this.quantity = quantity;
        this.corrects = corrects;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }
}
