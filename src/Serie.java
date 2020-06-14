import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Serie implements Comparable {

    private final int ID;
    private String nom;
    private int lastPossessed;
    private int lastPublished;
    private boolean ended;
    private static Database db = null;
    private ArrayList<Tag> tags = new ArrayList<Tag>();
    private ArrayList<Manga> tomes = new ArrayList<Manga>();

    public Serie(int id, String nom, int lastPossessed, int lastPublished, boolean ended) {
        ID = id;
        this.nom = nom;
        this.lastPossessed = lastPossessed;
        this.lastPublished = lastPublished;
        this.ended = ended;
    }

    public Serie(String nom, int lastPossessed, int lastPublished, boolean ended) {
        ID = db.nextSerieId();
        this.nom = nom;
        this.lastPossessed = lastPossessed;
        this.lastPublished = lastPublished;
        this.ended = ended;
        db.add(this);
    }

    public int getID() {
        return ID;
    }

    public int getLastPossessed() {
        return lastPossessed;
    }

    public int getLastPublished() {
        return lastPublished;
    }

    public String getNom() {
        return nom;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
        updateInDB();
    }

    public void addTome(Manga manga) {
        if (manga.getNumero() > lastPossessed) lastPossessed = manga.getNumero();
        if (manga.getNumero() > lastPublished) lastPublished = manga.getNumero();
        tomes.add(manga);
        if (db!= null) updateInDB();
    }

    public Manga[] getTomes() {
        Manga[] list = new Manga[tomes.size()];
        list = tomes.toArray(list);
        Arrays.sort(list, new Comparator<Manga>() {
            @Override
            public int compare(Manga o1, Manga o2) {
                return o1.compareTo(o2);
            }
        });
        return list;
    }

    public boolean isEmpty() {
        return tomes.size() == 0;
    }

    public void setLastPossessed(int lastPossessed) {
        this.lastPossessed = lastPossessed;
        updateInDB();
    }

    public void setLastPublished(int lastPublished) {
        this.lastPublished = lastPublished;
        updateInDB();
    }

    public void setNom(String nom) {
        this.nom = nom;
        updateInDB();
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
    }

    public boolean addTag (Tag tag) {
        if (tags.contains(tag)) return false;
        tags.add(tag);
        return true;
    }

    public boolean isEnded() {
        return ended;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Serie) {
            Serie s = (Serie) o;
            if (!nom.equals(s.nom)) return nom.compareTo(s.nom);
            else return s.ID - this.ID;
        }
        throw new ClassCastException("Comparing a serie with something else : BAD IDEA");
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


    public void removeTome(Manga manga) {
        tomes.remove(manga);
        if (manga.getNumero() == lastPossessed) {
            int max = 0;
            for (Manga m: tomes) if (m.getNumero() > max) max = m.getNumero();
            lastPossessed = max;
            updateInDB();
        }
    }
}
