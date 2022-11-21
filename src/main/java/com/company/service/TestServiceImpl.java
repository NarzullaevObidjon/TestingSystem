package com.company.service;

import com.company.db.Database;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.util.Result;
import com.google.gson.Gson;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestServiceImpl implements TestService {
    @Override
    public boolean addSubject(String name, double price) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setPrice(price);
        Database.subjects.add(subject);
        return rewriteSubjects();
    }

    @Override
    public List<Subject> getSubjects() {
        return Database.subjects;
    }

    @Override
    public Subject getSub(int id) {
        for (Subject subject : Database.subjects) {
            if (subject.getId() == id) {
                return subject;
            }
        }
        return null;
    }

    @Override
    public boolean doesHaveSub(String s) {
        if (Database.subjects == null || Database.subjects.isEmpty()) {
            return false;
        }
        for (Subject subject : Database.subjects) {
            if (subject.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addQuestion(Question question) {
        Database.questions.add(question);
        rewriteQuestions();
    }

    @Override
    public Question checkAndGetQuestion(int id) {
        if (Database.questions == null || Database.questions.isEmpty()) {
            return null;
        }
        for (Question question : Database.questions) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }

    @Override
    public List<Question> getQuestions() {
        return Database.questions;
    }


    @Override
    public void deleteQuestion(int id) {
        for (Iterator<Question> iterator = Database.questions.iterator(); iterator.hasNext(); ) {
            Question question = iterator.next();
            if (question.getId() == id) {
                iterator.remove();
            }
        }
        rewriteQuestions();
    }


    @Override
    public void deleteSubject(int id) {

        for (Iterator<Subject> iterator = Database.subjects.iterator(); iterator.hasNext(); ) {
            Subject subject = iterator.next();
            if (subject.getId() == id) {
                iterator.remove();
            }
        }
        for (Iterator<Question> iterator = Database.questions.iterator(); iterator.hasNext(); ) {
            Question question = iterator.next();
            if (question.getSubjectId() == id) {
                iterator.remove();
            }
        }
        rewriteSubjects();
        rewriteQuestions();
    }

    public boolean rewriteQuestions() {
        Gson gson = new Gson();
        File file = new File(Database.BASE_FOLDER, "questions.json");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(gson.toJson(Database.questions));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean rewriteSubjects() {
        Gson gson = new Gson();
        File file = new File(Database.BASE_FOLDER, "subjects.json");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(gson.toJson(Database.subjects));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Question> getQuestionsBySubject(int subId) {
        List<Question> questions = new ArrayList<>();
        if (Database.questions == null || Database.questions.isEmpty()) {
            return null;
        }
        for (Question question : Database.questions) {
            if (question.getSubjectId() == subId) {
                questions.add(question);
            }
        }
        return questions;
    }

    @Override
    public boolean toExcel() {
        File file = new File(Database.BASE_FOLDER, "questions.xlsx");
        List<Question> questionList = Database.questions;
        List<Subject> subjectList = Database.subjects;
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(file)) {
            for (Subject subject : subjectList) {
                int rowIndex = 0;
                XSSFSheet sheet = workbook.createSheet(subject.getName());
                XSSFRow rowSheet = sheet.createRow(0);
                rowSheet.createCell(0).setCellValue("Id");
                rowSheet.createCell(1).setCellValue("Subject Id");
                rowSheet.createCell(2).setCellValue("Question");
                rowSheet.createCell(3).setCellValue("Answer");
                rowSheet.createCell(4).setCellValue("Wrong Answers");
                for (Question question : questionList) {
                    if (question.getSubjectId() == subject.getId()) {
                        XSSFRow rowOrder = sheet.createRow(++rowIndex);
                        rowOrder.createCell(0).setCellValue(question.getId());
                        rowOrder.createCell(1).setCellValue(question.getSubjectId());
                        rowOrder.createCell(2).setCellValue(question.getText());
                        rowOrder.createCell(3).setCellValue(question.getAns());
                        rowOrder.createCell(4).setCellValue(question.getWrongAns().toString());
                    }
                }
                for (int j = 0; j < 5; j++) {
                    sheet.autoSizeColumn(j);
                }
            }
            workbook.write(out);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean getSubject() {
        File file = new File(Database.BASE_FOLDER, "subjects.docx");
        file.getParentFile().mkdirs();
        List<Subject> subjects = Database.subjects;

        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(file)) {

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setFontSize(16);
            run.setText("Subjects");

            XWPFTable table = document.createTable();
            table.setWidth("100%");

            XWPFTableRow headerRow =table.getRow(0);

            XWPFTableCell cell00 = headerRow.getCell(0);
            cell00.setText("Id");

            XWPFTableCell cell01 = headerRow.createCell();
            cell01.setText("Name");

            XWPFTableCell cell02 = headerRow.createCell();
            cell02.setText("Price");

            for (Subject subject : subjects) {
                XWPFTableRow row =table.createRow();
                row.getCell(0).setText(String.valueOf(subject.getId()));
                row.getCell(1).setText(subject.getName());
                row.getCell(2).setText(String.valueOf(subject.getPrice()));
            }

            XWPFParagraph footerParagraph = document.createParagraph();
            footerParagraph.setAlignment(ParagraphAlignment.RIGHT);
            footerParagraph.createRun().setText(
                    "\n"+ LocalDateTime.now()+" xolatiga ko'ra."
            );

            document.write(out);

        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
