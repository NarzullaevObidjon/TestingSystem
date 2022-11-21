package com.company.service;

import com.company.db.Database;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.util.Result;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.awt.*;
import java.io.*;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public User checkAndGetUser(String phone, String password) {
        for (User user : Database.users) {
            if(user.getPhone().equals(phone)&& user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean doesHave(String phone) {
        for (User user : Database.users) {
            if(user.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean fillBalance(double money, int id) {
        for (User user : Database.users) {
            if (user.getId()==id) {
                user.setBalance(user.getBalance()+money);
                writeUsers();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean userPdf() {
        Gson gson=new Gson();
        File file=new File(Database.BASE_FOLDER,"users.pdf");

        try (PdfWriter pdfWriter = new PdfWriter(file);
             PdfDocument pdfDocument=new PdfDocument(pdfWriter);
             Document document=new Document(pdfDocument)) {

            Paragraph paragraph=new Paragraph("Users");
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);



            List<User> users = Database.users;

            float []columns={20,50,50,50,50,50};

            Table table=new Table(columns,true);

            table.addCell("id");
            table.addCell("name");
            table.addCell("phone");
            table.addCell("password");
            table.addCell("balance");
            table.addCell("type");

            for (User user : users) {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getName());
                table.addCell(user.getPhone());
                table.addCell(user.getPassword());
                table.addCell(String.valueOf(user.getBalance()));
                table.addCell(String.valueOf(user.getType()));
            }

            document.add(table);
            table.complete();

        } catch (IOException e) {
            return false;
        }
        return true;
    }


    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getPhone(), userDTO.getPassword());
        Database.users.add(user);
        writeUsers();
        return user;
    }

    private void writeUsers() {
        File file = new File(Database.BASE_FOLDER, "users.json");
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new Gson();
            out.write(gson.toJson(Database.users));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
