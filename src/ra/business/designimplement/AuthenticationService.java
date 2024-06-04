package ra.business.designimplement;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.config.Enum;
import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.design.IAuthentication;
import ra.business.entity.Users;

import java.util.List;

import static ra.run.adminmenu.AdminManagement.adminMenu;
import static ra.run.teachmenu.TeachManagement.teachMenu;
import static ra.run.usermenu.UserManagement.userMenu;

public class AuthenticationService implements IAuthentication {
    public static List<Users> userList;
    public static List<Users> currentUserList;

    static {
        userList = IO_file.readObjFromFile(IO_file.USER_PATH);
        currentUserList = IO_file.readObjFromFile(IO_file.CURRENT_USER_PATH);
    }
//    static {
//        userList.add( new Users(1,"nguyen","anh","nguyen anh","lollol007","lollol007","dfhghjf@gmail.com","0942342344","hânm",Enum.ROLE_USER,true));
//        userList.add( new Users(2,"nguyen","anh","nguyen anh","lollol001","lollol001","dfhghjf@gmail.com","0942342344","hânm",Enum.ROLE_USER,false));
//        userList.add( new Users(3,"nguyen","anh","nguyen anh","lollol002","lollol002","dfhghjf@gmail.com","0942342344","hânm",Enum.ROLE_USER,true));
//        userList.add( new Users(4,"nguyen","anh","nguyen anh","lollol003","lollol003","dfhghjf@gmail.com","0942342344","hânm",Enum.ROLE_USER,true));
//        userList.add( new Users(5,"nguyen","anh","nguyen anh","lollol004","lollol004","dfhghjf@gmail.com","0942342344","hânm",Enum.ROLE_USER,true));
//        userList.add( new Users(6,"nguyen","anh","nguyen anh","lollol005","lollol005","dfhghjf@gmail.com","0942342344","hânm",Enum.ROLE_ADMIN,true));
//
//
//
//
//    }

    public static AuthenticationService authenticationService = new AuthenticationService();
    @Override
    public void login() {
        while (true) {
            System.out.print("Mời bạn nhập vào tên tài khoản:");
            String userName = InputMethods.getString();
            System.out.print("Mời bạn nhập vào mật khẩu:");
            String password = InputMethods.getString();
            if (getUserFromUsername(userName) == null) {
                System.out.println("tài khoản không chính xác mời đăng nhập lại");
            } else {
                boolean checkLogin = BCrypt.checkpw(password, getUserFromUsername(userName).getPassword());
                if (checkLogin) {
                    if(!getUserFromUsername(userName).isStatus()){
                        System.out.println("tài khoản đã bị khóa");
                        break;
                    }
                    currentUserList.add(getUserFromUsername(userName));
                    IO_file.writeObjFromFile(currentUserList, IO_file.CURRENT_USER_PATH);
                    if (getUserFromUsername(userName).getRole().equals(Enum.ROLE_USER)) {
                        userMenu();
                        break;
                    } else if (getUserFromUsername(userName).getRole().equals(Enum.ROLE_TEACH)) {
                        teachMenu();
                        break;
                    } else {
                        adminMenu();
                        break;
                    }
                } else {
                    System.out.println("Mật khẩu không chính xác mời đăng nhập lại");
                }
            }
        }
    }
    @Override
    public void register() {
        Users user = new Users();
        user.inputData();
        userList.add(user);
        System.out.println("đăng ký thành công");
        IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
    }

    private Users getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }
}