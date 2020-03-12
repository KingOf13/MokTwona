import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyCaution {
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JTextPane cautionPanel;
    private JComboBox selectBox;
    private JButton fiveButton;
    private JButton tenButton;
    private JButton confirmerButton;
    private JFrame frame;
    private final String[] exName = {"Pierre", "Paul", "Jean", "Jacques"};
    private final String[] addOrRemove = {"Ajouter", "Retirer"};
    private int[] exCaution = {10, 5 , 0, 5};
    private int montant = 0;
    private boolean notSet = true;

    public ModifyCaution(JFrame frame) {
        this.frame = frame;
        ComboBoxModel model1 = new DefaultComboBoxModel(addOrRemove);
        ComboBoxModel model2 = new DefaultComboBoxModel(exName);
        selectBox.setModel(model1);
        clientBox.setModel(model2);
        selectBox.setSelectedIndex(-1);
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = clientBox.getSelectedIndex();
                cautionPanel.setText(exName[idx] + " a une caution de " + exCaution[idx] + "€");
            }
        });
        fiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                montant = 5;
                if (clientBox.getSelectedIndex() != -1 && selectBox.getSelectedIndex() != -1) {
                    cautionPanel.setText(addOrRemove[selectBox.getSelectedIndex()] + " " + montant + "€ à " + exName[clientBox.getSelectedIndex()] + " ?");
                    notSet = false;
                }
                else {
                    cautionPanel.setText("Choisir un client et une action PUIS le montant");
                }

            }
        });
        tenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                montant = 10;
                if (clientBox.getSelectedIndex() != -1 && selectBox.getSelectedIndex() != -1) {
                    cautionPanel.setText(addOrRemove[selectBox.getSelectedIndex()] + " " + montant + "€ à " + exName[clientBox.getSelectedIndex()] + " ?");
                    notSet = false;
                }
                else {
                    cautionPanel.setText("Choisir un client et une action PUIS le montant");
                }

            }
        });
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientBox.getSelectedIndex() == -1 || selectBox.getSelectedIndex() == -1 || montant == 0 || notSet) {
                    String toPrint = "";
                    if (clientBox.getSelectedIndex() == -1) toPrint += "Sélectionner un client dans la base de donnée\n";
                    if (selectBox.getSelectedIndex() == -1) toPrint += "Sélectionner une action\n";
                    if (montant == 0) toPrint += "Sélectionner un montant";
                }
                else {
                    System.out.println(addOrRemove[selectBox.getSelectedIndex()] + " " + montant + "€ à " + exName[clientBox.getSelectedIndex()]);
                    frame.dispose();
                }
            }
        });
    }

}
