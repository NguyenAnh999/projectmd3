package ra.business.designimplement;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.entity.*;

import java.text.AttributedString;

import static ra.business.designimplement.AuthenticationService.currentUserList;
import static ra.business.designimplement.AuthenticationService.userList;
import static ra.business.designimplement.CatalogService.catalogsList;
import static ra.business.designimplement.ExamService.examList;
import static ra.business.designimplement.ResultService.resultDetailList;
import static ra.business.designimplement.ResultService.resultList;

public class UserService {
    public static void displayExamList() {
        int count = 1;
        for (Exam exam : examList) {
            System.out.println("-----------------Đê thi số: " + count + "-------------------------");
            System.out.printf("mã đề: %s  | Đề thi: %s | trạng thái: %s\n", exam.getExamId(), exam.getTitle(), exam.isStatus() ? "open" : "blook");
            System.out.println("==============================================================================");
            count++;
        }
    }

    public static void takeAnExam() {
        if (examList.isEmpty()) {
            System.out.println("hiện tại chưa có đề nào mời bạn quay lại sau");
        } else {
            displayExamList();
            System.out.println("mời bạn chọn đề theo số");
            int choice = InputMethods.getInteger();
            examList.get(choice - 1).displayDataForUser();
            System.out.println("============================== Bắt đầu thi ====================================");
            Result result = new Result();
            result.inputData(examList.get(choice - 1).getExamId());
            takeAnExamDetail(choice, result.getResultId(), result);
            result.setTotalPoint(
                    examList.get(choice - 1).getListQuestion().size() / 100 * result.getTotalPoint()
            );
            resultList.add(result);
            IO_file.writeObjFromFile(resultList, IO_file.RESUL_PATH);
            System.out.println("kết thúc bài thi, bạn có thể xem lại điểm của mình trong phần quản lý");
        }
    }
    public static void takeAnExamDetail(int choice, int resultId, Result result) {
        for (int i = 0; i < examList.get(choice - 1).getListQuestion().size(); i++) {

            System.out.print("Câu " + (1 + i) + " :");

            examList.get(choice - 1).getListQuestion().get(i).displayData();
            System.out.println("mời bạn chọn đáp án (1-" + examList.get(choice - 1).getListQuestion().get(i).getAnswerOption().size() + ")");

            int choiceIndexAnswer = choiceAnswer(examList.get(choice - 1).getListQuestion().get(i).getAnswerOption().size());

            int trueIndexAnswer = examList.get(choice - 1).getListQuestion().get(i).getAnswerTrue();

            boolean checkAnswer = false;
            if (trueIndexAnswer == choiceIndexAnswer) {
                checkAnswer = true;
                result.setTotalPoint(result.getTotalPoint() + 1);
            }
            ResultDetail resultDetail = new ResultDetail(resultId, i, choiceIndexAnswer, checkAnswer);
            resultDetailList.add(resultDetail);
            IO_file.writeObjFromFile(resultDetailList,IO_file.RESUL_DETAIL_PATH);
            System.out.println("\n=================================================================================================\n");
        }
    }


    private static int choiceAnswer(int questQuantity) {
        while (true) {
            int trueAnswer = InputMethods.getInteger();
            if (trueAnswer > questQuantity || trueAnswer < 1) {
                System.out.println("lựa chọn không hợp lệ");
            } else {
                return trueAnswer - 1;
            }

        }
    }

    public static void finExamByName() {
        if (examList.isEmpty()){
            System.out.println("hiện tại chưa có đề thi nào");
        }{
        System.out.println("mời bạn nhập vào tên đề thi muốn tìm");
        String examName = InputMethods.getString();
        if (examList.stream().anyMatch(exam -> exam.getTitle().contains(examName))) {
            examList.stream().filter(exam -> exam.getTitle().contains(examName)).forEach(UserService::displayExamListOfUser);
        } else {
            System.out.println("không tìm thấy đề nào như bạn đã tìm");
        }
        }
    }

    public static void finExamByCatalog() {
        System.out.println("mời bạn nhập vào tên danh mục đề thi muốn tìm");
        String catalogName = InputMethods.getString();
        Catalogs catalog = catalogsList.stream().filter(catalogs -> catalogs.getCatalogName().contains(catalogName)).findFirst().orElse(null);
        if (catalog == null) {
            System.out.println("không tìm thấy bài thi nào theo yêu cầu");
        } else {
            String catalogId = catalog.getCatalogId();
            examList.stream().filter(exam -> exam.getCatalogId().stream().filter(c -> c.contains(catalogId)).isParallel())
                    .toList().forEach(UserService::displayExamListOfUser);
        }

    }

    private static void displayExamListOfUser(Exam exam) {
        System.out.printf("mã đề: %s  | Đề thi: %s | trạng thái: %s", exam.getExamId(), exam.getTitle(), exam.isStatus() ? "open" : "blook");
        System.out.println("\n=============================================================================");
    }


    public static void updatePassword() {
        int updatePasswordID = currentUserList.get(0).getUserId();
        System.out.println("mời bạn nhập vào mật khẩu mới");
        finByID(updatePasswordID).setPassword( finByID(updatePasswordID).getInputPassword());
        IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
        System.out.println("đổi mật khẩu thành công");
    }


    public static void updateUserInformation() {
        int updateID = currentUserList.get(0).getUserId();
        boolean isExit = true;
        while (isExit) {
            System.out.println("Chọn trường muốn sửa:");
            System.out.println("1. họ");
            System.out.println("2. tên");
            System.out.println("3. mật khẩu");
            System.out.println("4. email");
            System.out.println("5. số điện thoại");
            System.out.println("6. địa chỉ");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    System.out.println("mời bạn nhập họ mới");
                    finByID(updateID).setFirstName(InputMethods.getString());
                    break;
                case 2:
                    System.out.println("mời bạn nhập tên mới");
                    finByID(updateID).setLastName(InputMethods.getString());
                    break;
                case 3:
                    updatePassword();
                    break;
                case 4:
                    finByID(updateID).setEmail(finByID(updateID).getInputEmail());
                    break;
                case 5:
                    finByID(updateID).setPhoneNumber(finByID(updateID).getInputPhoneNumber());
                    break;
                case 6:
                    finByID(updateID).setAddress(finByID(updateID).getInputAddress());
                    break;
                case 0:
                    IO_file.writeObjFromFile(userList,IO_file.USER_PATH);
                    isExit = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }


    private static Users finByID(int Id) {
        return userList.stream().filter(users -> users.getUserId() == Id).findFirst().orElse(null);
    }


}


