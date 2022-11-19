package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Subject {
   private int id;
   private String name;
   private double price;  // per question
   static int count=0;
   {
      id=++count;
   }
}
