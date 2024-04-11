package ra.run.usermenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.UserService;

import static ra.business.designimplement.AuthenticationService.authenticationService;
import static ra.business.designimplement.AuthenticationService.currentUserList;

public class UserManagement {
    public static void userMenu() {
        boolean isExit =true;
        do{
            System.out.println("----------------Chào Mừng học viên "+currentUserList.get(0).getFullName()+"--------------------");
            System.out.println("1: Chọn đề thi");
            System.out.println("2: Thông tin cá nhân");
            System.out.println("3: tìm đề theo tên");
            System.out.println("4: tìm đề theo danh mục");
            System.out.println("0: đăng xuất");
            System.out.println("Mời bạn chọn (1|2|3)");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    UserService.takeAnExam();
                    break;
                case 2:
                    UserInformation.UserInformationMenu();
                    break;
                case 3:
                    UserService.finExamByName();
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
        }while (isExit);
    }
}
