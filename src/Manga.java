import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class containing a Manga, as represented in the DB
 */
public class Manga implements Comparable {
    public static int loanDuration = 14;

    private final int ID;
    private final int numero;
    private final Serie serie;
    private int nbLocation;
    private final Person proprio;
    private boolean loue;
    private String etat;
    private final LocalDateTime addedTime;
    private static Database db = null;

    public Manga(int id, Serie serie, int numero, Person proprio, LocalDateTime addedTime, String etat, boolean loue, int nbLocation) {
        ID = id;
        this.numero = numero;
        this.serie = serie;
        this.nbLocation = nbLocation;
        this.proprio = proprio;
        this.loue = loue;
        this.etat = etat;
        this.addedTime = addedTime;
        serie.addTome(this);
    }

    public Manga(Serie serie, int numero, Person proprio, LocalDateTime addedTime, String etat, boolean loue, int nbLocation) {
        ID = db.nextMangaId();
        this.numero = numero;
        this.serie = serie;
        this.nbLocation = nbLocation;
        this.proprio = proprio;
        this.loue = loue;
        this.etat = etat;
        this.addedTime = addedTime;
        serie.addTome(this);
        db.add(this);
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

    public void setEtat(String etat) {
        this.etat = etat;
        updateInDB();
    }

    public void returnManga() {
        loue = false;
        updateInDB();
    }

    public Pret locate(Person person, boolean crew, boolean free) {
        if (loue) return null;
        Pret pret = new Pret(this, person, LocalDate.now().plusDays(loanDuration));
        if (!person.addPret(pret, crew, free)) return null;
        if  (db.add(pret)) {
            loue = true;
            nbLocation ++;
            updateInDB();
            return pret;
        }
        else return null;
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
    }

    public void setNbLocation(int nbLocation) {
        this.nbLocation = nbLocation;
        updateInDB();
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

    private void updateInDB() {
        db.update(this);
    }

    public String completeString() {
        return serie.getNom() + " tome " + numero;
    }
}
