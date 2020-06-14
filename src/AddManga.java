import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AddManga {
    private JComboBox mangaBox;
    private JTextPane seriePane;
    private JComboBox proprioBox;
    private JTextField numeroField;
    private JTextField stateField;
    public JPanel rootPanel;
    private JButton ajouterButton;
    private JTextPane resumePane;
    private JButton resumeButton;
    private JTextPane numéroTextPane;
    private JTextPane étatTextPane;
    private JFrame frame;
    private Serie[] series;
    private Person[] people;

    private int mangaIdx = -1;
    private int numero = -1;

    public AddManga(JFrame frame) {
        this.frame = frame;
        series = MokTwona.db.getSeries();
        people = MokTwona.db.getPeople();

        ajouterButton.setEnabled(false);

        mangaBox.setModel(new DefaultComboBoxModel(series));
        mangaBox.setSelectedIndex(-1);
        mangaBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mangaIdx = mangaBox.getSelectedIndex();
                seriePane.setText("Série : " + series[mangaIdx].getNom() + "\nDernier numéro possédé : "
                + series[mangaIdx].getLastPossessed() + "\nDernier publié : " + series[mangaIdx].getLastPublished());
            }
        });

        proprioBox.setModel(new DefaultComboBoxModel(people));
        proprioBox.setSelectedIndex(-1);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mangaIdx == -1) return;
                if (proprioBox.getSelectedIndex() == -1) return;
                if (numeroField.getText().trim().equals("")) return;
                if (stateField.getText().trim().equals("")) return;
                try {
                    numero = Integer.parseInt(numeroField.getText().trim());
                    String state = stateField.getText().trim();
                    resumePane.setText("Ajouter le tome " + numero + " à la série " + series[mangaIdx].getNom()
                    + " possédé par " + people[proprioBox.getSelectedIndex()] + "?\nÉtat : " + state);
                    ajouterButton.setEnabled(true);
                }
                catch (NumberFormatException e1) {
                    resumePane.setText("Entrer un numero de tome valide dans le champ !");
                }
            }
        });

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manga manga = new Manga(series[mangaIdx], numero, people[proprioBox.getSelectedIndex()], LocalDateTime.now(),
                        stateField.getText().trim(), false, 0);

                frame.dispose();
            }
        });
    }
}
