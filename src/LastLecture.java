import java.time.LocalDateTime;

public class LastLecture implements Comparable{
    private final Serie serie;
    private final Person person;
    private final int numero;
    private final LocalDateTime time;
    private static Database db = null;

    public LastLecture(Manga manga, Person person, LocalDateTime time) {
        this.serie = manga.getSerie();
        this.numero = manga.getNumero();
        this.person = person;
        this.time = time;
    }

    public LastLecture(Manga manga, Person person) {
        this.serie = manga.getSerie();
        this.numero = manga.getNumero();
        this.person = person;
        this.time = LocalDateTime.now();
    }

    public LastLecture(Serie serie, int numero, Person person, LocalDateTime time) {
        this.serie = serie;
        this.numero = numero;
        this.person = person;
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof LastLecture) {
            LastLecture l = (LastLecture) o;
            if (!person.equals(l.person)) return person.compareTo(l.person);
            else if (!time.equals(l.time)) return time.compareTo(l.time);
            else if (!serie.equals(l.serie)) return serie.compareTo(l.serie);
            else return numero - l.numero;
        }
        throw new ClassCastException("Comparing a last lecture with something else : BAD IDEA");
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
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

    public Person getPerson() {
        return person;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Serie getSerie() {
        return serie;
    }

    public int getNumero() {
        return numero;
    }

    private void updateInDB() {

    }
}
