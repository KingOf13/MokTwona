import java.time.LocalDate;
import java.time.Period;

public class Utils {

    public static int ecartDate(LocalDate fin) {
        Period period;
        fin = LocalDate.from(fin.atStartOfDay());
        LocalDate tday = LocalDate.from(LocalDate.now().atStartOfDay());
        period = Period.between(tday, fin);
        int cmp = period.getDays();
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
}
