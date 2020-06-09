import java.time.LocalDateTime;

public class Person implements Comparable {
    private static int nextID = 1;

    private final int ID;
    private final String nom;
    private final String prenom;
    private String email;
    private String gsm;
    private String address;
    private LocalDateTime lastActivity;
    private int caution;
    private int credit;

    public Person(int id, String nom, String prenom, String email, String gsm, String address, LocalDateTime lastActivity, int caution, int credit) {
        ID = id;
        if (nextID <= id) nextID = id + 1;
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
        ID = nextID;
        nextID++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.gsm = gsm;
        this.address = address;
        this.lastActivity = lastActivity;
        this.caution = caution;
        this.credit = credit;
    }

    public int getID() {
        return ID;
    }

    public static int getNextID() {
        return nextID;
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

    public static void setNextID(int nextID) {
        Person.nextID = nextID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public void setCaution(int caution) {
        this.caution = caution;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
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
}
