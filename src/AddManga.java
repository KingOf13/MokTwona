import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private int mangaIdx = -1;
    private int numero = -1;

    public AddManga(JFrame frame) {
        this.frame = frame;

        ajouterButton.setEnabled(false);

        mangaBox.setModel(new DefaultComboBoxModel(Example.exManga));
        mangaBox.setSelectedIndex(-1);
        mangaBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mangaIdx = mangaBox.getSelectedIndex();
                seriePane.setText("Série : " + Example.exManga[mangaIdx] + "\nDernier numéro possédé : "
                + Example.exLastPossessed[mangaIdx] + "\nDernier publié : " + Example.exLastPublished[mangaIdx]);
            }
        });

        proprioBox.setModel(new DefaultComboBoxModel(Example.exName));
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
                    resumePane.setText("Ajouter le tome " + numero + " à la série " + Example.exManga[mangaIdx]
                    + " possédé par " + Example.exName[proprioBox.getSelectedIndex()] + "?\nÉtat : " + state);
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
                frame.dispose();    
            }
        });
    }
}
