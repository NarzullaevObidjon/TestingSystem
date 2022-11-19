package com.company.ui;

import com.company.App;
import com.company.entity.History;
import com.company.entity.Question;
import com.company.entity.Subject;
import com.company.service.HistoryService;
import com.company.service.HistoryServiceImpl;
import com.company.util.ScannerUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.company.ui.UserUI.testService;

public class TestUI {
    static HistoryService historyService= new HistoryServiceImpl();
    public static void test() {
        List<Subject> subjects = testService.getSubjects();

        while (true){
            if(subjects.isEmpty()){
                System.out.println("We don't have any tests . Please try again a few moments later");
                return;
            }
            System.out.println("There is "+subjects.size()+" subjects to solve test");
            for (Subject subject : subjects) {
                System.out.println(subject.getId()+" => "+subject.getName());
            }
            System.out.println("0->Exit || Select subject => ");
            int id = ScannerUtil.numIn.nextInt();
            if(id==0)  break;

            List<Question> questionsBySubject = testService.getQuestionsBySubject(id);
            if(questionsBySubject==null || questionsBySubject.size()<5){
                System.out.println("No questions");
                break;
            }

            System.out.println("Select amount of test");
            int size = questionsBySubject.size();
            List<Integer> amountQues = new ArrayList<>();
            for (int i = 5; i <= size; i*=2) {
                amountQues.add(i);
                System.out.println(i +" tests");
            }
            int quantity = ScannerUtil.numIn.nextInt();

            if(!amountQues.contains(quantity)){
                System.out.println("wrong number is entered");
                continue;
            }
            Subject subject = testService.getSub(id);
            System.out.printf(quantity+" tests stands "+quantity*subject.getPrice()+" $\n");
            if(App.currUser.getBalance()<quantity*subject.getPrice()){
                System.out.println("\nYou can't take a test becouse of your balance is not enough\n");
                continue;
            }else {
                System.out.println("Are you sure pay and take a test ?\n 1. Yes(Start test)\n0. No(Exit)");
                int dec = ScannerUtil.numIn.nextInt();
                if(dec==0) return;
                if(dec!=1) break;
                App.currUser.setBalance(App.currUser.getBalance()-quantity*subject.getPrice());
            }
            List<Question> questions = questionsBySubject.subList(0, quantity);
            LocalTime time;
            int count =0;
            int score=0;
            LocalDateTime startedAt = LocalDateTime.now();
            while (count<questions.size()){

                time=LocalTime.now();
                System.out.println("\033[0m"+"Start: "+time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                System.out.println("Dedline: "+time.plusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                System.out.println((count+1)+"-question: "+questions.get(count).getText());
                System.out.println("Answers:");
                int correctAnswerIndex= (int) (Math.random() * 3)+ 65;
                int k=65;
                for(int i=0;i<3;i++){
                    if(k==correctAnswerIndex){
                        System.out.println((char)k+"->"+questions.get(count).getAns());
                        k++;
                    }
                    System.out.println((char)k+"->"+questions.get(count).getWrongAns().get(i));
                    k++;
                }
                System.out.println("\033[0m"+"Tanlang: ");
                char answer=ScannerUtil.textIn.nextLine().charAt(0);
                if(time.plusMinutes(1).isBefore(LocalTime.now())){
                    System.out.println("\033[1;31m"+"This answer is not accepted becouse of time is out");
                }else{
                    if(answer==correctAnswerIndex){
                        score++;
                        System.out.println("\033[1;32m"+"Well done");
                    }else {
                        System.out.println("\033[1;31m"+"Wrong answer");
                    }
                }
                System.out.println("\033[1;34m"+"Your score is "+score);
                count++;
            }
            LocalDateTime finishedAt = LocalDateTime.now();

            History history = new History(App.currUser.getId(),subject.getName(),quantity,score,startedAt.format(DateTimeFormatter.ofPattern("HH:mm:ss | dd-MM-yyyy")),
                    finishedAt.format(DateTimeFormatter.ofPattern("HH:mm:ss | dd-MM-yyyy")));
            historyService.addHistory(history);
            System.out.println("\033[0m"+"Test is finished");
            System.out.println("Your total score is "+score);
        }


    }
}
