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
}
