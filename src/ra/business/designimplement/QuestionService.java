package ra.business.designimplement;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.entity.Exam;
import ra.business.entity.Question;

import java.util.List;
import java.util.WeakHashMap;

public class QuestionService {
    public static void updateQuestion(List<Question> questionList) {
        while (true) {
            int count = 1;
            for (Question question : questionList) {
                System.out.println("câu số: " + count);
                System.out.println(question.getQuestionContent());
                count++;
            }
            System.out.println("Mời bạn chọn câu hỏi muốn sửa (số thứ tự)");
            System.out.println("chọn 0 để thoát");

            int choice = InputMethods.getInteger();
            if (choice <= 0) {
                System.out.println("đã tải lên gói câu hỏi mới mới thành công");
                break;
            }
            if (choice > questionList.size()) {
                System.out.println("Lựa chọn không chính xác");
                continue;
            }
            while (true) {
                System.out.println("chọn 0 để ngừng sửa câu hỏi này");
                System.out.println("chọn 1 để sửa tên");
                System.out.println("chọn 2 để sửa đáp án đúng");
                System.out.println("chọn 3 để sửa nội dung đáp án");
                System.out.println("mời bạn chọn");
               int choiceSmall = InputMethods.getInteger();
                if (choiceSmall <= 0) {
                    System.out.println("đã tải lên câu hỏi mới");
                    break;
                }
                switch (choiceSmall) {
                    case 1:
                        System.out.println("mời bạn nhập vào câu hỏi mới");
                        questionList.get(choice - 1).setQuestionContent(InputMethods.getString());
                        break;
                    case 2:
                        questionList.get(choice - 1)
                                .setAnswerTrue(questionList.get(choice - 1)
                                        .getInputAnswerTrue(questionList.get(choice - 1)
                                                .getAnswerOption()));
                        break;
                    case 3:
                        updateAnswer(questionList.get(choice - 1).getAnswerOption());
                        break;
                    default:
                        System.out.println("lựa chọn không chính xác");
                }
            }
        }
    }

    //    public static void addNewQuestion(List<Question> questionList) {
//        Question question = new Question();
//        question.inputData(questionList);
//        questionList.add(question);
//    }
//
    public static void deleteQuestion(List<Question> questionList) {
        displayQuestionList(questionList);
        System.out.println("Mời bạn nhập vào số thứ tự câu hỏi muốn xóa");
        int choice = InputMethods.getInteger();
        if (questionList.size() < choice) {
            System.out.println("lựa chọn của bạn nằm ngoài phạm vi");
        } else {
            questionList.remove(choice - 1);
            System.out.println("xóa thành công");
        }
    }

    public static void displayQuestionList(List<Question> questionList) {
        for (int i = 0; i < questionList.size(); i++) {
            System.out.println("câu hỏi thứ: " + (i + 1));
            System.out.println(questionList.get(i).getQuestionContent());
        }

    }

    public static void updateAnswer(List<String> listAnswer) {
        int count = 1;
        for (String s : listAnswer) {
            System.out.println("câu số: " + count);
            System.out.println(s);
            count++;
        }
        while (true) {
            System.out.println("Mời bạn chọn câu trả lời  muốn sửa (số thứ tự)");
            System.out.println("chọn 0 để thoát");
            int choice = InputMethods.getInteger();
            if (choice <= 0) {
                System.out.println("đã tải lên gói câu trả lời mới");
                break;
            }
            if (choice > listAnswer.size()) {
                System.out.println("Lựa chọn không chính xác");
                continue;
            }
            System.out.println("Mời bạn nhập vào câu trả lời mới");
            listAnswer.set(choice - 1, InputMethods.getString());
            System.out.println("sửa thành công");
        }
    }

}
