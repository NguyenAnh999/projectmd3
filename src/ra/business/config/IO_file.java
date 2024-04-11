package ra.business.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IO_file {
    public final static String USER_PATH = "C:\\JavaCodeOff\\project_Quizz\\src\\ra\\data\\Users.txt";
    public final static String CURRENT_USER_PATH = "C:\\JavaCodeOff\\project_Quizz\\src\\ra\\data\\CurrentUser.txt";
    public final static String EXAM_PATH ="C:\\JavaCodeOff\\project_Quizz\\src\\ra\\data\\Exam.txt";

    public static <T> List<T> readObjFromFile(String PATH) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(PATH);
            ois = new ObjectInputStream(fis);
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ArrayList<>();
    }


    public static <T> void writeObjFromFile(List<T> list, String PATH) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(PATH);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
