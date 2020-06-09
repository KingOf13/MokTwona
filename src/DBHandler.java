import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class DBHandler {
    private static String url = "jdbc:sqlite:./mokona.db";
    private static int missingTomes = -1;
    private static int seriesWithBlank = -1;

    public static void connectSeriesTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS series (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
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
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
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
                + "    etat text DEFAULT \"Bon\"\n"
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
                + "    time integer NOT NULL DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'localtime'))\n"
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
                + "    end_time integer NOT NULL DEFAULT (DATE(CURRENT_TIMESTAMP, 'localtime', '+14 day'))\n"
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
                + "    serie_id integer NOT NULL\n"
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
        String sql = "CREATE TABLE IF NOT EXISTS transaction (\n"
                + "    montant integer NOT NULL,\n"
                + "    person integer NOT NULL,\n"
                + "    type text NOT NULL,\n"
                + "    date integer NOT NULL DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'localtime'))\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void insertSeries(Connection conn, String name, String id) { insertSeries(conn, new Serie(name, 1, 1, false));}


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
        String sql = "INSERT OR REPLACE INTO people(id, nom, prenom, mail, gsm, address, last_activity, caution, credit) VALUES(?,?,?,?,?,?,?,?,?)";

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
            pstmt.setLong(7, manga.getAddedTime().toEpochSecond(ZoneOffset.of("-1")));
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
            pstmt.setInt(1, lastLecture.getManga().getSerie().getID());
            pstmt.setInt(2, lastLecture.getPerson().getID());
            pstmt.setInt(3, lastLecture.getManga().getNumero());
            pstmt.setLong(4, lastLecture.getTime().toEpochSecond(ZoneOffset.of("-1")));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean insertTransaction(Connection conn, Transaction transaction) {
        String sql = "INSERT OR REPLACE INTO transaction(date, montant, type, person) VALUES(?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, transaction.getTime().toEpochSecond(ZoneOffset.of("-1")));
            pstmt.setInt(2, transaction.getMontant());
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
        String sql = "INSERT OR REPLACE INTO pret(manga_id, person_id, end_time) VALUES(?,?,?)";

        try{
            //System.out.println("Begin insertion");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pret.getTome().getID());
            pstmt.setInt(2, pret.getPerson().getID());
            pstmt.setLong(3, pret.getEndDate().toEpochDay());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static int insertTag(Connection conn, Tag tag) {
        String sql = "INSERT OR REPLACE INTO pret(tag_name, serie_id) VALUES(?,?)";

        ArrayList<Serie> series = tag.getSeries();
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
        String sql = "SELECT date, montant, type, person FROM transaction ORDER BY date ASC";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getAllPret(Connection conn) {
        String sql = "SELECT manga_id, person_id, end_time FROM pret ORDER BY end_time ASC";
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

    /*public static void extractSeries(Connection seriesConnection) {
        System.out.println("Starting extracting series ...");
        int processed = 0;
        try {
            File file = new File("./T_SERIES.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            //System.out.println("Root element : " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("T_SERIES");
            processed = nodeList.getLength();
            connectSeriesTable(seriesConnection);
            if (seriesConnection == null) { System.out.println("Connection is null");}
            for (int itr = 0; itr < nodeList.getLength(); itr++) //nodeList.getLength()
            //for (int itr = 0; itr < 20; itr++) //nodeList.getLength()
            {
                if (itr > 0 && itr%100 == 0) System.out.println("Series iteration n°" + itr);
                Node node = nodeList.item(itr);
                //System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    //System.out.println("Titre série: "+ eElement.getElementsByTagName("SER_TITRE").item(0).getTextContent());
                    //System.out.println("ID: "+ eElement.getElementsByTagName("SER_PK").item(0).getTextContent());
                    insertSeries(seriesConnection, eElement.getElementsByTagName("SER_TITRE").item(0).getTextContent(),
                            eElement.getElementsByTagName("SER_PK").item(0).getTextContent());
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.println("Ended extraction of " + processed + " series");

    }

    public static void extractPeople(Connection peopleConnection) {
        System.out.println("Starting extracting people ...");
        int processed = 0;
        try {
            File file = new File("./T_TIERS.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            //System.out.println("Root element : " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("T_TIERS");
            processed = nodeList.getLength();
            connectPersonTable(peopleConnection);
            if (peopleConnection == null) { System.out.println("Connection is null");}
            for (int itr = 0; itr < nodeList.getLength(); itr++) //nodeList.getLength()
            //for (int itr = 0; itr < 20; itr++) //nodeList.getLength()
            {
                if (itr > 0 && itr%100 == 0) System.out.println("People iteration n°" + itr);
                Node node = nodeList.item(itr);
                //System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    //System.out.println("Titre série: "+ eElement.getElementsByTagName("SER_TITRE").item(0).getTextContent());
                    //System.out.println("ID: "+ eElement.getElementsByTagName("SER_PK").item(0).getTextContent());
                    insertPeople(peopleConnection, (eElement.getElementsByTagName("TIER_NOM").item(0).getTextContent()
                                    + " " +  eElement.getElementsByTagName("TIER_PRENOM").item(0).getTextContent()).trim(),
                            eElement.getElementsByTagName("TIER_PK").item(0).getTextContent());
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.println("Ended extraction of " + processed + " people");

    }

    public static void extractManga(Connection mangaConnection) {
        System.out.println("Starting extracting mangas ...");
        int processed = 0;
        int deleted = 0;
        try {
            File file = new File("./T_MANGAS.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            //System.out.println("Root element : " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("T_MANGAS");
            processed = nodeList.getLength();
            connectMangaTable(mangaConnection);
            if (mangaConnection == null) { System.out.println("Connection is null");}
            for (int itr = 0; itr < nodeList.getLength(); itr++) //nodeList.getLength()
            //for (int itr = 0; itr < 20; itr++) //nodeList.getLength()
            {
                if (itr > 0 && itr%100 == 0) System.out.println("Manga iteration n°" + itr);
                Node node = nodeList.item(itr);
                //System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    //System.out.println("Titre série: "+ eElement.getElementsByTagName("SER_TITRE").item(0).getTextContent());
                    //System.out.println("ID: "+ eElement.getElementsByTagName("SER_PK").item(0).getTextContent());
                    if (Integer.parseInt(eElement.getElementsByTagName("MAN_DELETED").item(0).getTextContent()) == 0) {
                        insertManga(mangaConnection, eElement.getElementsByTagName("MAN_PK").item(0).getTextContent(),
                                eElement.getElementsByTagName("MAN_SER_FK").item(0).getTextContent(),
                                Integer.parseInt(eElement.getElementsByTagName("MAN_NUMERO").item(0).getTextContent()),
                                eElement.getElementsByTagName("MAN_PROPRIO_FK").item(0).getTextContent(),
                                eElement.getElementsByTagName("MAN_ETAT").item(0).getTextContent());
                    }
                    else deleted++;
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.println("Ended extraction of " + (processed-deleted) + " mangas, " + deleted + " deleted");

    }

    public static void updateSeries(Connection connection) {
        String sql = "SELECT id,name FROM series";
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            int treated = 0;
            int deleted = 0;
            while (rs.next()) {
                treated ++;
                if (treated > 0 && (treated % 100 == 0)) System.out.println("Series updated : " + treated);
                String id = rs.getString("id");
                String sqlManga = "SELECT * FROM manga WHERE manga_id = \"" + id + "\"";
                ResultSet rs2 = connection.createStatement().executeQuery(sqlManga);
                int size = 0;
                int maxVolume = 0;
                while (rs2.next()) {
                    size++;
                    if (rs2.getInt("numero") > maxVolume) maxVolume = rs2.getInt("numero");
                }
                if (size == 0) {
                    deleted++;
                    String sqlDelete = "DELETE FROM series WHERE id = \"" + id + "\"";
                    connection.createStatement().execute(sqlDelete);
                    System.out.println("Serie deleted, name : " + rs.getString("name"));
                }
                else {
                    String sqlUpdate = "UPDATE series SET last_published = " + maxVolume + " WHERE id = \"" + id + "\"";
                    connection.createStatement().execute(sqlUpdate);
                }
            }
            System.out.println("Deleted " + deleted + " series with no volume in the library");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void scanToFindBlank(Connection connection){
        System.out.println("Start scanning to find blank ...");
        String sql = "SELECT id, name, last_published, ended FROM series ORDER BY name ASC";
        BufferedWriter writer = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        int notPossessed = 0;
        seriesWithBlank = 0;
        try {
            writer = new BufferedWriter(new FileWriter("missings.txt"));
            rs = connection.createStatement().executeQuery(sql);
            int treated = 0;
            while (rs.next()) {
                treated ++;
                if (treated > 0 && (treated % 100 == 0)) System.out.println("Series scanned : " + treated);
                String id = "\"" + rs.getString("id") + "\"";
                int maxVolume = rs.getInt("last_published");
                boolean possessed[] = new boolean[maxVolume];
                for (int i = 0; i < maxVolume; i++) possessed[i] = false;
                String sqlManga = "SELECT numero FROM manga WHERE manga_id = " + id;
                rs2 = connection.createStatement().executeQuery(sqlManga);
                while (rs2.next()) {
                    int idx = rs2.getInt("numero");
                    if (idx > 0) possessed[idx - 1] = true;
                }
                String toPrint = rs.getString("name");
                if (rs.getBoolean("ended")) toPrint += " (TERMINE)";
                toPrint += " :";
                int missing = 0;
                for (int i = 0; i < maxVolume; i ++) {
                    if (!possessed[i]) {
                        notPossessed++;
                        missing++;
                        if (missing == 1) toPrint += " " + (i+1);
                        else toPrint += ", " + (i+1);
                    }
                }
                toPrint += "\n";
                if (missing > 0) {
                    writer.write(toPrint);
                    seriesWithBlank++;
                }
            }
            writer.write("\n======= Mangas manquants : " + notPossessed + " =======\n");
            writer.write("\n======= Séries avec trous : " + seriesWithBlank + " =======\n");
            writer.close();
            System.out.println("Ended scanning, found " + notPossessed + " blank in " + seriesWithBlank + " series (" + toPercent(seriesWithBlank,treated) +"%)");
            missingTomes = notPossessed;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println(rs.getString("name") + " : " + rs2.getInt("numero"));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void scanForLoaned(Connection connection) {
        System.out.println("Starting to scan for loaned ...");
        String sqlSeries = "SELECT id, name FROM series";
        String sqlManga1 = "SELECT numero, proprio_id FROM manga WHERE manga_id = ";
        String sqlManga2 = " ORDER BY numero ASC";
        String sqlKotmanga = "SELECT id  FROM people WHERE name = \"Kotmanga\"";
        String sqlProprio = "SELECT name FROM people WHERE id = ";
        String sqlSynthesis = "SELECT count(numero) AS nombre, proprio_name AS name FROM extManga GROUP BY proprio_name";

        ResultSet rsSeries = null;
        ResultSet rsManga = null;

        String kotmangaID = null;

        BufferedWriter writer = null;

        int notPossessed = 0;

        try {
            writer = new BufferedWriter(new FileWriter("loan.txt"));
            kotmangaID = connection.createStatement().executeQuery(sqlKotmanga).getString("id");
            rsSeries = connection.createStatement().executeQuery(sqlSeries);
            while (rsSeries.next()) {
                String id = "\"" + rsSeries.getString("id") + "\"";
                rsManga = connection.createStatement().executeQuery(sqlManga1+id+sqlManga2);
                boolean first = true;
                while (rsManga.next()) {
                    if (!rsManga.getString("proprio_id").equals(kotmangaID)){
                        notPossessed++;
                        if (notPossessed % 100 == 0) System.out.println("Found " + notPossessed + " mangas not owned by Kotmanga already");
                        if (first) {
                            writer.write("Tomes prêtés pour le manga " + rsSeries.getString("name") + " :\n");
                            first = false;
                        }
                        String proprioID = "\"" + rsManga.getString("proprio_id") + "\"";
                        String proprioName = connection.createStatement().executeQuery(sqlProprio + proprioID).getString("name");
                        insertExtManga(connection, rsSeries.getString("name"), proprioName, rsManga.getInt("numero"));
                        writer.write("Numéro " + rsManga.getInt("numero") + " par " +proprioName + "\n");
                    }
                }
            }

            ResultSet rsSynthesis = connection.createStatement().executeQuery(sqlSynthesis);

            writer.write("\n======= Résumé des prêts =======\n");
            while (rsSynthesis.next()) {
                String toWrite = null;
                if (rsSynthesis.getInt("nombre")>1) toWrite = rsSynthesis.getInt("nombre") + " tomes au kot.\n";
                else  toWrite = rsSynthesis.getInt("nombre") + " tome au kot.\n";
                writer.write(rsSynthesis.getString("name") + " a prêté " + toWrite);
            }

            writer.write("\n======= Mangas en prêt : " + notPossessed + " =======\n");
            writer.close();
            System.out.println("Ended scanning, found " + notPossessed + " loaned.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void peopleWhoLoaned(Connection connection) {
        String sql = "SELECT DISTINCT proprio_name AS name FROM extManga;";
        ResultSet rs = null;
        BufferedWriter writer = null;
        try {
            rs = connection.createStatement().executeQuery(sql);
            writer = new BufferedWriter(new FileWriter("peopleWhoLoaned.txt"));
            while (rs.next()) {
                writer.write(rs.getString("name") + "\n");
            }
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double toPercent(int a, int b) {
        double percentage = (int)((((double) a)/b)*10000);
        percentage /= 100.0;
        return percentage;
    }

    public static double toPercent(int a, int b, int nbDec) {
        double factor = Math.pow(10,nbDec);
        double percentage = (int)((((double) a)/b)*100*factor);
        percentage /= factor;
        return percentage;
    }

    public static void extractLoanBy(Connection connection, String name) {
        String sql = "SELECT manga_name AS name, numero FROM extManga WHERE proprio_name = \"" + name + "\" ORDER BY name, numero ASC;";
        ResultSet rs = null;
        BufferedWriter writer = null;
        String manga = null;
        boolean first = true;
        boolean firstTome = true;
        try {
            rs = connection.createStatement().executeQuery(sql);
            writer = new BufferedWriter(new FileWriter("loanedBy" + name.replace(" ", "") + ".txt"));
            while (rs.next()) {
                String mangaTemp = rs.getString("name");
                if (!mangaTemp.equals(manga)) {
                    if (!first) writer.write('\n');
                    manga = mangaTemp;
                    writer.write(manga + " : ");
                    first = false;
                    firstTome = true;
                }
                if (!firstTome) writer.write(", ");
                writer.write(rs.getInt("numero") + "");
                firstTome = false;
            }
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stats(Connection connection) {
        try {
            int nbSeries = connection.createStatement().executeQuery("SELECT count(*) AS size FROM series").getInt("size");
            int nbTome = connection.createStatement().executeQuery("SELECT count(*) AS size FROM manga").getInt("size");
            int nbClient = connection.createStatement().executeQuery("SELECT count(*) AS size FROM people").getInt("size");
            int nbLoaned = connection.createStatement().executeQuery("SELECT count(*) AS size FROM extManga").getInt("size");
            int nbLoaner = connection.createStatement().executeQuery("SELECT count(DISTINCT proprio_name) AS size FROM extManga").getInt("size");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connection = null;

        boolean resetDB = false;
        boolean scanForBlank = false;
        boolean scanForLocation = false;

        boolean repondu = false;

        while (!repondu) {
            System.out.println("Voulez-vous remettre à jour la base de donnée ? [oui/non]");

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("oui")) {
                resetDB = true;
                repondu = true;
            }
            else if (line.equalsIgnoreCase("non")) {
                resetDB = false;
                repondu = true;
            }
            else System.out.println("Répondre par oui ou non !");
        }

        repondu = false;

        while (!repondu) {
            System.out.println("Voulez-vous chercher les trous dans les séries ? [oui/non]");

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("oui")) {
                scanForBlank = true;
                repondu = true;
            }
            else if (line.equalsIgnoreCase("non")) {
                scanForBlank = false;
                repondu = true;
            }
            else System.out.println("Répondre par oui ou non !");
        }

        repondu = false;

        while (!repondu) {
            System.out.println("Voulez-vous chercher pour les mangas en prêt au kot par des membres ? [oui/non]");

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("oui")) {
                scanForLocation = true;
                repondu = true;
            }
            else if (line.equalsIgnoreCase("non")) {
                scanForLocation = false;
                repondu = true;
            }
            else System.out.println("Répondre par oui ou non !");
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            if (connection == null) {
                System.err.println("No connection to DB");
                System.exit(-1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (scanForLocation) connectExtMangaTable(connection);

        if (resetDB) {
            extractSeries(connection);
            extractPeople(connection);
            extractManga(connection);
            updateSeries(connection);
        }

        if (scanForBlank) {
            scanToFindBlank(connection);
            System.out.println("Mangas manquants dans missing.txt");
        }

        if (scanForLocation) {
            scanForLoaned(connection);
            System.out.println("Mangas prêté dans loan.txt");
            peopleWhoLoaned(connection);
        }

        extractLoanBy(connection, "Leo Durvaux");

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/
}
