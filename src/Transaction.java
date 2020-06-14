import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Transaction implements Comparable {
    private final LocalDateTime time;
    private final double montant;
    private final String type;
    private final Person person;
    private static Database db = null;

    public Transaction(LocalDateTime time, double montant, String type, Person person) {
        this.time = time;
        this.montant = montant;
        this.type = type;
        this.person = person;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Transaction) {
            Transaction t = (Transaction) o;
            if (!time.equals(t.time)) return time.compareTo(t.time);
            else if (!person.equals(t.person)) return person.compareTo(t.person);
            else if (!type.equals(t.type)) return type.compareTo(t.type);
            else return (int) Math.signum(montant - t.montant);
        }
        throw new ClassCastException("Comparing a transaction with something else : BAD IDEA");
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

    public LocalDateTime getTime() {
        return time;
    }

    public Person getPerson() {
        return person;
    }

    public double getMontant() {
        return montant;
    }

    public String getType() {
        return type;
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
    }

    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) + "\t|\t" + type + "\t|\t" + person.toString()
                + "\t|\t" + montant + "€";
    }

    private void updateInDB() {

    }
}
