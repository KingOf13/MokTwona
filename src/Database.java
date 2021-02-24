import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Database {
    public Transaction[] getTransactions() {
        Transaction[] returnArray = new Transaction[transactions.size()];
        Iterator<Transaction> iterator = transactions.iterator();
        for (int i = 0; iterator.hasNext(); i++) returnArray[i] = iterator.next();
        Arrays.sort(returnArray);
        return returnArray;
    }

    private class InitialisationException extends Exception{
        public InitialisationException(String action) {
            super("Database initialisation failed when " + action +".");
        }
    }
    private static final String url = "jdbc:sqlite:./mokona.db";
    private Serie[] series;
    private Person[] people;
    private Manga[] mangas;
    private ArrayList<Pret> prets;
    private ArrayList<LastLecture> lectures;
    private ArrayList<Tag> tags;
    private ArrayList<Transaction> transactions;

    private final Connection connection;

    public static Database initDB() {
        try {
            Database db = new Database();
            boolean successed = Serie.setDb(db);
            successed &= LastLecture.setDb(db);
            successed &= Manga.setDb(db);
            successed &= Person.setDb(db);
            successed &= Pret.setDb(db);
            successed &= Tag.setDb(db);
            successed &= Transaction.setDb(db);
            if (!successed) return null;

            return db;
        } catch (InitialisationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Database() throws InitialisationException {
        Connection conn = null;
        try {
            //Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            if (conn == null) {
                System.err.println("No connection to DB");
                throw new InitialisationException("connecting to URL, returned null");
            }
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new InitialisationException("loading classe");
        } */catch (SQLException e) {
            e.printStackTrace();
            throw new InitialisationException("connecting to URL");
        }
        connection = conn;
        if (!initSeries()) throw new InitialisationException("initialising series");
        if (!initPeople()) throw new InitialisationException("initialising people");
        if (!initMangas()) throw new InitialisationException("initialising mangas");
        if (!initTransactions()) throw new InitialisationException("initialising transactions");
        if (!initPrets()) throw new InitialisationException("initialising prets");
        if (!initLastLectures()) throw new InitialisationException("initialising last lectures");
        if (!initTags()) throw new InitialisationException("initialising tags");
    }


    private boolean initSeries() {
        DBHandler.connectSeriesTable(connection);
        ResultSet rs = DBHandler.getAllSerie(connection);
        int maxID = 1;
        ArrayList<Serie> seriesTemp = new ArrayList<Serie>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean ended = rs.getInt("ended") == 1;
                int lastPossessed = rs.getInt("last_possessed");
                int lastPublished = rs.getInt("last_published");

                seriesTemp.add(new Serie(id, name, lastPossessed, lastPublished, ended));
                if (id > maxID) maxID = id;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        series = new Serie[maxID + 3];
        Iterator<Serie> ite = seriesTemp.iterator();
        while (ite.hasNext()) {
            Serie s = ite.next();
            series[s.getID()] = s;
        }
        return true;
    }

    private boolean initPeople() {
        DBHandler.connectPersonTable(connection);
        ResultSet rs = DBHandler.getAllPerson(connection);
        int maxID = 1;
        ArrayList<Person> peopleTemp = new ArrayList<Person>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                String gsm = rs.getString("gsm");
                String address = rs.getString("address");
                LocalDateTime time = Utils.longToLocalDateTime(rs.getLong("last_activity"));
                int caution = rs.getInt("caution");
                int credit = rs.getInt("credit");
                peopleTemp.add(new Person(id, nom, prenom, mail, gsm, address, time, caution, credit));
                if (id > maxID) maxID = id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        people = new Person[maxID + 5];
        Iterator<Person> ite = peopleTemp.iterator();
        while (ite.hasNext()) {
            Person p = ite.next();
            people[p.getID()] = p;
        }
        return true;
    }

    private boolean initMangas() {
        DBHandler.connectMangaTable(connection);
        ResultSet rs = DBHandler.getAllManga(connection);
        int maxID = 1;
        ArrayList<Manga> mangasTemp = new ArrayList<Manga>();

        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                Serie serie = series[rs.getInt("serie_id")];
                Person proprio = people[rs.getInt("proprio_id")];
                int numero = rs.getInt("numero");
                int nbLocation = rs.getInt("nb_location");
                boolean loue = rs.getInt("loue") == 1;
                LocalDateTime addedTime = Utils.longToLocalDateTime(rs.getLong("added_time"));
                String etat = rs.getString("etat");
                mangasTemp.add(new Manga(id, serie, numero, proprio, addedTime, etat, loue, nbLocation));
                if (id > maxID) maxID = id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        mangas = new Manga[maxID + 5];
        Iterator<Manga> ite = mangasTemp.iterator();

        while (ite.hasNext()) {
            Manga m = ite.next();
            mangas[m.getID()] = m;
        }

        return true;
    }

    private boolean initPrets() {
        DBHandler.connectPretTable(connection);
        ResultSet rs = DBHandler.getAllPret(connection);
        int count = 0;
        prets = new ArrayList<Pret>();

        try {
            while (rs.next()) {
                Manga manga = mangas[rs.getInt("manga_id")];
                Person person = people[rs.getInt("person_id")];
                LocalDate endDate = Utils.longToLocalDate(rs.getLong("end_time"));
                int rappelSent = rs.getInt("rappel_sent");
                Pret pret = new Pret(manga, person, endDate, rappelSent);
                person.addPret(pret);
                prets.add(pret);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean initLastLectures() {
        DBHandler.connectLastLectureTable(connection);
        ResultSet rs = DBHandler.getAllLastLecture(connection);

        lectures = new ArrayList<LastLecture>();

        try {
            while (rs.next()) {
                Serie serie = series[rs.getInt("serie_id")];
                Person person = people[rs.getInt("person_id")];
                int numero = rs.getInt("numero");
                LocalDateTime time = Utils.longToLocalDateTime(rs.getLong("time"));

                lectures.add(new LastLecture(serie, numero, person, time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean[] findBlanks(Serie serie) {
        int lastPublished = serie.getLastPublished();
        boolean[] tomes = new boolean[lastPublished];
        for (boolean t: tomes) t = false;
        ResultSet rs = DBHandler.getMangaFromSerie(connection, serie.getID());
        try {
            while (rs.next()) {
                int numero = rs.getInt("numero");
                tomes[numero-1] = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tomes;
    }

    public ArrayList<Person> getLoaners() {
        ResultSet rs = DBHandler.getPersonLoaning(connection);
        ArrayList<Person> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(people[rs.getInt("id")]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Manga> getLoanedByPerson(Person person) {
        ArrayList<Manga> list = new ArrayList<>();
        ResultSet rs = DBHandler.getMangaFromPerson(connection, person.getID());
        try {
            while (rs.next()) {
                list.add(mangas[rs.getInt("id")]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Manga[] getMangas() {
        return Utils.removeNull(mangas);
    }

    public Person[] getPeople() {
        return Utils.removeNull(people);
    }

    public Serie[] getSeries() {
        return Utils.removeNull(series);
    }


    private boolean initTags() {
        DBHandler.connectTagTable(connection);
        ResultSet rs = DBHandler.getAllTag(connection);

        tags = new ArrayList<Tag>();

        try {
            while (rs.next()) {
                String name = rs.getString("tag_name");
                Serie serie = series[rs.getInt("serie_id")];

                int idx = tags.indexOf(new Tag(name));
                if (idx == -1) tags.add(new Tag(name, serie));
                else tags.get(idx).add(serie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean initTransactions() {
        DBHandler.connectTransactionTable(connection);
        ResultSet rs = DBHandler.getAllTransaction(connection);

        transactions = new ArrayList<Transaction>();

        try {
            while (rs.next()) {
                LocalDateTime time = Utils.longToLocalDateTime(rs.getLong("date"));
                double montant = rs.getDouble("montant");
                String type = rs.getString("type");
                Person person = people[rs.getInt("person")];

                transactions.add(new Transaction(time, montant, type, person));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public int nextPersonId() {
        int idx;
        for (idx = 0; idx < people.length; idx ++)
            if (people[idx] == null) return idx;
        return idx;
    }

    public int nextSerieId() {
        int idx;
        for (idx = 0; idx < series.length; idx ++)
            if (series[idx] == null) return idx;
        return idx;
    }

    public int nextMangaId() {
        int idx;
        for (idx = 0; idx < mangas.length; idx ++)
            if (mangas[idx] == null) return idx;
        return idx;
    }

    public boolean add(Serie serie) {
        if (serie.getID() < series.length) {
            if (series[serie.getID()] != null) {
                series[serie.getID()] = serie;
                return DBHandler.updateSerie(connection, serie);
            }
            else {
                series[serie.getID()] = serie;
                return DBHandler.insertSeries(connection, serie);
            }
        }
        else {
            Serie[] temp = new Serie[series.length + 10];
            for (int i = 0; i < series.length; i++) temp[i] = series[i];
            series = temp;
            series[serie.getID()] = serie;
            return DBHandler.insertSeries(connection, serie);
        }
    }

    public boolean add(Manga manga) {
        if (manga.getID() < mangas.length) {
            if (mangas[manga.getID()] != null) {
                mangas[manga.getID()] = manga;
                return DBHandler.updateManga(connection, manga);
            }
            else {
                mangas[manga.getID()] = manga;
                return DBHandler.insertManga(connection, manga);
            }
        }
        else {
            Manga[] temp = new Manga[mangas.length + 10];
            for (int i = 0; i < mangas.length; i++) temp[i] = mangas[i];
            mangas = temp;
            mangas[manga.getID()] = manga;
            return DBHandler.insertManga(connection, manga);
        }
    }

    public boolean add(LastLecture lecture) {
        return DBHandler.insertLastLecture(connection, lecture);
    }

    public boolean add(Person person) {
        System.out.println("BEGIN INSERTION");
        if (person.getID() < people.length) {
            if (people[person.getID()] != null) {
                people[person.getID()] = person;
                return DBHandler.updatePerson(connection, person);
            }
            else {
                people[person.getID()] = person;
                return DBHandler.insertPeople(connection, person);
            }
        }
        else {
            Person[] temp = new Person[people.length + 10];
            for (int i = 0; i < people.length; i++) temp[i] = people[i];
            people = temp;
            people[person.getID()] = person;
            return DBHandler.insertPeople(connection, person);
        }
    }

    public boolean add(Tag tag, Serie serie) {
        return DBHandler.insertTag(connection, tag.getName(), serie);
    }

    public boolean add(Pret pret) {
        if (DBHandler.insertPret(connection, pret)) return prets.add(pret);
        else return false;
    }

    public boolean add(Transaction transaction) {
        transactions.add(transaction);
        return DBHandler.insertTransaction(connection, transaction);
    }

    public boolean update(Person person) {
        return DBHandler.updatePerson(connection, person);
    }

    public boolean update(Manga manga) {
        return DBHandler.updateManga(connection, manga);
    }

    public boolean update(Pret pret) {
        return DBHandler.updatePret(connection, pret);
    }

    public boolean update(Serie serie) {
        return DBHandler.updateSerie(connection, serie);
    }

    public boolean remove(Tag tag) {
        if (DBHandler.removeTagByName(connection, tag.getName()) && tags.contains(tag))
            tags.remove(tag);
        return !tags.contains(tag);
    }

    public boolean remove(Tag tag, Serie serie) {
        return DBHandler.removeTag(connection, serie, tag.getName());
    }

    public boolean remove(LastLecture lecture) {
        return DBHandler.removeLastLecture(connection, lecture);
    }

    public boolean remove(Transaction transaction) {
        return DBHandler.removeTransaction(connection, transaction);
    }

    public boolean remove(Pret pret) {
        return DBHandler.removePret(connection, pret);
    }

    public boolean remove(Serie serie) {
        if (DBHandler.removeSerie(connection, serie)) series[serie.getID()] = null;
        return series[serie.getID()] == null;
    }

    public boolean remove(Person person) {
        if (DBHandler.removePerson(connection, person)) people[person.getID()] = null;
        return people[person.getID()] == null;
    }

    public boolean remove(Manga manga) {
        if (DBHandler.removeManga(connection, manga)) mangas[manga.getID()] = null;
        return mangas[manga.getID()] == null;
    }

    public void removeAllTransactions() {
        DBHandler.removeAllTransactions(connection);
        transactions = new ArrayList<Transaction>();
    }

    public void removeFromPret(Manga manga) {
        Pret toRemove = null;
        boolean found = false;
        for (Pret p: prets){
            if (p.getTome().equals(manga)) {
                toRemove = p;
                found = true;
          }
        }
        if (found) {
            toRemove.getPerson().removeFromPret(toRemove);
            prets.remove(toRemove);
        }
        DBHandler.removeFromPret(connection, manga);
    }
}
