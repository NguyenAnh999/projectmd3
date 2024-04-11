package ra.business.config;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
//System.out.println("\u001B[36mCyan text 😢🤦‍♂️🤦‍♀️🤦‍♀️😜🤞✌️🤷‍♂️😁👍🙌😒😍😊😂🤣❤️⚛️♾️◀️🔼");
//        System.out.println("\u001B[37mWhite text😊😂😂😂🤣🤣❤️༼ つ ◕_◕ ༽つ");
//// hiển thị với màu sắc trong màn console (sử dụng icon Window + .)
//        System.out.println("\u001B[31mRed text");
//        System.out.println("\u001B[32mGreen text ༼ つ ◕_◕ ༽つ");
//        System.out.println("\u001B[33mYellow text");
//        System.out.println("\u001B[34mBlue text");
//        System.out.println("\u001B[35mPurple text");
//        System.out.println("\u001B[36mCyan text 😢🤦‍♂️🤦‍♀️🤦‍♀️😜🤞✌️🤷‍♂️😁👍🙌😒😍😊😂🤣❤️⚛️♾️◀️🔼");
/**
 * <p><b><u}>Description detail</u></b> </p>
 * <p>getString()       ==>> Return a string value from the user.</p>
 * <p>getChar()         ==>> Return a character value from the user</p>
 * <p>getBoolean()	    ==>> Return a boolean value from the user.</p>
 * <p>getByte()	        ==>> Return a byte value from the user.</p>
 * <p>getShort()	    ==>> Return a short value from the user.</p>
 * <p>getInteger()	    ==>> Return a integer value from the user.</p>
 * <p>getLong()	        ==>> Return a long value from the user.</p>
 * <p>getFloat()	    ==>> Return a float value from the user.</p>
 * <p>getDouble()	    ==>> Return a double value from the user.</p>
 */
public final class InputMethods {
    private static final String ERROR_ALERT = "===>> Định dạng không hợp lệ, hoặc ngoài phạm vi! Vui lòng thử lại....";
    private static final String ERROR_DATE = "===>> Định dạng không hợp lệ (dd/MM/yyyy)! Vui lòng thử lại....";
    private static final String EMPTY_ALERT = "===>> Trường nhập vào không thể để trống! Vui lòng thử lại....";

    public static final Scanner input = new Scanner(System.in);
    /*========================================Input Method Start========================================*/

    /**
     * getString()  Return a String value from the user.
     */
    public static String getString() {
        while (true) {
            String result = getInput();
            if (result.equals("")) {
                System.err.println(EMPTY_ALERT);
                continue;
            }
            return result;
        }
    }

    /**
     * getChar()  Return a Character value from the user.
     */
    public static char getChar() {
        return getString().charAt(0);
    }

    /**
     * getBoolean()  Return a Boolean value from the user.
     */
    public static boolean getBoolean() {
        String result = getString();
        return result.equalsIgnoreCase("true");
    }

    /**
     * getByte()  Return a Byte value from the user.
     */
    public static byte getByte() {
        while (true) {
            try {
                return Byte.parseByte(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getShort()  Return a Short value from the user.
     */
    public static short getShort() {
        while (true) {
            try {
                return Short.parseShort(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getInteger()  Return a Integer value from the user.
     */
    public static int getInteger() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getLong()  Return a Long value from the user.
     */
    public static long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getFloat()  Return a Float value from the user.
     */
    public static float getFloat() {
        while (true) {
            try {
                return Float.parseFloat(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getDouble()  Return a Double value from the user.
     */
    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    /*========================================Input Method End========================================*/

    /**
     * getInput()  Return any String value from the user.
     */
    private static String getInput() {
        return input.nextLine();
    }

    /**
     * Nhap ngay thang : dd/MM/yyyy
     */
    public static Date getDate(){
        while (true) {
            try {
                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                return sf.parse(getInput());
            } catch (ParseException errException) {
                System.err.println(ERROR_DATE);
            }
        }
    }

    /**
     * pressAnyKey()  Press any key to continue....
     */
    public static void pressAnyKey() {
        getInput();
    }
    public static LocalDate getLocalDate(){
        while (true) {
            try {
                DateTimeFormatter sf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return LocalDate.parse(getInput(),sf);
            } catch (Exception e) {
                System.err.println(ERROR_DATE);
            }
        }
    }
    /*========================================Other Method========================================*/
}


