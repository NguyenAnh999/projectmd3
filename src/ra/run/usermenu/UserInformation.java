package ra.run.usermenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.UserService;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class UserInformation {

    public static void UserInformationMenu() {
        boolean isExit =true;
        do{
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                            ❤    THÔNG TIN CÁ NHÂN    ❤                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                         1: Hiển thị thông tin cá nhân                          ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                         2: Chỉnh sửa thông tin cá nhân                         ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                         3: Đổi mật khẩu                                        ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                         0: Quay lại                                            ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("Mời bạn chọn");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    currentUserList.get(0).displayDataAll();
                    break;
                case 2:
                    UserService.updateUserInformation();
                    break;
                case 3:
                    UserService.updatePassword();
                    break;
                case 0:
                    isExit = false;
                    break;
                default:
                    System.out.println("lựa chọn sai");
                    break;
            }
        }while (isExit);
    }


}
