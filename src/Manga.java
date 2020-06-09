import java.time.LocalDateTime;

/**
 * Class containing a Manga, as represented in the DB
 */
public class Manga implements Comparable {
    private static int nextID = 1;

    private final int ID;
    private final int numero;
    private final Serie serie;
    private int nbLocation;
    private final Person proprio;
    private boolean loue;
    private String etat;
    private final LocalDateTime addedTime;

    public Manga(int id, Serie serie, int numero, Person proprio, LocalDateTime addedTime, String etat, boolean loue, int nbLocation) {
        ID = id;
        if (id >= nextID) nextID = id + 1;
        this.numero = numero;
        this.serie = serie;
        this.nbLocation = nbLocation;
        this.proprio = proprio;
        this.loue = loue;
        this.etat = etat;
        this.addedTime = addedTime;
        if (serie.getLastPossessed() < numero) serie.setLastPossessed(numero);
        if (serie.getLastPublished() < numero) serie.setLastPublished(numero);
    }

    public Manga(Serie serie, int numero, Person proprio, LocalDateTime addedTime, String etat, boolean loue, int nbLocation) {
        ID = nextID;
        this.numero = numero;
        this.serie = serie;
        this.nbLocation = nbLocation;
        this.proprio = proprio;
        this.loue = loue;
        this.etat = etat;
        this.addedTime = addedTime;
        nextID ++;
        if (serie.getLastPossessed() < numero) serie.setLastPossessed(numero);
        if (serie.getLastPublished() < numero) serie.setLastPublished(numero);
    }

    public static int getNextID() {
        return nextID;
    }

    public int getID() {
        return ID;
    }

    public int getNbLocation() {
        return nbLocation;
    }

    public int getNumero() {
        return numero;
    }

    public Person getProprio() {
        return proprio;
    }

    public Serie getSerie() {
        return serie;
    }

    public String getEtat() {
        return etat;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public static void setNextID(int nextID) {
        Manga.nextID = nextID;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setLoue(boolean loue) {
        this.loue = loue;
    }

    public void setNbLocation(int nbLocation) {
        this.nbLocation = nbLocation;
    }

    @Override
    public String toString() {
        return "tome " + numero;
    }

    public boolean isLoue() {
        return loue;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Manga) {
            Manga m = (Manga) o;
            if (!serie.equals(m.serie)) return serie.compareTo(m.serie);
            else return numero - m.numero;
        }
        throw new ClassCastException("Comparing a manga with something else : BAD IDEA");
    }

    @Override
    public boolean equals(Object obj) {
        int cmp = -1;
        try {
            cmp = compareTo(obj);
        } catch (ClassCastException e) {
            cmp = -1;
        }
        return cmp == 0;
    }
}
