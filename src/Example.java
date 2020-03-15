import java.time.LocalDate;

public class Example {
    public static final int[] exCredit = {1, 3, 42, 69};
    public static final int[] exCaution = {10, 5, 0, 10};

    public static String[] exName = {"Pierre", "Paul", "Jean", "Jacques"};

    public static String[] exPretsPierre = {"Naruto tome 1", "Bleach tome 7", "One Piece tome 2"};
    public static String[] exPretsPaul = {"Naruto tome 2", "Bleach tome 3", "One Piece tome 15"};
    public static String[] exPretsJean = {"Naruto tome 7", "Bleach tome 52", "One Piece tome 88", "One Piece tome 89", "One Piece tome 90"};
    public static String[] exPretsJacques = {"Naruto tome 6", "Bleach tome 1", "Bleach tome 2"};
    public static String[][] exPrets = {exPretsPierre, exPretsPaul, exPretsJean, exPretsJacques};

    public static LocalDate date1 = LocalDate.of(2020, 03, 22);
    public static LocalDate date2 = LocalDate.of(2020, 03, 10);

    public static LocalDate[] exDatePierre = {date1, date1, date1};
    public static LocalDate[] exDatePaul = {date2, date2, date2};
    public static LocalDate[] exDateJean = {date2, date2, date1, date1, date1};
    public static LocalDate[] exDateJacques = {date1, date1, date1};
    public static LocalDate[][] exDate = {exDatePierre, exDatePaul, exDateJean, exDateJacques};

    public static final String[] exManga = {"Bleach", "Naruto", "One Piece"};
    public static final int[] exLast = {75, 73, 93};
}
