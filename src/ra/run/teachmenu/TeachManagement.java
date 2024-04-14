package ra.run.teachmenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.ResultService;
import ra.business.designimplement.UserService;
import ra.run.adminmenu.ReportManagement;
import ra.run.teachmenu.ExamManagement;
import ra.run.usermenu.UserInformation;

import java.util.concurrent.ScheduledExecutorService;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class TeachManagement {
    public static void teachMenu() {
        boolean isExit = true;
        do {
            String currentUserName = currentUserList.get(0).getFullName();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ +");
            System.out.printf("┃         Chào Mừng giáo viên %-50s ┃\n", currentUserName);
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                            1: Quản lý đề thi                                   ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                            2: Quản lý báo cáo                                  ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                            3: Thông tin cá nhân                                ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                            4: Tìm đề theo danh mục                             ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                            0: Đăng xuất                                        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("Mời bạn chọn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    ExamManagement.examControlMenu();
                    break;
                case 2:
                    ResultService.userPointList(false);
                    break;
                case 3:
                    UserInformation.UserInformationMenu();
                    break;
                case 4:
                    UserService.finExamByCatalog();
                    break;
                case 0:
                    currentUserList.clear();
                    IO_file.writeObjFromFile(currentUserList, IO_file.CURRENT_USER_PATH);
                    isExit = false;
                    break;
                default:
                    System.out.println("lựa chọn sai");
                    break;
            }
        } while (isExit);
    }
}
