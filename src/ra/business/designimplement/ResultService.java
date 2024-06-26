package ra.business.designimplement;

import ra.business.config.Enum;
import ra.business.config.IO_file;
import ra.business.config.InputMethods;
import ra.business.entity.Exam;
import ra.business.entity.Result;
import ra.business.entity.ResultDetail;
import ra.business.entity.Users;

import java.awt.image.ImageProducer;
import java.util.*;
import java.util.stream.Collectors;

import static ra.business.designimplement.AuthenticationService.currentUserList;
import static ra.business.designimplement.AuthenticationService.userList;
import static ra.business.designimplement.ExamService.examList;

public class ResultService {
    public static List<Result> resultList;
    public static List<ResultDetail> resultDetailList;

    static {
        resultList = IO_file.readObjFromFile(IO_file.RESUL_PATH);
        resultDetailList = IO_file.readObjFromFile(IO_file.RESUL_DETAIL_PATH);
    }

    public static void ReportExamList() {
        System.out.printf("hiện tại đang có tổng cộng %d bài thi dược tạo trên wep\n", examList.size());
    }

    public static void ReportUserQuantity() {
        if (isNotResult()) {
            for (Users users : userList) {
                if (users.getRole() == Enum.ROLE_USER) {
                    long count = resultList.stream().filter(result -> result.getUserId() == users.getUserId()).count();

                    if (count > 0) {
                        System.out.printf("thí sinh %s đã thi %d lần\n", users.getFullName(), count);
                        System.out.println("=======================================================");

                    } else {
                        System.out.printf("thí sinh %s chưa có lần dự thi nào\n", users.getFullName());
                        System.out.println("=======================================================");
                    }
                }
            }
        } else {
            System.out.println("chưa có dữ liệu nào để thống kê");
        }
    }

    public static void averagePointPerMonth() {
        System.out.println("mời bạn nhập vào tháng muốn thông kê điểm");
        byte month = InputMethods.getByte();
        double totalPointForMont = 0;
        if (month > 12 || month < 1) {
            System.out.println("tháng bạn nhâp không hợp lệ");
        } else {
            OptionalDouble check = resultList.stream().filter(result -> result.getCreatedDate().getMonthValue() == month).mapToInt(Result::getTotalPoint).average();
            if (check.isPresent()) {
                totalPointForMont = check.getAsDouble();
            } else {
                totalPointForMont = 0;
            }
        }
        if (totalPointForMont == 0) {
            System.out.println("tháng bạn muốn xem không có người thi");
        } else {
            System.out.printf("điêm thi trung bình học sinh tháng %d là %s diểm\n", month, totalPointForMont);
        }
    }

    public static void top2User() {
        // Nhập vào tháng để xem top 2 học sinh có điểm trung bình cao nhất
        System.out.println("mời bạn nhập vào tháng xem top 2 học sinh có điểm trung bình cao nhất");
        byte month = InputMethods.getByte();
        if (month > 12 || month < 1) {
            System.out.println("tháng bạn nhâp không hợp lệ");
        } else {
            // Lọc danh sách các kỳ thi trong tháng được chọn
            List<Result> examsCurrent = resultList.stream().filter(exam -> exam.getCreatedDate().getMonthValue() == month).toList();
            if (examsCurrent.isEmpty()) {
                System.out.println("tháng này không có ai tham gia thi");
            } else {
                double totalPoint;
                Map<Users, Double> map = new HashMap<>();
                // Tính điểm trung bình cho từng thí sinh
                for (Users user : userList) {
                    OptionalDouble check = examsCurrent.stream().filter(exam -> exam.getUserId() == user.getUserId()).mapToInt(Result::getTotalPoint).average();
                    if (check.isPresent()) {
                        totalPoint = check.getAsDouble();
                    } else {
                        totalPoint = 0;
                    }
                    map.put(user, totalPoint);
                }

                // Lấy ra top 2 học sinh có điểm cao nhất
                Map<Users, Double> topTwo = map.entrySet()
                        .stream().sorted(Map.Entry.<Users, Double>comparingByValue().reversed())
                        .limit(2)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));

                // In ra top 2 học sinh có điểm cao nhất

                System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ");
                System.out.printf ("┃Top 2 học sinh có điểm cao nhất tháng %-26s┃\n", + month);
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.printf ("┃ %-30s ┃ %-14s \n", "Thí sinh", "Điểm trung bình               ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                for (Map.Entry<Users, Double> userInTop2 : topTwo.entrySet()) {
                    String fullName = userInTop2.getKey().getFullName();
                    String averageScore = String.format("%.2f", userInTop2.getValue());
                    System.out.printf ("┃ %-30s ┃ %-30s┃\n", fullName, averageScore);
                    System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

                }
            }
        }

    }

    public static void top2Exam() {
        System.out.println("mời bạn nhập vào tháng xem top 2 đề được thi nhiều nhất");
        byte month = InputMethods.getByte();
        if (month > 12 || month < 1) {
            System.out.println("tháng bạn nhâp không hợp lệ");
        } else {
            List<Result> examsCurrent = resultList.stream().filter(exam -> exam.getCreatedDate().getMonthValue() == month).toList();
            if (examsCurrent.isEmpty()) {
                System.out.println("tháng này không có ai tham gia thi");
            } else {
                Map<Exam, Integer> map = new HashMap<>();
                for (Exam exam : examList) {
                    int count = (int) examsCurrent.stream().filter(result -> result.getExamId() == exam.getExamId()).count();
                    map.put(exam, count);
                }
//        map.entrySet().stream() tạo một stream từ các entry của map.
//        sorted(Map.Entry.comparingByValue().reversed()) sắp xếp các entry theo giá trị giảm dần.
//                limit(2) giới hạn kết quả đầu ra chỉ gồm hai entry đầu tiên sau khi sắp xếp.
//        collect(Collectors.toMap(...)) thu thập hai entry này vào một HashMap mới.
                Map<Exam, Integer> topTwo = map.entrySet().stream()
                        .sorted(Map.Entry.<Exam, Integer>comparingByValue().reversed())
                        .limit(2)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
                System.out.println("top 2 de thi được lựa chon nhiều nhất tháng " + month);
                for (Map.Entry<Exam, Integer> examIntegerEntry : topTwo.entrySet()) {
                    System.out.printf("Tiêu đề: %s  | số lân được chọn: %s\n", examIntegerEntry.getKey().getTitle(), examIntegerEntry.getValue());
                    System.out.println("=====================================================================");
                }
            }
        }
    }


    public static void userPointList(boolean isAdmin) {
        List<Exam> currentExam;

        if (isAdmin) {
            currentExam = examList;
        } else {
            currentExam = examList.stream().filter(exam -> exam.getUserId() == currentUserList.get(0).getUserId()).toList();
        }
        for (int i = 0; i < currentExam.size(); i++) {
            int x = i;
            resultList.stream().filter(result -> result.getExamId() == currentExam.get(x).getExamId()).forEach(Result::displayData);
        }
    }

    public static boolean isNotResult() {
        if (resultList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
