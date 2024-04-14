package ra.run.adminmenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.ResultService;
import ra.business.designimplement.UserService;
import ra.run.usermenu.UserInformation;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class ReportManagement {
    public static void ReportManageMenu() {
        boolean isExit = true;
        do {
            String currentUserName=currentUserList.get(0).getFullName();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ +");
            System.out.printf ("┃          Chào Mừng Admin %-53s ┃\n",currentUserName);
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃          1: thống kê danh sách người dự thi                                    ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃          2: thống kê danh sách bài thi                                         ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃          3: thống kê điểm trung bình theo tháng                                ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃          4: top 2 đề được thi nhiều nhất theo tháng                            ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃          5: top 2 học sinh có điểm thi cao nhất theo tháng                     ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃          0: quay lại                                                           ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("Mời bạn chọn");

            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    ResultService.ReportUserQuantity();
                    break;
                case 2:
                    ResultService.ReportExamList();
                    ResultService.userPointList(true);
                    break;
                case 3:
                    ResultService.averagePointPerMonth();
                    break;
                case 4:
                    ResultService.top2Exam();
                    break;
                case 5:
                    ResultService.top2User();
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
