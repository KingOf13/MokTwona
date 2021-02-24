import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;

public class FindMissingManga {
    private JButton scanButton;
    private JTextField fileNameField;
    private JTextPane nomDuFichierDansTextPane;
    public JPanel rootPanel;
    private JFrame frame;
    private String filename = "missing.txt";


    private static int missingTomes = -1;
    private static int seriesWithBlank = -1;


    public FindMissingManga(JFrame frame) {
        this.frame = frame;

        fileNameField.setText("missing_tomes_"+ LocalDate.now().toString().replace('-', '_') + ".txt");

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filename = fileNameField.getText().trim();
                if (scanToFindBlank()) frame.dispose();
            }
        });
    }


    public boolean scanToFindBlank(){
        System.out.println("Start scanning to find blank ...");
        String sql = "SELECT id, name, last_published, ended FROM series ORDER BY name ASC";
        BufferedWriter writer = null;
        ResultSet rs2 = null;
        int notPossessed = 0;
        seriesWithBlank = 0;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            int treated = 0;
            for (Serie s : MokTwona.db.getSeries()) {
                treated++;
                int maxVolume = s.getLastPublished();
                boolean possessed[] = MokTwona.db.findBlanks(s);
                String toPrint = s.getNom();
                if (s.isEnded()) toPrint += " (TERMINE)";
                toPrint += " :";
                int missing = 0;
                for (int i = 0; i < maxVolume; i++) {
                    if (!possessed[i]) {
                        notPossessed++;
                        missing++;
                        if (missing == 1) toPrint += " " + (i + 1);
                        else toPrint += ", " + (i + 1);
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
            Utils.informationDialog("Scan terminé, il manque " + notPossessed + " tomes répartis dans " + seriesWithBlank + " séries (" + Utils.toPercent(seriesWithBlank, treated) + "%)");
            missingTomes = notPossessed;
        }
        catch (IOException e) {
            e.printStackTrace();
            Utils.informationDialog("Une erreur est arrivée pendant la création du fichier !");
            return false;
        }
        return true;
    }
}
