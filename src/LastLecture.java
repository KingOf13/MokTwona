import java.time.LocalDateTime;

public class LastLecture implements Comparable{
    private final Manga manga;
    private final Person person;
    private final LocalDateTime time;

    public LastLecture(Manga manga, Person person, LocalDateTime time) {
        this.manga = manga;
        this.person = person;
        this.time = time;
    }

    public LastLecture(Manga manga, Person person) {
        this.manga = manga;
        this.person = person;
        this.time = LocalDateTime.now();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof LastLecture) {
            LastLecture l = (LastLecture) o;
            if (!person.equals(l.person)) return person.compareTo(l.person);
            else if (!time.equals(l.time)) return time.compareTo(l.time);
            else return manga.compareTo(l.manga);
        }
        throw new ClassCastException("Comparing a last lecture with something else : BAD IDEA");
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

    public Manga getManga() {
        return manga;
    }
}
