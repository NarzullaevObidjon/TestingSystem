package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Question {
   private int id;
   private int subjectId;
   private String text;
   private String ans;
   private List<String> wrongAns;
   static int count=0;
   {
      id=++count;
   }
}
