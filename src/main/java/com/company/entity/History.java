package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
  private int id;
  private int userId;
  private String subject;
  private int quantity;
  private int corrects;
  private String startedAt;
  private String finishedAt;
}
