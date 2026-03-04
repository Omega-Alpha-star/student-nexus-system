package student.nexus.util;

import java.time.Year;
import java.util.Random;

public class StudentGenerator {

    public static String generateStudentNumber () {

        String year = String.valueOf(Year.now().getValue()).substring(2); // this takes the last two integer of the actual year

        Random rd = new Random();
        int randomNumber = 100000 + rd.nextInt(900000);

        return year + randomNumber;
    }

    public static String generatePin () {

        Random rd = new Random();
        int pin = 10000 + rd.nextInt(90000);
        return String.valueOf(pin);
    }
}
