package ra.business.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static ra.business.designimplement.AuthenticationService.currentUserList;
import static ra.business.designimplement.AuthenticationService.userList;
import static ra.business.designimplement.CatalogService.catalogsList;
import static ra.business.designimplement.ExamService.examList;
import static ra.business.designimplement.ResultService.resultDetailList;
import static ra.business.designimplement.ResultService.resultList;

public class Result implements Serializable {
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
        String getUserName = userList.stream().filter(users -> users.getUserId() == this.userId).findFirst().orElse(null).getUserName();
        String getExamName = examList.stream().filter(exam -> exam.getExamId() == this.examId).findFirst().orElse(null).getTitle();
        String date = this.createdDate.toString();

        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.println("│                 BẢNG ĐIỂM KẾT QUẢ THI                │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.printf("│ Đề: %-48s │\n", getExamName);
        System.out.printf("│ Người thi: %-41s │\n", getUserName);
        System.out.printf("│ Điểm: %-46s │\n", this.totalPoint);
        System.out.printf("│ Ngày thi: %-42s │\n", date);
        System.out.println("└──────────────────────────────────────────────────────┘");

    }

    public void displayDataAll() {
        displayData();
        Exam thisExam = examList.stream().filter(exam -> exam.getExamId() == this.examId).findFirst().orElse(null);
        displayDataForTeach(thisExam);
    }

    private void displayDataForTeach(Exam exam) {
        displayDataForUser(exam);
        for (int i = 0; i < exam.getListQuestion().size(); i++) {
            System.out.print("┃ Câu " + (1 + i) + " :");
            exam.getListQuestion().get(i).displayData();
            ResultDetail currentResul = getUserChoice(i);
            System.out.printf("┃ Đáp án đúng là câu thứ: %-75s┃\n", (exam.getListQuestion().get(i).getAnswerTrue()) + 1);
            System.out.printf("┃ Đáp án bạn chọn là câu: %-75s┃\n", (currentResul.getIndexChoice())+1);
            System.out.printf("┃ Đáp đó là: %-88s┃\n", currentResul.isCheck()?"Đáp án dúng":"Đáp án sai" );
            if (i == exam.getListQuestion().size() - 1) {
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            } else {
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            }
        }
    }

    private void displayDataForUser(Exam exam) {
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("┃                                   %-65s┃\n", exam.getTitle().toUpperCase());
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃ Thời gian làm bài: %-4s phút %-16s                                                      ┃\n", exam.getDuration(), " ");
        System.out.printf("┃ Môt tả: %-91s┃\n", exam.getDescription());
        String teachName = userList.stream().filter(users -> users.getUserId() == this.userId).findFirst().orElse(null).getFullName();
        System.out.printf("┃ người ra đề : %-85s┃\n", teachName);
        System.out.print("┃ các danh mục: ");
        System.out.printf("%-85s┃\n", idToName(exam));
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

    }

    private List<String> idToName(Exam exam) {
        return exam.getCatalogId().stream()
                .map(catalogs -> catalogsList.stream().filter(catalogs1 -> catalogs1.getCatalogId().equals(catalogs))
                        .findFirst().orElse(null).getCatalogName()).toList();
    }

    private ResultDetail getUserChoice(int index) {
        return resultDetailList.stream().filter(resultDetail -> resultDetail.getResultId() == this.resultId)
                .filter(resultDetail -> resultDetail.getIndexQuestion() == index).findFirst().orElse(null);
    }
}
