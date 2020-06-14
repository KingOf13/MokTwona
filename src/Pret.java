import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Pret implements Comparable {

    private final Manga tome;
    private final Person person;
    private LocalDate endDate;
    private int rappelSent = 0;
    private static Database db = null;

    public Pret(Manga tome, Person person, LocalDate endDate) {
        this.tome = tome;
        this.person = person;
        this.endDate = endDate;
        this.rappelSent = 0;
    }

    public Pret(Manga tome, Person person, LocalDate endDate, int rappelSent) {
        this.tome = tome;
        this.person = person;
        this.endDate = endDate;
        this.rappelSent = rappelSent;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Manga getTome() {
        return tome;
    }

    public Person getPerson() {
        return person;
    }

    public int getRappelSent() {
        return rappelSent;
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
    }

    public int penalties() {
        return  Utils.computeRetard(Math.abs(Utils.ecartDate(endDate)));
    }

    public boolean prolongate() {
        if (isLate()) endDate = LocalDate.now().plusDays(Manga.loanDuration);
        else endDate = endDate.plusDays(Manga.loanDuration);
        updateInDB();
        return true;
    }

    public int conclude() {
        person.addLecture(new LastLecture(tome, person));
        tome.returnManga();
        person.concludePret(this);
        db.remove(this);
        if (isLate()) return penalties();
        else return 0;
    }

    public boolean isLate() {
        return endDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return person + " a loué " + tome.getSerie() + " " + tome + " jusqu'au " + endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    public String compactString() {
        int days = Math.abs(Utils.ecartDate(endDate));
        int penalties = Utils.computeRetard(days);
        return tome.completeString() + " - " + endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + (isLate()? " [RETARD DE "
                + (days>1 ? days + " JOURS - PENALITE DE " : days + "JOUR - PENALITE DE ") + (penalties>1? penalties + " CREDITS" : penalties + " CREDIT") +"]": "");
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Pret) {
            Pret p = (Pret) o;
            int cmp = endDate.compareTo(p.endDate);
            if (cmp != 0) return cmp;
            else return person.compareTo(p.person);
        }
        throw new ClassCastException("Comparing a loan with something else : BAD IDEA");
    }

    @Override
    public boolean equals(Object obj) {
        int cmp = -1;
        try {
            cmp = compareTo(obj);
        } catch (ClassCastException e) {
            return false;
        }
        return cmp == 0;
    }

    private void updateInDB() {
        db.update(this);
    }
}
