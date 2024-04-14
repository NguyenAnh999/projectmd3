package ra.run.usermenu;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.designimplement.UserService;

import static ra.business.designimplement.AuthenticationService.authenticationService;
import static ra.business.designimplement.AuthenticationService.currentUserList;

public class UserManagement {
    public static void userMenu() {
        boolean isExit = true;
        do {
            String currentUserFullName = currentUserList.get(0).getFullName();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ +");
            System.out.printf("┃          Chào Mừng học viên %-50s ┃\n", currentUserFullName);
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              1: Chọn đề thi                                    ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              2: Thông tin cá nhân                              ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              3: Tìm đề theo tên                                ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              4: Tìm đề theo danh mục                           ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              5: xem lịch sử thi                                ┃");
            System.out.println("┃--------------------------------------------------------------------------------┃");
            System.out.println("┃                              0: Đăng xuất                                      ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛+");
            System.out.println("Mời bạn chọn");

            byte choice = InputMethods.getByte();
            switch (choice) {
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
                case 5:
                    UserService.studentOfResultList();
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
