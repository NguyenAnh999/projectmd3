package ra.run.teachmenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.run.teachmenu.ExamManagement;
import ra.run.usermenu.UserInformation;

import java.util.concurrent.ScheduledExecutorService;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class TeachManagement {
    public static void teachMenu() {
        boolean isExit = true;
        do {
            System.out.println("----------------Chào Mừng Giáo viên " + currentUserList.get(0).getFullName() + "--------------------");
            System.out.println("1: Quản lý đề thi");
            System.out.println("2: Quản lý báo cáo");
            System.out.println("3: Thông tin cá nhân");
            System.out.println("0: đăng xuất");
            System.out.println("Mời bạn chọn (1|2|3)");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    ExamManagement.examControlMenu();
                    break;
                case 2:

                    break;
                case 3:
                    UserInformation.UserInformationMenu();
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
