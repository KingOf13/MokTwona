import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PossessedManga {
    private JTextField fileNameField;
    private JButton scanButton;
    public JPanel rootPanel;
    private JFrame frame;
    private String filename = "mangas.txt";

    public PossessedManga(JFrame frame) {
        this.frame = frame;
        fileNameField.setText("mangas_"+ LocalDate.now().toString().replace('-', '_') + ".txt");

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filename = fileNameField.getText().trim();
                if (findPossessed()) frame.dispose();
            }
        });
    }

    private boolean findPossessed(){
        Serie[] series = MokTwona.db.getSeries();
        Arrays.sort(series);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(filename));
            for(Serie serie: series) {
                ArrayList<Integer> tomes = MokTwona.db.getTomesFromSerie(serie);
                writer.write(serie.getNom() + (serie.isEnded()? " (TERMINÉ) : ":": "));
                boolean first = true;
                Iterator<Integer> ite = tomes.iterator();
                while (ite.hasNext()){
                    Integer numero = ite.next();
                    if (first) {
                        writer.write(numero.toString());
                        first = false;
                    }
                    else writer.write(", " + numero.toString());
                }
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
