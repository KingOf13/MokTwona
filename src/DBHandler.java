import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class DBHandler {
    private static String url = "jdbc:sqlite:./moktwona.db";

    public static void connectSeriesTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS series (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    ended integer DEFAULT 0,\n"
                + "    last_possessed integer DEFAULT 1,\n"
                + "    last_published integer DEFAULT 1\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectPersonTable(Connection conn) {


        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS person (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    nom text NOT NULL,\n"
                + "    prenom text NOT NULL,\n"
                + "    mail text NOT NULL,\n"
                + "    gsm text NOT NULL,\n"
                + "    address text NOT NULL,\n"
                + "    last_activity integer NOT NULL DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'localtime')),\n"
                + "    caution integer NOT NULL DEFAULT 0,\n"
                + "    credit integer NOT NULL DEFAULT 0\n"
                + ");";
        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectMangaTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS manga (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    serie_id integer NOT NULL,\n"
                + "    proprio_id integer NOT NULL,\n"
                + "    numero integer NOT NULL,\n"
                + "    nb_location integer NOT NULL DEFAULT 0,\n"
                + "    loue integer NOT NULL,\n"
                + "    added_time integer NOT NULL DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'localtime')),\n"
                + "    etat text DEFAULT \"Bon\",\n"
                + "    FOREIGN KEY (serie_id) REFERENCES series (id),\n"
                + "    FOREIGN KEY (proprio_id) REFERENCES person (id)\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectLastLectureTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS lastLecture (\n"
                + "    serie_id integer NOT NULL,\n"
                + "    person_id integer NOT NULL,\n"
                + "    numero integer NOT NULL,\n"
                + "    time integer NOT NULL DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'localtime')),\n"
                + "    FOREIGN KEY (serie_id) REFERENCES series (id),\n"
                + "    FOREIGN KEY (person_id) REFERENCES person (id)\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectPretTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS pret (\n"
                + "    manga_id integer NOT NULL,\n"
                + "    person_id integer NOT NULL,\n"
                + "    end_time integer NOT NULL DEFAULT (DATE(CURRENT_TIMESTAMP, 'localtime', '+14 day')),\n"
                + "    rappel_sent integer NOT NULL DEFAULT 0,\n"
                + "    FOREIGN KEY (manga_id) REFERENCES manga (id),\n"
                + "    FOREIGN KEY (person_id) REFERENCES person (id)\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectTagTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS tag (\n"
                + "    tag_name text NOT NULL,\n"
                + "    serie_id integer NOT NULL,\n"
                + "    FOREIGN KEY (serie_id) REFERENCES series (id)\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectTransactionTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS transaction_table (\n"
                + "    montant real NOT NULL,\n"
                + "    person integer NOT NULL,\n"
                + "    type text NOT NULL,\n"
                + "    date integer NOT NULL DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'localtime')),\n"
                + "    FOREIGN KEY (person) REFERENCES person (id)\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
//            System.out.println("Connected table transaction");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static boolean insertSeries(Connection conn, Serie serie) {
        String sql = "INSERT OR REPLACE INTO series(id, name, ended, last_published, last_possessed) VALUES(?,?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, serie.getID());
            pstmt.setString(2, serie.getNom());
            if (serie.isEnded()) pstmt.setInt(3, 1);
            else pstmt.setInt(3, 0);
            pstmt.setInt(4, serie.getLastPublished());
            pstmt.setInt(5, serie.getLastPossessed());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static boolean insertPeople(Connection conn, Person person) {
        String sql = "INSERT OR REPLACE INTO person(id, nom, prenom, mail, gsm, address, last_activity, caution, credit) VALUES(?,?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, person.getID());
            pstmt.setString(2, person.getNom());
            pstmt.setString(3, person.getPrenom());
            pstmt.setString(4, person.getEmail());
            pstmt.setString(5, person.getGsm());
            pstmt.setString(6, person.getAddress());
            pstmt.setLong(7, person.getLastActivity().toEpochSecond(ZoneOffset.of("-1")));
            pstmt.setInt(8, person.getCaution());
            pstmt.setInt(9, person.getCredit());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static boolean insertManga(Connection conn, Manga manga) {
        String sql = "INSERT OR REPLACE INTO manga(id, serie_id, proprio_id, numero, etat, loue, added_time, nb_location) VALUES(?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, manga.getID());
            pstmt.setInt(2, manga.getSerie().getID());
            pstmt.setInt(3, manga.getProprio().getID());
            pstmt.setInt(4, manga.getNumero());
            pstmt.setString(5, manga.getEtat());
            if (manga.isLoue()) pstmt.setInt(6, 1);
            else pstmt.setInt(6, 0);
            pstmt.setLong(7, Utils.localDateTimeToLong(manga.getAddedTime()));
            pstmt.setInt(8, manga.getNbLocation());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean insertLastLecture(Connection conn, LastLecture lastLecture) {
        String sql = "INSERT OR REPLACE INTO lastLecture(serie_id, person_id, numero, time) VALUES(?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lastLecture.getSerie().getID());
            pstmt.setInt(2, lastLecture.getPerson().getID());
            pstmt.setInt(3, lastLecture.getNumero());
            pstmt.setLong(4, Utils.localDateTimeToLong(lastLecture.getTime()));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean insertTransaction(Connection conn, Transaction transaction) {
        String sql = "INSERT OR REPLACE INTO transaction_table(date, montant, type, person) VALUES(?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, Utils.localDateTimeToLong(transaction.getTime()));
            pstmt.setDouble(2, transaction.getMontant());
            pstmt.setString(3, transaction.getType());
            pstmt.setInt(4, transaction.getPerson().getID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static boolean insertPret(Connection conn, Pret pret) {
        String sql = "INSERT OR REPLACE INTO pret(manga_id, person_id, end_time, rappel_sent) VALUES(?,?,?,?)";

        try{
            //System.out.println("Begin insertion");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pret.getTome().getID());
            pstmt.setInt(2, pret.getPerson().getID());
            pstmt.setLong(3, Utils.localDateToLong(pret.getEndDate()));
            pstmt.setInt(4, pret.getRappelSent());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static int insertTag(Connection conn, Tag tag) {
        String sql = "INSERT OR REPLACE INTO pret(tag_name, serie_id) VALUES(?,?)";

        Serie[] series = tag.getSeries();
        int failure = 0;
        for (Serie serie : series) {
            try{
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tag.getName());
                pstmt.setInt(2, serie.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                failure ++;
            }
        }
        return failure;
    }


    public static boolean insertTag(Connection conn, String tag, Serie serie) {
        String sql = "INSERT OR REPLACE INTO pret(tag_name, serie_id) VALUES(?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tag);
            pstmt.setInt(2, serie.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static ResultSet getAllSerie(Connection conn) {
        String sql = "SELECT id, name, ended, last_published, last_possessed FROM series ORDER BY id ASC";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getPersonLoaning(Connection connection) {
        String sql = "SELECT DISTINCT proprio_id AS id FROM manga WHERE proprio_id != 0;";
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getMangaFromPerson(Connection connection, int person_id) {
        String sql = "SELECT id FROM manga WHERE proprio_id = " + person_id + ";";
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getAllManga(Connection conn) {
        String sql = "SELECT id, numero, serie_id, loue, etat, added_time, nb_location, proprio_id FROM manga ORDER BY id ASC";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getMangaFromSerie(Connection conn, int serie_id) {
        String sql = "SELECT numero FROM manga WHERE serie_id = " + serie_id + " ORDER BY numero ASC;";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }



    public static ResultSet getAllPerson(Connection conn) {
        String sql = "SELECT id, nom, prenom, mail, gsm, address, last_activity, caution, credit FROM person ORDER BY id ASC";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getAllLastLecture(Connection conn) {
        String sql = "SELECT serie_id, person_id, time, numero FROM lastLecture";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getAllTransaction(Connection conn) {
        String sql = "SELECT date, montant, type, person FROM transaction_table ORDER BY date ASC";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getAllPret(Connection conn) {
        String sql = "SELECT manga_id, person_id, end_time, rappel_sent FROM pret ORDER BY end_time ASC";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getAllTag(Connection conn) {
        String sql = "SELECT tag_name, serie_id FROM tag";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean updateSerie(Connection connection, Serie serie) {
        String sql = "UPDATE series SET name = ?, last_possessed = ?, last_published = ?, ended = ? WHERE id = ?;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, serie.getNom());
            pstmt.setInt(2, serie.getLastPossessed());
            pstmt.setInt(3, serie.getLastPublished());
            if (serie.isEnded()) pstmt.setInt(4, 1);
            else pstmt.setInt(4, 0);
            pstmt.setInt(5, serie.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updatePerson(Connection connection, Person person) {
        String sql = "UPDATE person SET nom = ?, prenom = ?, mail = ?, gsm = ?, address = ?, last_activity = ?, caution = ?, " +
                "credit = ? WHERE id = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, person.getNom());
            pstmt.setString(2, person.getPrenom());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getGsm());
            pstmt.setString(5, person.getAddress());
            pstmt.setLong(6, Utils.localDateTimeToLong(person.getLastActivity()));
            pstmt.setInt(7, person.getCaution());
            pstmt.setInt(8, person.getCredit());
            pstmt.setInt(9, person.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean updateManga(Connection connection, Manga manga) {
        String sql = "UPDATE manga SET serie_id = ?, proprio_id = ?, numero = ?, nb_location = ?, loue = ?, added_time = ?, etat = ? WHERE id = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, manga.getSerie().getID());
            pstmt.setInt(2, manga.getProprio().getID());
            pstmt.setInt(3, manga.getNumero());
            pstmt.setInt(4, manga.getNbLocation());
            if (manga.isLoue()) pstmt.setInt(5, 1);
            else pstmt.setInt(5, 0);
            pstmt.setLong(6, Utils.localDateTimeToLong(manga.getAddedTime()));
            pstmt.setString(7, manga.getEtat());
            pstmt.setInt(8, manga.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean updatePret(Connection connection, Pret pretRecord) {
        String sql = "UPDATE pret SET end_time = ? , rappel_sent = ? WHERE manga_id = ? AND person_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setLong(1, Utils.localDateToLong(pretRecord.getEndDate()));
            pstmt.setInt(2, pretRecord.getRappelSent());
            pstmt.setInt(3, pretRecord.getTome().getID());
            pstmt.setInt(4, pretRecord.getPerson().getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean removeSerie(Connection connection, Serie serie) {
        String sqlM = "SELECT * FROM manga WHERE serie_id = " + serie.getID() +";";
        String sqlLL = "SELECT * FROM lastLecture WHERE serie_id = " + serie.getID() + ";";

        String sqlDelete = "DELETE FROM series WHERE id = " + serie.getID() + ";";
        try {
            if (connection.createStatement().executeQuery(sqlM).getFetchSize() != 0) return false;
            if (connection.createStatement().executeQuery(sqlLL).getFetchSize() != 0) return false;
            removeSerieFromTags(connection, serie);
            connection.createStatement().execute(sqlDelete);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeManga(Connection connection, Manga manga) {
        String sqlP = "SELECT * FROM pret WHERE manga_id = " + manga.getID() + ";";
        String sqlDelete = "DELETE FROM manga WHERE id = " + manga.getID() + ";";
        try {
            ResultSet rs = connection.createStatement().executeQuery(sqlP);
            if (rs.getFetchSize() != 0) return false;
            connection.createStatement().execute(sqlDelete);
            manga.getSerie().removeTome(manga);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removePerson(Connection connection, Person person) {
        String sqlT = "SELECT * FROM transaction_table WHERE person = " + person.getID();
        String sqlP = "SELECT * FROM pret WHERE person_id = " + person.getID();
        String sqlM = "SELECT * FROM manga WHERE proprio_id = " + person.getID();
        String sqlDelete = "DELETE FROM person WHERE id = " + person.getID();
        try {
            if (connection.createStatement().executeQuery(sqlM).getFetchSize() != 0) return false;
            if (connection.createStatement().executeQuery(sqlT).getFetchSize() != 0) return false;
            if (connection.createStatement().executeQuery(sqlP).getFetchSize() != 0) return false;
            removeLastLecture(connection, person);
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeSerieFromTags(Connection connection, Serie serie){
        String sqlDelete = "DELETE FROM tag WHERE serie_id = " + serie.getID();
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTagByName(Connection connection, String name){
        String sqlDelete = "DELETE FROM tag WHERE tag_name = \"" + name + "\";";
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTag(Connection connection, Serie serie, String name){
        String sqlDelete = "DELETE FROM tag WHERE tag_name = \"" + name + "\" AND serie_id = " + serie.getID();
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTransaction(Connection connection, Transaction transaction) {
        String sqlDelete = "DELETE FROM transaction_table WHERE date = " + Utils.localDateTimeToLong(transaction.getTime())
                + " AND person = " + transaction.getPerson().getID() + " AND montant = " + transaction.getMontant() + "AND type = \""
                + transaction.getType() +"\";";
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTransactionBefore(Connection connection, LocalDateTime dateTime) {
        String sqlDelete = "DELETE FROM transaction_table WHERE date < " + Utils.localDateTimeToLong(dateTime);
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTransactionAfter(Connection connection, LocalDateTime dateTime) {
        String sqlDelete = "DELETE FROM transaction_table WHERE date > " + Utils.localDateTimeToLong(dateTime);
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTransactionBetween(Connection connection, LocalDateTime from, LocalDateTime to) {
        String sqlDelete = "DELETE FROM transaction_table WHERE date <= " + Utils.localDateTimeToLong(to) +
                " AND date >= " + Utils.localDateTimeToLong(from);
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeTransactionBefore(Connection connection, LocalDate date) {
        return removeTransactionBefore(connection, LocalDateTime.of(date, LocalTime.MIDNIGHT));
    }

    public static boolean removeTransactionAfter(Connection connection, LocalDate date) {
        return removeTransactionAfter(connection, LocalDateTime.of(date, LocalTime.MIDNIGHT));
    }

    public static boolean removeTransactionBetween(Connection connection, LocalDate from, LocalDate to) {
        return removeTransactionBetween(connection, LocalDateTime.of(from, LocalTime.MIDNIGHT), LocalDateTime.of(to, LocalTime.MIDNIGHT));
    }

    public static boolean removePret(Connection connection, Pret pret) {
        String sqlDelete = "DELETE FROM pret WHERE manga_id = " + pret.getTome().getID();
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeLastLecture(Connection connection, LastLecture lecture) {
        String sqlDelete = "DELETE FROM lastLecture WHERE serie_id = " + lecture.getSerie().getID()
                + " AND person_id =  " + lecture.getPerson().getID() + " AND numero = " + lecture.getNumero();
        try {
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeLastLecture(Connection connect, Person person) {
        String sqlDelete = "DELETE FROM lastLecture WHERE person_id = " + person.getID();
        try{
            connect.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void removeAllTransactions(Connection connection) {
        String sqlDelete = "DELETE FROM transaction_table";
        try{
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromPret(Connection connection, Manga manga) {
        String sqlDelete = "DELETE FROM pret WHERE manga_id = " + manga.getID();
        try{
            connection.createStatement().execute(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
