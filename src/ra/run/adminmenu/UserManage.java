package ra.run.adminmenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.AdminService;
import ra.business.entity.Users;

import static ra.business.designimplement.AuthenticationService.currentUserList;
import static ra.business.designimplement.AuthenticationService.userList;

public class UserManage {
    public static void userControlMenu() {
        boolean isExit = true;
        do {
            System.out.println("----------------" + currentUserList.get(0).getFullName() + "--------------------");
            System.out.println("1: Danh sách người dùng");
            System.out.println("2: khóa/mở người dùng");
            System.out.println("3: Tìm kiếm người dùng");
            System.out.println("0: quay lại");
            System.out.println("Mời bạn chọn (1|2|3)");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    AdminService.displayData();
                    break;
                case 2:
                    AdminService.setStatusForUser();
                    break;
                case 3:
                    AdminService.searchByName();
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