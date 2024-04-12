package ra.run.teachmenu;

import ra.business.config.InputMethods;
import ra.business.designimplement.AdminService;
import ra.business.designimplement.ExamService;
import ra.business.designimplement.ResultService;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class ExamManagement {


    public static void examControlMenu() {
        boolean isExit = true;
        do {
            String currentUserName=currentUserList.get(0).getFullName();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ +");
             System.out.printf("┃         Chào Mừng giáo viên %-50s ┃\n",currentUserName);
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              1:Thêm đề thi                                     ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                           2:  khóa/mở đề thi                                   ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              3: sửa đề thi                                     ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              4: xóa đề thi                                     ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                      5: Thông kê kết quả người dự thi                          ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                          6: xem tri tiết đề thi                                ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              0: quay lại                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("Mời bạn chọn");


            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    ExamService.addNewExam();
                    break;
                case 2:
                    ExamService.updateStatusExam();
                    break;
                case 3:
                    ExamService.updateExam();
                    break;
                case 4:
                    ExamService.deleteExam();
                    break;
                case 5:
                    ResultService.userPointList();
                    break;
                case 6:
                    ExamService.displayExamDetail();
                    break;
                case 0:
                    isExit = false;
                    break;
                default:
                    System.out.println("lựa chọn sai");
                    break;
            }
        } while (isExit);
    }
}
