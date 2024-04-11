package ra.run.usermenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.UserService;

import static ra.business.designimplement.AuthenticationService.currentUserList;

public class UserInformation {

    public static void UserInformationMenu() {
        boolean isExit =true;
        do{
            System.out.println("----------------Thông tin cá nhân--------------------");
            System.out.println("1: hiển thị thông tin cá nhân");
            System.out.println("2: chỉnh sửa thông tin cá nhân");
            System.out.println("3: đổi mật khẩu");
            System.out.println("0: quay lại");
            System.out.println("Mời bạn chọn (1|2|3)");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    UserService.takeAnExam();
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
