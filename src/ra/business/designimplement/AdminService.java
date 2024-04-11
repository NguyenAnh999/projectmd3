package ra.business.designimplement;

import ra.business.config.InputMethods;
import ra.business.entity.Users;

import javax.swing.plaf.IconUIResource;

import java.util.spi.AbstractResourceBundleProvider;

import static ra.business.config.Enum.ROLE_ADMIN;
import static ra.business.config.Enum.ROLE_USER;
import static ra.business.designimplement.AuthenticationService.userList;

public class AdminService {
    public static void setStatusForUser() {
        int cont = 1;
        for (Users users : userList) {
            if (users.getRole().equals(ROLE_USER)) {
                System.out.println("NGƯỜI DÙNG THỨ: " + cont);
                users.displayData();
                cont++;
            }
        }
        System.out.println("mời bạn nhập vào số thứ tự muốn sửa trạng thái");
        while (true) {
            byte updateStatusID = InputMethods.getByte();
            if (updateStatusID > userList.size()) {
                System.out.println("lựa chọn không chính xác");
            } else {
               if (userList.get(updateStatusID-1).isStatus()){
                   userList.get(updateStatusID-1).setStatus(false);
                   System.out.println("bạn đã khóa tài khoản của " +userList.get(updateStatusID-1).getFullName()+" thành công ");
                   break;
               }else {
                       userList.get(updateStatusID-1).setStatus(true);
                       System.out.println("bạn đã mở khóa tài khoản của " +userList.get(updateStatusID-1).getFullName()+" thành công ");
                       break;
               }
            }
        }
    }

    public static void displayData() {
        userList.forEach(Users::displayData);
    }

public static void searchByName(){
    System.out.println("Mời bạn nhập vào tên người dùng muốn tìm kiếm");
    String userName=InputMethods.getString();
    userList.stream().filter(users -> users.getFullName().contains(userName)).forEach(Users::displayData);
}
}
