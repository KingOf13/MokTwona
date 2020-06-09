import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Pret implements Comparable {

    private final Manga tome;
    private final Person person;
    private final LocalDate endDate;

    public Pret(Manga tome, Person person, LocalDate endDate) {
        this.tome = tome;
        this.person = person;
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return person + " a loué " + tome.getSerie() + " " + tome + " jusqu'au " + endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
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
}
