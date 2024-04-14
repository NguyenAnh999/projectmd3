package ra.business.designimplement;

import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.entity.Users;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import java.sql.SQLOutput;
import java.util.spi.AbstractResourceBundleProvider;

import static ra.business.config.Enum.ROLE_ADMIN;
import static ra.business.config.Enum.ROLE_USER;
import static ra.business.designimplement.AuthenticationService.userList;

public class AdminService {
    public static final int ADMIN_CODE = 123456789;

    public static void setStatusForUser() {
        displayData();
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
                if (userList.get(updateStatusID - 1).isStatus()) {
                    userList.get(updateStatusID - 1).setStatus(false);
                    IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
                    System.out.println("bạn đã khóa tài khoản của " + userList.get(updateStatusID - 1).getFullName() + " thành công ");
                    break;
                } else {
                    userList.get(updateStatusID - 1).setStatus(true);
                    System.out.println("bạn đã mở khóa tài khoản của " + userList.get(updateStatusID - 1).getFullName() + " thành công ");
                    IO_file.writeObjFromFile(userList, IO_file.USER_PATH);
                    break;
                }
            }
        }
    }

    public static void paginationMath() {
        byte choice = 1;
        int length = userList.size();
        int pageQuantity = (length % 4) == 0 ? length / 4 : (length / 4) + 1;
        int stat;
        userList.stream().limit(4).toList().forEach(Users::displayData);

        System.out.println("                          ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("                          ┃ trang  %-3s/ %-18s ┃\n", choice, pageQuantity);
        System.out.println("                          ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");


        while (true) {
            System.out.println("Mời bạn nhập vào số  trang bạn muốn");
            System.out.println("nhập 0 để thoát");
            choice = InputMethods.getByte();
            stat = (choice - 1) * 4;
            if (choice <= 0) {
                return;
            }
            if (choice <= pageQuantity) {
                userList.stream().skip(stat).limit(4).toList().forEach(Users::displayData);
                System.out.println("                          ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.printf("                          ┃ trang  %-3s/ %-18s ┃\n", choice, pageQuantity);
                System.out.println("                          ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            } else {
                System.err.println("nhập sai mời nhập lại");
            }
        }
    }

    public static void displayData() {
        userList.forEach(Users::displayData);
    }

    public static void searchByName() {
        System.out.println("Mời bạn nhập vào tên người dùng muốn tìm kiếm");
        String userName = InputMethods.getString();
        if (userList.stream().anyMatch(users -> users.getFullName().equals(userName))) {
            userList.stream().filter(users -> users.getFullName().contains(userName)).forEach(Users::displayData);
        } else {
            System.out.println("không tìm thấy ai có tên giồn với " + userName);
        }
    }
}
