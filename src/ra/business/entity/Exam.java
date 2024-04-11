package ra.business.entity;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.CatalogService;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ra.business.designimplement.AuthenticationService.currentUserList;
import static ra.business.designimplement.AuthenticationService.userList;
import static ra.business.designimplement.CatalogService.catalogsList;
import static ra.business.designimplement.ExamService.examList;

public class Exam implements Serializable {
    private int examId;
    private int userId;
    private String description;
    private long duration;
    private List<Question> listQuestion;
    private LocalDate createdAt;
    private boolean status;
    private List<String> catalogId;
    private String title;

    public Exam() {
    }

    public Exam(int examId, int userId, String description, long duration, List<Question> listQuestion, LocalDate createdAt, boolean status, List<String> catalogId, String title) {
        this.examId = examId;
        this.userId = userId;
        this.description = description;
        this.duration = duration;
        this.listQuestion = listQuestion;
        this.createdAt = createdAt;
        this.status = status;
        this.catalogId = catalogId;
        this.title = title;
    }

    public int getExamId() {
        return examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<Question> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(List<String> catalogId) {
        this.catalogId = catalogId;
    }

    public void inputData() {
        this.examId = getInputExamId();
        this.title = getInputTitle();
        this.userId = getInputUserId();
        this.description = getInputDescription();
        this.duration = getInputDuration();
        this.listQuestion = getInputListQuestion();
        this.createdAt = getDateInout();
        this.status = getInputStatus();
        this.catalogId = getInputCatalogId();
    }

    private String getInputTitle() {
        System.out.println("mời bạn nhập vào tiêu đề bai thi");
        return InputMethods.getString();
    }

    private int getInputExamId() {
        int max = examList.stream().map(Exam::getExamId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }

    private int getInputUserId() {
        return currentUserList.get(0).getUserId();
    }

    private String getInputDescription() {
        System.out.println("mời bạn nhập mô tả");
        return InputMethods.getString();
    }

    private long getInputDuration() {
        System.out.println("mời bạn nhập thời gian làm bài");
        return InputMethods.getLong();
    }

    private List<Question> getInputListQuestion() {
        System.out.println("Mời bạn nhập số câu hỏi có trong bài");
        int questionQuantity = InputMethods.getInteger();
        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < questionQuantity; i++) {
            Question question = new Question();
            question.inputData(questionList);
            questionList.add(question);
        }
        return questionList;
    }

    private LocalDate getDateInout() {
        return LocalDate.now();
    }


    private boolean getInputStatus() {
        return true;
    }

    public List<String> getInputCatalogId() {
        List<String> listString = new ArrayList<>();
        if (catalogsList.isEmpty()) {
            System.out.println("hiện tại chưa có danh mục nào,bạn có muốn tạo mới không");
            System.out.println("1:có");
            System.out.println("2:không");
            while (true) {
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        return creatCategoryList(listString);
                    case 2:
                        return listString;
                    default:
                        System.out.println("lựa chọn sai mời chọn lại");
                        break;
                }
            }
        } else {
            System.out.println("bạn muốn thêm danh mục hay chọn từ danh mục đã có");
            System.out.println("1:thêm danh mục mới");
            System.out.println("2:chọn từ danh mục đã có");
            while (true) {
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        return creatCategoryList(listString);
                    case 2:
                        return choiceCategorylist(listString);
                    default:
                        System.out.println("lựa chọn sai mời chọn lại");
                        break;
                }
            }
        }
    }

    private List<String> choiceCategorylist(List<String> list) {
        int count = 1;
        for (Catalogs catalogs : catalogsList) {
            System.out.println("danh mục số: " + count);
            catalogs.displayData();
            count++;
        }
        while (true) {
            System.out.println("mời bạn chọn danh mục hoặc bấm [0] để thoát ");
            int choice = InputMethods.getInteger();
            if (choice <= 0) {
                break;
            }
            if (choice > count) {
                System.out.println("lựa chọn không chính xác");
            } else {
                list.add(catalogsList.get(choice - 1).getCatalogId());
                System.out.println("Thêm danh mục: " + catalogsList.get(choice - 1).getCatalogName() + " thành công");
            }
        }
        return list;
    }


    private List<String> creatCategoryList(List<String> listString) {
        System.out.println("Mời bạn nhập vào số danh mục muốn thêm");
        byte categoryQuantity = InputMethods.getByte();
        for (int i = 0; i < categoryQuantity; i++) {
            Catalogs catalogs = new Catalogs();
            catalogs.inputData();
            catalogsList.add(catalogs);
            listString.add(catalogs.getCatalogId());
        }
        System.out.println("đã thêm danh mục mới tạo vào đề thi thành công");
        return listString;
    }

    public void displayDataForTeach() {
        displayDataForUser();
        for (int i = 0; i < this.listQuestion.size(); i++) {
            System.out.print("Câu " + (1 + i) + " :");
            this.listQuestion.get(i).displayData();
            System.out.println("\n=================================================================================================\n");
        }
    }
    public void displayDataForUser() {
        System.out.println("=========================================" + this.title + "==================================================");
        System.out.println("Thời gian làm bài: " + this.duration + " phút");
        System.out.println("Môt tả: " + this.description);
        String teachName = userList.stream().filter(users -> users.getUserId() == this.userId).findFirst().orElse(null).getFullName();
        System.out.println("người ra đề : " + teachName);
        System.out.print("các danh mục: ");
        System.out.println(idToName());
    }

    public List<String> idToName() {
        return this.catalogId.stream()
                .map(catalogs -> catalogsList.stream().filter(catalogs1 -> catalogs1.getCatalogId().equals(catalogs))
                        .findFirst().orElse(null).getCatalogName()).toList();
    }
}
