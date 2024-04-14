package ra.business.entity;

import ra.business.config.InputMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ra.business.designimplement.AuthenticationService.userList;

public class Question implements Serializable {
    private int questionId;
    private String questionContent;
    private List<String> answerOption;
    private int answerTrue;

    public Question() {
    }

    public Question(int questionId, String questionContent, List<String> answerOption, int answerTrue) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.answerOption = answerOption;
        this.answerTrue = answerTrue;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<String> getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(List<String> answerOption) {
        this.answerOption = answerOption;
    }

    public int getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(int answerTrue) {
        this.answerTrue = answerTrue;
    }

    public void inputData(List<Question> list) {
        this.questionId = getNewId(list);
        this.questionContent = getInputQuestionContent(list);
        this.answerOption = getInputListAnswer();
        this.answerTrue = getInputAnswerTrue(answerOption);
    }

    public void displayData() {
        System.out.printf("%-92s┃\n",this.questionContent);
        for (int i = 0; i < answerOption.size(); i++) {
            String answer = answerOption.get(i);
            System.out.printf("┃ Đáp án "+(i+1)+": %-89s┃\n",answer);
        }
    }

    public byte getInputAnswerTrue(List<String> list) {
        System.out.println("mời bạn nhập vào vị trí câu trả lời đúng (câu thứ mấy?)");
        while (true) {
            byte trueAnswer = InputMethods.getByte();
            if (trueAnswer > list.size() || trueAnswer < 1) {
                System.out.println("ban chỉ có" + list.size() + " cau trả lời");
            } else {
                return (byte) (trueAnswer - 1);
            }
        }
    }

    private List<String> getInputListAnswer() {
        List<String> answerList = new ArrayList<>();
        System.out.println("mời bạn nhập số lượng câu trả lời cho câu hỏi");
        byte AnswerQuantity = InputMethods.getByte();
        for (int i = 0; i < AnswerQuantity; i++) {
            System.out.println("câu "+(i+1));
            String answer = InputMethods.getString();
            answerList.add(answer);
        }
        return answerList;
    }


    private String getInputQuestionContent(List<Question> questionList) {
        System.out.println("mời bạn nhập nội dung câu hỏi");
        while (true) {
            String questionContentInput = InputMethods.getString();
            if (questionList.stream().anyMatch(question -> question.questionContent.equals(questionContentInput))) {
                System.out.println("câu hỏi đã tồn tại");
            } else {
                return questionContentInput;
            }
        }
    }

    private int getNewId(List<Question> list) {
        int max = list.stream().map(Question::getQuestionId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }

}
