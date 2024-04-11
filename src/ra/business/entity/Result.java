package ra.business.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import static ra.business.designimplement.AuthenticationService.currentUserList;
import static ra.business.designimplement.AuthenticationService.userList;
import static ra.business.designimplement.ExamService.examList;
import static ra.business.designimplement.ResultService.resultList;

public class Result {
    private int resultId;
    private int userId;
    private int examId;
    private int totalPoint;
    private LocalDate createdDate;

    public Result() {
    }

    public Result(int resultId, int userId, int examId, int totalPoint, LocalDate createdDate) {
        this.resultId = resultId;
        this.userId = userId;
        this.examId = examId;
        this.totalPoint = totalPoint;
        this.createdDate = createdDate;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }


    public void inputData(int currentExamId) {
        this.resultId = getNewId();
        this.userId = currentUserList.get(0).getUserId();
        this.examId = currentExamId;
        this.totalPoint = 0;
        this.createdDate = LocalDate.now();
    }

    private int getNewId() {
        int max = resultList.stream().map(Result::getResultId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }

    public void displayData() {
        String getUserName=userList.stream().filter(users -> users.getUserId()==this.userId).findFirst().orElse(null).getUserName();
        String getExamName=examList.stream().filter(exam -> exam.getExamId()==this.examId).findFirst().orElse(null).getTitle();
        String date = this.createdDate.toString();
        System.out.println("Đề:  "+getExamName);
        System.out.println("Người thi:" +getUserName);
        System.out.println("Điểm: " +this.totalPoint);
        System.out.println("ngày thi: "+date);
    }
}
