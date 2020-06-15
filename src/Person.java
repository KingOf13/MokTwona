import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Person implements Comparable {
    private static int historicSize = 5;

    private final int ID;
    private final String nom;
    private final String prenom;
    private String email;
    private String gsm;
    private String address;
    private LocalDateTime lastActivity;
    private LastLecture[] lectures = new LastLecture[historicSize];
    private int caution;
    private int credit;
    private static Database db = null;
    private ArrayList<Pret> prets = new ArrayList<Pret>();

    public Person(int id, String nom, String prenom, String email, String gsm, String address, LocalDateTime lastActivity, int caution, int credit) {
        this.ID = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.gsm = gsm;
        this.address = address;
        this.lastActivity = lastActivity;
        this.caution = caution;
        this.credit = credit;
    }

    public Person(String nom, String prenom, String email, String gsm, String address, LocalDateTime lastActivity, int caution, int credit) {
        this.ID = db.nextPersonId();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.gsm = gsm;
        this.address = address;
        this.lastActivity = lastActivity;
        this.caution = caution;
        this.credit = credit;
        db.add(this);
    }

    public void addLecture(LastLecture lastLecture) {
        for (int i = 0; i < lectures.length; i++) if (lectures[i] == null) {
            lectures[i] = lastLecture;
            return;
        }
        Arrays.sort(lectures, new Comparator<LastLecture>() {
            @Override
            public int compare(LastLecture o1, LastLecture o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        db.remove(lectures[0]);
        db.add(lastLecture);
        lectures[0] = lastLecture;
    }

    public void concludePret(Pret pret) {
        prets.remove(pret);
        addLecture(new LastLecture(pret.getTome(), this));
        updateInDB();
    }

    public boolean addPret(Pret pret, boolean crewMember, boolean free) {
        if (!crewMember && outOfCapacity()) return false;
        if (!free && credit < 1) return false;
        prets.add(pret);
        if (!free) credit --;
        updateInDB();
        return true;
    }

    public Pret[] getPrets() {
        Pret[] array = new Pret[prets.size()];
        Iterator<Pret> ite = prets.iterator();
        for (int i = 0; ite.hasNext(); i++) array[i] = ite.next();
        return array;
    }

    public void addPret(Pret pret) {
        prets.add(pret);
    }

    private boolean outOfCapacity() {
        switch (caution) {
            case 0:
                return true;
            case 5:
                return prets.size() >= 2;
            case 10:
                return prets.size() >= 5;
            default:
                return false;
        }
    }

    public int capacityRemaining() {
        switch (caution) {
            case 0:
                return 0;
            case 5:
                return Math.max(2-prets.size(), 0);
            case 10:
                return Math.max(5-prets.size(), 0);
            default:
                return 0;
        }
    }

    public int getID() {
        return ID;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getCaution() {
        return caution;
    }

    public int getCredit() {
        return credit;
    }

    public String getAddress() {
        return address;
    }

    public String getGsm() {
        return gsm;
    }

    public void setEmail(String email) {
        this.email = email;
        updateInDB();
    }

    public void setAddress(String address) {
        this.address = address;
        updateInDB();
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
        updateInDB();
    }

    public void setCaution(int caution) {
        this.caution = caution;
        updateInDB();
    }

    public void setCredit(int credit) {
        this.credit = credit;
        updateInDB();
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
    }

    public void modify(String address, String mail, String gsm) {
        this.address = address;
        this.email = mail;
        this.gsm = gsm;
        updateInDB();
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Person) {
            Person p = (Person) o;
            if (!nom.equals(p.nom)) return nom.compareTo(p.nom);
            else if (!prenom.equals(p.prenom)) return prenom.compareTo(p.prenom);
            else return this.ID - p.ID;
        }
        throw new ClassCastException("Comparing a person with something else : BAD IDEA");
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
        setLastActivity(LocalDateTime.now());
        db.update(this);
    }

    public void addCredit(int creditToAdd) {
        credit += creditToAdd;
        updateInDB();
    }

    public void removeFromPret(Pret pret) {
        prets.remove(pret);
    }
}
