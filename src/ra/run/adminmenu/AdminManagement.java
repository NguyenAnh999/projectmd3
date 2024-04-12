package ra.run.adminmenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.run.adminmenu.UserManage;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class AdminManagement {

    public static void adminMenu() {
        boolean isExit = true;
        do {
            String currentUserName=currentUserList.get(0).getFullName();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ");
             System.out.printf("┃                       Chào Mừng Admin %-40s ┃\n",currentUserName);
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                           1: Quan ly người dùng                                ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                            2: báo cáo thống kê                                 ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              3: đăng xuất                                      ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                               0: quay lại                                      ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("Mời bạn chọn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    UserManage.userControlMenu();
                    break;
                case 2:
                    ReportManagement.ReportManageMenu();
                    break;
                case 3:
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
