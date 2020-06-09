public class Serie implements Comparable {
    private static int nextID = 1;

    private final int ID;
    private String nom;
    private int lastPossessed;
    private int lastPublished;
    private boolean ended;

    public Serie(int id, String nom, int lastPossessed, int lastPublished, boolean ended) {
        ID = id;
        if (id >= nextID) nextID = id + 1;
        this.nom = nom;
        this.lastPossessed = lastPossessed;
        this.lastPublished = lastPublished;
        this.ended = ended;
    }

    public Serie(String nom, int lastPossessed, int lastPublished, boolean ended) {
        ID = nextID;
        nextID++;
        this.nom = nom;
        this.lastPossessed = lastPossessed;
        this.lastPublished = lastPublished;
        this.ended = ended;
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

    public static int getNextID() {
        return nextID;
    }

    public String getNom() {
        return nom;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void setLastPossessed(int lastPossessed) {
        this.lastPossessed = lastPossessed;
    }

    public void setLastPublished(int lastPublished) {
        this.lastPublished = lastPublished;
    }

    public static void setNextID(int nextID) {
        Serie.nextID = nextID;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
}
