package ra.business.designimplement;

import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.ls.LSOutput;
import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.entity.*;

import javax.crypto.spec.PSource;
import java.text.AttributedString;
import java.util.List;

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
            if (examList.get(choice - 1).isStatus()) {

                examList.get(choice - 1).displayDataForUser();
                System.out.println("============================== Bắt đầu thi ====================================");
                Result result = new Result();
                result.inputData(examList.get(choice - 1).getExamId());
                takeAnExamDetail(choice, result.getResultId(), result);
                result.setTotalPoint(
                        result.getTotalPoint() * 100 / examList.get(choice - 1).getListQuestion().size()
                );
                resultList.add(result);
                IO_file.writeObjFromFile(resultList, IO_file.RESUL_PATH);
                System.out.println("kết thúc bài thi, bạn có thể xem lại điểm của mình trong phần quản lý");
            } else {
                System.out.println("đề bạn chọn hiện đang được chỉnh sửa, mời bạn quay lại sau");
            }
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
            IO_file.writeObjFromFile(resultDetailList, IO_file.RESUL_DETAIL_PATH);
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
        if (examList.isEmpty()) {
            System.out.println("hiện tại chưa có đề thi nào");
        }
        {
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
            boolean check = true;
            String catalogId = catalog.getCatalogId();
            for (Exam exam : examList) {
                for (String s : exam.getCatalogId()) {
                    if (s.contains(catalogId)) {
                        UserService.displayExamListOfUser(exam);
                        check = false;
                    }
                }
            }
            if (!check) {
                System.out.println("khong tim thay");
            }
        }

    }

    private static void displayExamListOfUser(Exam exam) {
        System.out.printf("mã đề: %s  | Đề thi: %s | trạng thái: %s", exam.getExamId(), exam.getTitle(), exam.isStatus() ? "open" : "blook");
        System.out.println("\n=============================================================================");
    }


    public static void updatePassword() {
        int count=0;
        while (true) {
            int updatePasswordID = currentUserList.get(0).getUserId();
            System.out.println("moi ban nhap vao mat khau cu");
            String oldPassword = InputMethods.getString();
            boolean checkPass = BCrypt.checkpw(oldPassword, getUserFromUsername(currentUserList.get(0).getUserName()).getPassword());
            if (!checkPass) {
                System.out.println("mat khau cu khong chinh xac, moi nhap lai");
               count= count+1;
                if(count==3){
                    System.err.println("tai khoan cua ban da bi khoa do nhap sai mat khau qua 3 lan");
                    userList.stream().filter(user -> user.getUserName().equals(currentUserList.get(0).getUserName())).findFirst().orElse(null)
                            .setStatus(false);
                    currentUserList.clear();
                    IO_file.writeObjFromFile(currentUserList,IO_file.CURRENT_USER_PATH);
                    IO_file.writeObjFromFile(userList,IO_file.USER_PATH);

                    System.exit(0);
                }
                continue;
            }
            System.out.println("mời bạn nhập vào mật khẩu mới");
            finByID(updatePasswordID).setPassword(finByID(updatePasswordID).getInputPassword());
            IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
            System.out.println("đổi mật khẩu thành công");
            break;
        }
    }

    private static Users getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
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
                    finByID(updateID).setFullName(finByID(updateID).getInputFullName());
                    break;
                case 2:
                    System.out.println("mời bạn nhập tên mới");
                    finByID(updateID).setLastName(InputMethods.getString());
                    finByID(updateID).setFullName(finByID(updateID).getInputFullName());
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
                    IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
                    isExit = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public static void studentOfResultList() {
        if (resultList.stream().anyMatch(result -> result.getUserId() == currentUserList.get(0).getUserId())) {
            List<Result> resultsCurrentUser = resultList.stream().filter(results -> results.getUserId() == currentUserList.get(0).getUserId()).toList();

            int count = 1;
            for (Result result : resultsCurrentUser) {
                System.out.printf("┌──────────────────────────────────────────────────────┐\n" +
                        "│                  Bài thi số:  %-10s             │\n", count);
                result.displayData();
                count++;
            }
            System.out.println("bạn có thể nhập số thứ tự bài thi để xem chi tiết hoặc chon 0 đêt thoát");
            while (true) {
                System.out.println("mời bạn chọn bài thi muốn xem tri tiết");
                System.out.print("Bài số: ");
                int choice = InputMethods.getInteger();
                if (choice >= count) {
                    System.out.println("lựa chọn không chính xác");

                } else if (choice < 1) {
                    break;
                } else {
                    resultsCurrentUser.get(choice - 1).displayDataAll();
                }
            }
        } else {
            System.out.println("lịch sử thi rỗng");
        }
    }

    private static Users finByID(int Id) {
        return userList.stream().filter(users -> users.getUserId() == Id).findFirst().orElse(null);
    }

    public static void getPasswordByEmailEndPhone() {
        System.out.println("mời bạn nhập vào emai và số điện thoại của mình");
        System.out.print("Email: ");
        String email = InputMethods.getString();
        System.out.println();
        System.out.print("Số điện thoại: ");
        String phoneNum = InputMethods.getString();
        Users acc = userList.stream().filter(users -> users.getEmail().equals(email))
                .filter(users -> users.getPhoneNumber().equals(phoneNum))
                .findFirst().orElse(null);

        if (acc == null) {
            System.out.println("thông tin Email hoặc sdt không đúng");
        } else {
            System.out.println("Xin chào " + acc.getFullName().toUpperCase());
            System.out.println("Đây là tài khoản của bạn: " + acc.getUserName());
            System.out.println("Mời bạn nhập mật khẩu mới");
            acc.setPassword(BCrypt.hashpw(acc.getInputPassword(), BCrypt.gensalt(5)));
            IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
            System.out.println("thay đổi mật khẩu thành công");
        }
    }




}