import java.time.LocalDateTime;

public class Transaction implements Comparable {
    private final LocalDateTime time;
    private final int montant;
    private final String type;
    private final Person person;

    public Transaction(LocalDateTime time, int montant, String type, Person person) {
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
            else return montant - t.montant;
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

    public int getMontant() {
        return montant;
    }

    public String getType() {
        return type;
    }
}
