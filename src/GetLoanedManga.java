import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class GetLoanedManga {
    private JButton scanButton;
    private JTextField fileNameField;
    public JPanel rootPanel;
    private JFrame frame;
    private String filename = "prete_au_kot.txt";

    public GetLoanedManga(JFrame frame) {
        this.frame = frame;
        fileNameField.setText("prete_au_kot_"+ LocalDate.now().toString().replace('-', '_') + ".txt");

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filename = fileNameField.getText().trim();
                if (scanToFindLoaned()) frame.dispose();
            }
        });
    }

    private boolean scanToFindLoaned() {
        ArrayList<Person> loaners = MokTwona.db.getLoaners();
        loaners.sort(Person::compareTo);
        BufferedWriter writer = null;
        int loaned = 0;
        int count = 0;

        try {
            writer = new BufferedWriter(new FileWriter(filename));
            Iterator<Person> ite = loaners.iterator();
            while (ite.hasNext()) {
                Person person = ite.next();
                Serie currSerie = null;
                ArrayList<Manga> mangas = MokTwona.db.getLoanedByPerson(person);
                writer.write("----- " + person.toString() + " -----");
                mangas.sort(Manga::compareTo);

                Iterator<Manga> mangaIte = mangas.iterator();
                while (mangaIte.hasNext()) {
                    Manga manga = mangaIte.next();
                    if (manga.equals(currSerie)) {
                        writer.write(", " + manga.getNumero());
                    }
                    else {
                        currSerie = manga.getSerie();
                        writer.write("\n" + currSerie.getNom() + " : " + manga.getNumero());
                    }
                    loaned ++;
                }

                count ++;

            }
            writer.write("\n##### RESUME #####\n" + loaned + " manga(s) en prêt au kot par " + count + " personnes.");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
