import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Test {
    private static Person pierre = new Person("Dupont", "Pierre", "example@gmail.com", "0477/64.16.33", "Kotmanga", LocalDateTime.now(), 0, 0);
    private static Person paul = new Person("Dupont", "Pauk", "example@gmail.com", "0477/64.16.33", "Kotmanga", LocalDateTime.now(), 0, 0);
    private static Serie bleach = new Serie("Bleach", 0, 74, true);
    private static Serie naruto = new Serie("Naruto", 0, 72, true);
    private static Manga t4 = new Manga(bleach, 4, pierre, LocalDateTime.now(), "Bon", false, 0);
    private static Manga t5 = new Manga(bleach, 5, pierre, LocalDateTime.now(), "Bon", false, 0);
    private static Pret pret = new Pret(t4, pierre, Example.date3);

    public static void main(String [] args){
        if (1==1) System.out.println("1 is TRUE");
        else if (0==1) System.out.println("0 is TRUE");
        else System.out.println("Meh.");
    }

    public static void testCompareTo() {
        System.out.println("a".compareTo("b"));
        System.out.println(t4.compareTo(t5));
        System.out.println(paul.compareTo(pierre));
        System.out.println(bleach.compareTo(naruto));
    }

    public static void testSendMail() {
        String to = "julien.aigret@gmail.com";//change accordingly
        String from = "kotmanga@gmail.com"; //change accordingly
        String pw = "NarutoLol";
        String subject = "Test";
        String text = "Ceci est un test pour envoyer des mails via une application java\nSi vous vous posez des questions, demandez à Julien ;)\nDes bisous :D";
        if (SendMail.send(from, to, pw, subject, text)) System.out.println("Message sent");
        else System.out.println("Sent failed");
    }

    public static void testDatePrint() {
        LocalDate date =  LocalDate.from(LocalDate.now().atStartOfDay());
        System.out.println(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
    }

    public static void testPrint() {
        System.out.println(pierre);
        System.out.println(bleach);
        System.out.println(t4);
        System.out.println(pret);
    }
}