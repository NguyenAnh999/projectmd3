package ra.business.config;

import ra.business.designimplement.AuthenticationService;
import ra.business.entity.Catalogs;
import ra.business.entity.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ra.business.designimplement.CatalogService.catalogsList;

public class RanDum {
    private static final String[] firstNames = {"Nguyen", "Tran", "Le", "Pham", "Hoang", "Vo", "Dang", "Do", "Bui", "Vu"};
    private static final String[] lastNames = {"Van", "Thi", "Thanh", "Duc", "Minh", "Hoa", "Tuan", "Hieu", "Phuong", "An"};
    private static final String[] emails = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "icloud.com"};
    private static final String[] phonePrefixes = {"032", "033", "034", "035", "036", "037", "038", "039", "096", "097", "098", "086", "083", "084", "085", "081", "082", "088", "091", "094", "070", "079", "077", "076", "078", "090", "093", "089", "056", "058", "092", "059", "099"};

    public static List<Users> generateRandomUsers(int count) {
        List<Users> usersList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String userName = firstName.toLowerCase() + "_" + lastName.toLowerCase() + (i + 1);
            String password = "Password" + (i + 1);
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + (i + 1) + "@" + emails[random.nextInt(emails.length)];
            String phoneNumber = phonePrefixes[random.nextInt(phonePrefixes.length)] + String.format("%07d", random.nextInt(9999999));
            String address = "Address" + (i + 1);
            Enum role = Enum.values()[random.nextInt(Enum.values().length)]; // Chọn ngẫu nhiên một trong các vai trò
            boolean status = random.nextBoolean(); // Chọn ngẫu nhiên trạng thái hoạt động hoặc không hoạt động
            Users user = new Users(i + 1, firstName, lastName, firstName + " " + lastName, userName, password, email, phoneNumber, address, role, status);
            usersList.add(user);
        }

        return usersList;
    }

//    public static void main(String[] args) {
//
//        AuthenticationService.userList = generateRandomUsers(50);
//        for (Users user : AuthenticationService.userList) {
//            System.out.println(user);
//        }
//        IO_file.writeObjFromFile(AuthenticationService.userList,IO_file.USER_PATH);
//    }


    private static final String[] catalogNames = {"Toán", "Văn", "Lịch sử", "Địa lý", "Sinh học", "Hóa học", "Vật lý", "Ngoại ngữ", "Công nghệ", "Thể dục"};

    public static List<Catalogs> generateRandomCatalogs(int count) {
        List<Catalogs> catalogsList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String catalogId = generateRandomCatalogId();
            String catalogName = catalogNames[random.nextInt(catalogNames.length)];
            String description = "Mô tả danh mục đề thi số " + (i + 1);

            Catalogs catalogs = new Catalogs(catalogId, catalogName, description);
            catalogsList.add(catalogs);
        }

        return catalogsList;
    }

    private static String generateRandomCatalogId() {
        StringBuilder catalogId = new StringBuilder("C");
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            catalogId.append((char) (random.nextInt(26) + 'A'));
        }
        return catalogId.toString();
    }
//
//    public static void main(String[] args) {
//        catalogsList = generateRandomCatalogs(30);
//        for (Catalogs catalogs : catalogsList) {
//            System.out.println(catalogs.toString());
//        }
//        IO_file.writeObjFromFile(catalogsList,IO_file.CATALOGS_PATH);
//
//    }











}
