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
   static int count=0;
   {
      id=++count;
   }

   public User( String name, String phone, String password) {
      this.name = name;
      this.phone = phone;
      this.password = password;
   }
   public User( String name, String phone, String password, UserType userType) {
      this.type=userType;
      this.name = name;
      this.phone = phone;
      this.password = password;
   }
}
