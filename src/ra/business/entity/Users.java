package ra.business.entity;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.config.Enum;
import ra.business.config.InputMethods;
import ra.business.designimplement.AdminService;

import java.io.Serializable;
import java.util.Comparator;

import static ra.business.designimplement.AuthenticationService.userList;

public class Users implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private Enum role;
    private boolean status;

    public Users() {
    }

    public Users(int userId, String firstName, String lastName, String fullName, String userName, String password, String email, String phoneNumber, String address, Enum role, boolean status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Enum getRole() {
        return role;
    }

    public void setRole(Enum role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean getInputStatus() {
        return true;
    }


    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
public void displayData(){
        String roleStr = this.role==Enum.ROLE_USER?"thí sinh":(this.role==Enum.ROLE_TEACH?"giáo viên":"Admin");
    System.out.printf("| ID: %-3d | Tên: %-6s | Quyền: %-10s | Trạng thái: %-5s |",this.userId,this.fullName,roleStr,(this.status?"open":"blook"));
    System.out.println("\n=============================================================================");
}

    public void inputData() {
        this.userId = getNewId();
        this.firstName = getInputFistName();
        this.lastName = getInputLastName();
        this.fullName = getInputFullName();
        this.userName = getInputUserName();
        this.password = getInputPassword();
        confirmPassword(password);
        this.setPassword(BCrypt.hashpw(this.password, BCrypt.gensalt(5)));
        this.email = getInputEmail();
        this.phoneNumber = getInputPhoneNumber();
        this.address = getInputAddress();
        this.role = getInputRole();
        this.status = getInputStatus();
    }

    private Enum getInputRole() {
        System.out.println("mời bạn chọn quyền:");
        while (true) {
            System.out.println("1: học viên");
            System.out.println("2: người ra đề");
            System.out.println("3: Admin");
            System.out.print("Mời bạn chọn :");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    return Enum.ROLE_USER;
                case 2:
                    return Enum.ROLE_TEACH;
                case 3:
                  return isAdmin();
                default:
                    System.out.println("lựa chọn không chính xác,mời chọn lại");
            }
        }
    }
private Enum isAdmin(){
    System.out.println("mời bạn nhập vào mã code để tạo tài khoản Admin");
    int adminCode=InputMethods.getInteger();
    if (adminCode==AdminService.ADMIN_CODE) {
        return Enum.ROLE_ADMIN;
    }else {
        System.err.println("MÃ không đúng YÊU CẦU KHÔNG TRUY CẬP TRÁI PHÉP !!");
        System.exit(0);
        return null;
    }
}
    public String getInputAddress() {
        System.out.println("mời bạn nhập vào địa chỉ:");
        return InputMethods.getString();
    }

    public String getInputPhoneNumber() {
        String regex = "^(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7}$";
        System.out.println("Mời bạn nhập vào số điện thoại:");
        while (true) {
            String InputPhoneNumber = InputMethods.getString();
            if (!InputPhoneNumber.matches(regex)) {
                System.out.println("số điện thoại không đúng định dạng");
            } else {
                if (userList.stream().noneMatch(users -> users.getPhoneNumber().equals(InputPhoneNumber))) {
                    return InputPhoneNumber;
                } else {
                    System.out.println("số điện thoại của bạn đã bị trùng, mời bạn nhập số khác:");
                }
            }
        }
    }

    public String getInputEmail() {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        System.out.println("Mời bạn nhập vào email:");
        while (true) {
            String userInputEmail = InputMethods.getString();
            if (!userInputEmail.matches(regex)) {
                System.out.println("email không đúng định dạng");
            } else {
                if (userList.stream().noneMatch(users -> users.getEmail().equals(userInputEmail))) {
                    return userInputEmail;
                } else {
                    System.out.println("email của bạn đã bị trùng, mời bạn nhập email khác:");
                }
            }
        }
    }

    public String getInputPassword() {
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$";
        System.out.println("mời bạn nhập mật khẩu");
        while (true) {
            String passwordInput = InputMethods.getString();
            if (!passwordInput.matches(regex)) {
                System.out.println("Mật khẩu phải có cả số và chữ và lớn hơn 8 kí tự");
            } else {
                return passwordInput;
            }
        }

    }

    public void confirmPassword(String passwordInput) {
        System.out.println("mời bạn nhập lại mật khẩu");
        while (true) {
            String confirmPassword = InputMethods.getString();
            if (confirmPassword.equals(passwordInput)) {
                return;
            } else {
                System.out.println("Mật khẩu không trùng khớp mời nhập lại");
            }
        }
    }

    public String getInputUserName() {
        String regex = "^[a-zA-Z0-9]{6,}$";
        System.out.println("Mời bạn nhập vào tên đăng nhập:");
        while (true) {
            String userInputName = InputMethods.getString();
            if (!userInputName.matches(regex)) {
                System.out.println("tên của bạn không thể ít hơn 6 kí tự và không có kí tự đặc biệt");
            } else {
                if (userList.stream().anyMatch(users -> users.getUserName().equals(userInputName))) {
                    System.out.println("Tên đăng nhập của bạn bị trùng, mời bạn nhập lại:");
                } else {
                    return userInputName;
                }
            }
        }
    }

    public String getInputLastName() {
        System.out.println("mời bạn nhập họ:");
        return InputMethods.getString();
    }

    public String getInputFistName() {
        System.out.println("mời bạn nhập tên:");
        return InputMethods.getString();
    }

    private String getInputFullName() {
        return this.firstName + " " + this.lastName;
    }

    public int getNewId() {
        int max = userList.stream().map(Users::getUserId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }

}
