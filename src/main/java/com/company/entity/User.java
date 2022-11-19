package com.company.entity;

import com.company.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
   private int id;
   private String name;
   private String phone;
   private String password;
   private double balance=0.0;
   private UserType type=UserType.USER;
   private boolean notActive =true;

   public User(int id, String name, String phone, String password) {
      this.id=id;
      this.name = name;
      this.phone = phone;
      this.password = password;
   }
}
