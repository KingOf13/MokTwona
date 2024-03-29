import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Utils {


    public static double toPercent(int a, int b) {
        double percentage = (int)((((double) a)/b)*10000);
        percentage /= 100.0;
        return percentage;
    }

    public static double toPercent(int a, int b, int nbDec) {
        double factor = Math.pow(10,nbDec);
        double percentage = (int)((((double) a)/b)*100*factor);
        percentage /= factor;
        return percentage;
    }

    public static void informationDialog(String message) {
        JFrame frame = new JFrame();
        try {
            frame.setIconImage(ImageIO.read(new File("src/img/exclamation.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JOptionPane d = new JOptionPane();
        d.showMessageDialog(frame, message);
        frame.dispose();
    }

    public static boolean confirmationDialog(String question) {
        JFrame frame = new JFrame();
        try {
            frame.setIconImage(ImageIO.read(new File("src/img/question.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JOptionPane d = new JOptionPane();
        int retour = d.showConfirmDialog(frame, question,
                "Confirmation", JOptionPane.OK_CANCEL_OPTION);
        frame.dispose();
        return retour == 0;
    }

    public static int ecartDate(LocalDate fin) {
        fin = LocalDate.from(fin.atStartOfDay());
        LocalDate tday = LocalDate.from(LocalDate.now().atStartOfDay());
        long noOfDays = ChronoUnit.DAYS.between(tday, fin);
        int cmp = (int) noOfDays;
        return cmp;
    }

    public static String formatDate(int cmp){
        String toPrint;
        if (cmp < 0 ) toPrint = (-cmp) + " jour(s) de retard ! (" + computeRetard(-cmp) + " crédits de pénalité)";
        else toPrint = cmp + " jour(s) avant la fin de la location";
        return toPrint;
    }

    public static int computeRetard(int days) {
        int weeks = (int) Math.ceil(days/7.0);
        return weeks*2;
    }

    public static long localDateToLong(LocalDate date) {
        return date.toEpochDay();
    }

    public static long localDateTimeToLong(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.of("-1"));
    }

    public static Serie[] removeNull(Serie[] array) {
        int size = 0;
        for (Serie s: array) if (s != null) size++;
        Serie[] returnArray = new Serie[size];
        int nextIdx = 0;
        for (int i = 0; nextIdx < size; i ++) {
            if (array[i] != null) {
                returnArray[nextIdx] = array[i];
                nextIdx++;
            }
        }
        return returnArray;
    }

    public static Person[] removeNull(Person[] array) {
        int size = 0;
        for (Person s: array) if (s != null) size++;
        Person[] returnArray = new Person[size];
        int nextIdx = 0;
        for (int i = 0; nextIdx < size; i ++) {
            if (array[i] != null) {
                returnArray[nextIdx] = array[i];
                nextIdx++;
            }
        }
        return returnArray;
    }

    public static Manga[] removeNull(Manga[] array) {
        int size = 0;
        for (Manga s: array) if (s != null) size++;
        Manga[] returnArray = new Manga[size];
        int nextIdx = 0;
        for (int i = 0; nextIdx < size; i ++) {
            if (array[i] != null) {
                returnArray[nextIdx] = array[i];
                nextIdx++;
            }
        }
        return returnArray;
    }

    public static LocalDate longToLocalDate(long time) {
        return LocalDate.ofEpochDay(time);
    }

    public static LocalDateTime longToLocalDateTime(long time) {
        return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.of("-1"));
    }
}
