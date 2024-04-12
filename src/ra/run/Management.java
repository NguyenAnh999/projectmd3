package ra.run;

import ra.business.config.Enum;
import ra.business.config.InputMethods;

import static ra.business.designimplement.AuthenticationService.*;
import static ra.run.adminmenu.AdminManagement.adminMenu;
import static ra.run.teachmenu.TeachManagement.teachMenu;
import static ra.run.usermenu.UserManagement.userMenu;

public class Management {

    public static void main(String[] args) {
        if (!currentUserList.isEmpty()) {
            if (currentUserList.get(0).getRole().equals(Enum.ROLE_USER)) {
                userMenu();
            } else if (currentUserList.get(0).getRole().equals(Enum.ROLE_TEACH)) {
                teachMenu();
            } else {
                adminMenu();
            }
        }
        do {
            System.out.println("┏━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃ 1: đăng ký       ┃ 2: đăng nhập     ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃ 3: quên mật khẩu ┃ 0: thoát         ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━┛");
            System.out.println("Mời bạn chọn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    authenticationService.register();
                    break;
                case 2:
                    authenticationService.login();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("lựa chọn sai");
            }
        } while (true);
    }
}
