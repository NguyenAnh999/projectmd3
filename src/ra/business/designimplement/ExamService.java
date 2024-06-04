package ra.business.designimplement;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.entity.Exam;
import ra.business.entity.Question;

import javax.sound.midi.MidiFileFormat;
import java.util.ArrayList;
import java.util.List;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class ExamService {
    public static List<Exam> examList;

    static {
        examList = IO_file.readObjFromFile(IO_file.EXAM_PATH);
    }

    public static Exam findById(int ID) {
        return examList.stream()
                .filter(exam -> exam.getExamId() == ID)
                .findFirst().orElse(null);
    }

    public static void addNewExam() {
        Exam exam = new Exam();
        exam.inputData();
        examList.add(exam);
        System.out.println("bạn đã thêm đề thi thành công ");
        IO_file.writeObjFromFile(examList, IO_file.EXAM_PATH);
    }

    public static void displayExamListOfUser() {
        if (examList.isEmpty()) {
            System.out.println("hiện tại chưa có đề thi nào được thêm");
        } else {
            int count = 1;
            for (Exam exam : examList) {
                if (exam.getUserId() != currentUserList.get(0).getUserId()) {
                    continue;
                }
                System.out.println("============Đê thi số: " + count + "================");
                System.out.printf("mã đề: %s  | Đề thi: %s | trạng thái: %s", exam.getExamId(), exam.getTitle(), exam.isStatus() ? "open" : "blook");
                System.out.println("\n=============================================================================");
                count++;
            }
        }
    }

    public static void displayExamDetail() {
        displayExamListOfUser();
        if (!examList.isEmpty()) {
            System.out.println("mời bạn nhập váo id đề thi muốn xem");
            byte choiceId = InputMethods.getByte();
            if (examList.stream().anyMatch(exam -> exam.getExamId() == choiceId)) {
                examList.stream().filter(exam -> exam.getExamId() == choiceId).findFirst().orElse(null).displayDataForTeach();
            } else {
                System.out.println("mã đề bạn nhập không tồn tại");
            }
        }
    }

    public static void deleteExam() {
        displayExamListOfUser();
        if (!examList.isEmpty()) {
            System.out.println("Mời bạn nhập vào ID đề muốn xóa");
            int id = InputMethods.getInteger();
            if (findById(id) == null) {
                System.out.println("lựa chọn của bạn nằm ngoài phạm vi");
            } else {
                examList.remove(findById(id));
                IO_file.writeObjFromFile(examList, IO_file.EXAM_PATH);
                System.out.println("xóa thành công");
            }
        }
    }

    public static void updateStatusExam() {
        displayExamListOfUser();
        if (!examList.isEmpty()) {
            System.out.println("Mời bạn nhập vào ID đề muốn sửa trạng thái");
            int id = InputMethods.getInteger();
            if (findById(id) == null) {
                System.out.println("lựa chọn của bạn nằm ngoài phạm vi");
            } else {
                System.out.println("mời bạn nhập vào trạng thái mới(true=open|false=blook)");
                boolean newStatus = InputMethods.getBoolean();
                findById(id).setStatus(newStatus);
                IO_file.writeObjFromFile(examList, IO_file.EXAM_PATH);
                System.out.println("thay đổi thành công");
            }
        }
    }

    public static void updateExam() {
        displayExamListOfUser();
        if (!examList.isEmpty()) {
            System.out.println("Mời bạn nhập vào ID đề muốn sửa");
            int id = InputMethods.getInteger();
            if (findById(id) == null) {
                System.out.println("lựa chọn của bạn nằm ngoài phạm vi");
            } else {
                boolean isExit = true;
                while (isExit) {
                    System.out.println("Chọn trường muốn sửa:");
                    System.out.println("1. Tên đề thi");
                    System.out.println("2. Mô tả đề thi");
                    System.out.println("3. Thời gian làm bài");
                    System.out.println("4. các câu hỏi");
                    System.out.println("5. các danh mục");
                    System.out.println("6. trạng thái");
                    System.out.println("7. xóa câu hỏi");
                    System.out.println("0. Thoát");
                    System.out.print("Chọn: ");
                    // Nhập lựa chọn từ người dùng
                    int choice = InputMethods.getInteger();
                    // Xử lý lựa chọn sử dụng switch case
                    switch (choice) {
                        case 1:
                            System.out.println("mời bạn nhập vào tiêu đề mới");
                            String titleName = InputMethods.getString();
                            findById(id).setTitle(titleName);
                            System.out.println("thay đổi thành công");
                            break;
                        case 2:
                            System.out.println("mời bạn nhập vào mô tả mới");
                            String newDescription = InputMethods.getString();
                            findById(id).setDescription(newDescription);
                            System.out.println("thay đổi thành công");
                            break;
                        case 3:
                            System.out.println("mời bạn nhập vào thời gian làm bài mới");
                            long newDuration = InputMethods.getLong();
                            findById(id).setDuration(newDuration);
                            System.out.println("thay đổi thành công");
                            break;
                        case 4:
                            QuestionService.updateQuestion(findById(id).getListQuestion());
                            System.out.println("thay đổi thành công");
                            break;
                        case 5:
                            findById(id).setCatalogId(findById(id).getInputCatalogId());
                            System.out.println("thay đổi thành công");
                            break;
                        case 6:
                            System.out.println("mời bạn nhập vào trạng thái mới(true=open|false=blook)");
                            boolean newStatus = InputMethods.getBoolean();
                            findById(id).setStatus(newStatus);
                            System.out.println("thay đổi thành công");
                            break;
                        case 7:
                            QuestionService.deleteQuestion(findById(id).getListQuestion());
                            break;
                        case 0:
                            IO_file.writeObjFromFile(examList, IO_file.EXAM_PATH);
                            System.out.println("up date thành công");
                            isExit = false;
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ.");
                            break;
                    }
                }
            }
        }

    }

}





























