package com.company.ui;

import com.company.App;
import com.company.entity.History;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.service.*;
import com.company.util.ScannerUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserUI {
    static UserService userService = new UserServiceImpl();
    static TestService testService = new TestServiceImpl();
    static HistoryService historyService = new HistoryServiceImpl();
    public static void userInterface() {
        while (true){
            System.out.println("1. Solve test\n2. Fill balance\n3. Show results\n0. Log out");

            switch (ScannerUtil.numIn.nextInt()){
                case 1:
                    TestUI.test();
                    break;
                case 2:
                    fillBalance();
                    break;
                case 3:
                    showResults();
                    break;
                case 0:{App.currUser=null; return;}
            }
        }

    }

    private static void showResults() {
        List<History> histories = historyService.getHistories(App.currUser.getId());
        if(histories==null || histories.isEmpty()){
            System.out.println("no history");
        }
        System.out.printf("%-2s %-10s %-5s %-20s %-20s","id","subject","score","     startedAt","     finishedAt");
        System.out.println();
        for (History history : histories) {
            System.out.printf("%-2d %-10s %-5s %-20s   %-20s",history.getId(),history.getSubject(),""+history.getCorrects()+"/"+history.getQuantity(),
                    history.getStartedAt(),history.getFinishedAt()+"\n");
        }
    }

    private static void fillBalance() {
        System.out.println("Balans : "+App.currUser.getBalance());

        System.out.println("Enter money");
        double money = ScannerUtil.numIn.nextDouble();

        if(money<0){
            System.out.println("Wrong");
            return;
        }
        if (userService.fillBalance(money, App.currUser.getId())) {
            System.out.println("Success");
            System.out.println("Your balance changed to : "+App.currUser.getBalance()+" $");
        }

    }


    public static void adminInterface() {
        while (true){
            System.out.println("         1. Show subjects           5. Edit question\n" +
                               "         2. Show questions          6. Add question\n" +
                               "         3. Delete subject          7. Add subject\n" +
                               "         4. Delete question         0. Log out");
            switch (ScannerUtil.numIn.nextInt()){
                case 1:
                    printSubjects();
                    break;
                case 2:
                    printQuestions();
                    break;
                case 3:
                    deleteSubject();
                    break;
                case 4:
                    deleteQuestion();
                    break;
                case 5:
                    edit();
                    break;
                case 6:
                    addQuestion();
                    break;
                case 7:
                    addSubject();
                    break;
                case 0: { App.currUser=null; return;}
            }
        }

    }

    private static void addSubject() {
        System.out.println("Fan nomini kiriting");
        String sub = ScannerUtil.textIn.nextLine().toUpperCase();
        if(testService.doesHaveSub(sub)){
            System.out.println("\033[1;31m"+"This subject already exists");
            return;
        }

        System.out.println("Enter price of the test of the subject");
        double price = ScannerUtil.numIn.nextDouble();
        testService.addSubject(sub,price);
        System.out.println(sub+" is added");
    }

    private static void addQuestion() {
        if(testService.getSubjects()== null || testService.getSubjects().isEmpty()){
            System.out.println("No subjects. Firstly, add subject");
            return;
        }
        printSubjects();
        System.out.print("Select subject => ");

        int subId = ScannerUtil.numIn.nextInt();
        if (!checkSubjectById(subId)) {
            System.out.println("wrong id");
            return;
        }

        System.out.println("Enter text of question");
        String text = ScannerUtil.textIn.nextLine();

        System.out.println("Enter correct answer");
        String ans = ScannerUtil.textIn.nextLine();

        System.out.println("Enter 3 wrong answers");
        String ans1=ScannerUtil.textIn.nextLine();
        String ans2=ScannerUtil.textIn.nextLine();
        String ans3=ScannerUtil.textIn.nextLine();

        Question question = new Question();
        question.setSubjectId(subId);
        question.setAns(ans);
        question.setWrongAns(new ArrayList<>(List.of(ans1,ans2,ans3)));
        question.setText(text);
        testService.addQuestion(question);
        System.out.println("question added");
    }

    private static void edit() {
        if(testService.getQuestions()==null || testService.getQuestions().isEmpty()){
            System.out.println("No questions to edit");
            return;
        }
        printQuestions();
        System.out.print("Select question to edit => ");
        int id = ScannerUtil.numIn.nextInt();
        Question question = testService.checkAndGetQuestion(id);
        if(question==null){
            System.out.println("Wrong id entered");
            return;
        }

        System.out.println("Text : "+question.getText());
        System.out.println("'Enter' -> Skip  || Enter new text");
        String text = ScannerUtil.textIn.nextLine();
        if(!text.isBlank()){
            text=question.getText();
        }
        System.out.println("Enter new correct answer");
        String ans = ScannerUtil.textIn.nextLine();

        System.out.println("Enter 3 wrong answers");
        String ans1 = ScannerUtil.textIn.nextLine();
        String ans2 = ScannerUtil.textIn.nextLine();
        String ans3 = ScannerUtil.textIn.nextLine();

        question.setAns(ans);
        question.setWrongAns(new ArrayList<>(List.of(ans1,ans2,ans3)));
        System.out.println("Editing is finished successfilly");
    }

    private static void deleteQuestion() {
        if(testService.getQuestions()==null || testService.getQuestions().isEmpty()){
            System.out.println("No questions to delete");
            return;
        }
        printQuestions();
        System.out.print("Select question to delete => ");
        int id = ScannerUtil.numIn.nextInt();
        Question question = testService.checkAndGetQuestion(id);
        if(question==null){
            System.out.println("Wrong id entered");
            return;
        }
        testService.deleteQuestion(id);
        System.out.println("Question deleted successfully");
    }

    private static void deleteSubject() {
        if(testService.getSubjects()==null  ||testService.getSubjects().isEmpty()){
            System.out.println("No subjects to delete");
            return;
        }
        printSubjects();
        System.out.print("Select subject to delete => ");
        int id = ScannerUtil.numIn.nextInt();
        if (checkSubjectById(id)) {
            testService.deleteSubject(id);
            System.out.println("Subject deleted");
        }else {
            System.out.println("wrong id entered");
        }
    }

    private static void printQuestions() {
        List<Question> questions = testService.getQuestions();
        if(questions==null  ||questions.isEmpty()){
            System.out.println("No questions");
            return;
        }

        System.out.printf("%2s -> %15s","Id","Text\n");
        for (Question question : questions) {
            System.out.printf("%2d -> %15s\n",question.getId(),question.getText());
            System.out.println();
        }
    }

    private static void printSubjects() {
        List<Subject> subjects = testService.getSubjects();
        if(subjects==null  || subjects.isEmpty()){
            System.out.println("No subjects");
            return;
        }
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println(subjects.get(i).getId()+" = >"+subjects.get(i).getName());
        }
    }
    private static boolean checkSubjectById(int id){
        Subject sub = testService.getSub(id);
        if(sub!=null){
            return true;
        }
        return false;
    }

}
