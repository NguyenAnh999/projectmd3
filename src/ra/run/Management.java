package ra.run;

import ra.business.config.Enum;
import ra.business.config.InputMethods;
import ra.business.designimplement.AdminService;
import ra.business.designimplement.ResultService;
import ra.business.designimplement.UserService;
import ra.business.entity.Result;
import ra.business.entity.ResultDetail;

import static ra.business.designimplement.AuthenticationService.*;
import static ra.business.designimplement.ExamService.examList;
import static ra.business.designimplement.ResultService.resultList;
import static ra.run.adminmenu.AdminManagement.adminMenu;
import static ra.run.teachmenu.TeachManagement.teachMenu;
import static ra.run.usermenu.UserManagement.userMenu;

public class Management {

    public static void main(String[] args) {
        if (!currentUserList.isEmpty()) {
            if (!currentUserList.get(0).isStatus()){
                System.out.println("tai khaon cua ban da bi khoa lien he voi chung toi(0987654321)");
                System.exit(0);
            }
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
            System.out.println("┃ 1: Đăng ký  ◀    ┃ 2: Đăng nhập     ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃ 3: Quên mật khẩu ┃ 0: Thoát         ┃");
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
                case 3:
                    UserService.getPasswordByEmailEndPhone();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("lựa chọn sai");
            }
        } while (true);
    }
}
