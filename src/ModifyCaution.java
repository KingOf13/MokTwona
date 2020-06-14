import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ModifyCaution {
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JTextPane cautionPanel;
    private JComboBox selectBox;
    private JButton fiveButton;
    private JButton tenButton;
    private JButton confirmerButton;
    private JFrame frame;
    private final String[] addOrRemove = {"Ajouter", "Retirer"};
    private int montant = 0;
    private boolean notSet = true;
    private int idxClient = -1;
    private int idxSelect = -1;
    private Person[] people;

    public ModifyCaution(JFrame frame) {
        this.frame = frame;
        people = MokTwona.db.getPeople();
        ComboBoxModel model1 = new DefaultComboBoxModel(addOrRemove);
        ComboBoxModel model2 = new DefaultComboBoxModel(people);
        selectBox.setModel(model1);
        clientBox.setModel(model2);
        selectBox.setSelectedIndex(-1);
        clientBox.setSelectedIndex(-1);
        refreshPanel();
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idxClient = clientBox.getSelectedIndex();
                refreshPanel();
            }
        });
        selectBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idxSelect = selectBox.getSelectedIndex();
                refreshPanel();
            }
        });
        fiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                montant = 5;
                refreshPanel();
            }
        });
        tenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                montant = 10;
                refreshPanel();
            }
        });
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idxClient == -1 || idxSelect == -1 || montant == 0) {
                    refreshPanel();
                }
                else {
                    if (idxSelect == 0) { // Ajouter
                        if ((montant + people[idxClient].getCaution()) <= 10) {
                            MokTwona.db.add(new Transaction(LocalDateTime.now(), montant, "Ajout de caution", people[idxClient]));
                            people[idxClient].setCaution(montant + people[idxClient].getCaution());
                            frame.dispose();
                        }
                        else cautionPanel.setText("Action non permise - changez les données");
                    }
                    else {
                        if ((people[idxClient].getCaution() - montant) >= 0) {
                            MokTwona.db.add(new Transaction(LocalDateTime.now(), -montant, "Retrait de caution", people[idxClient]));
                            people[idxClient].setCaution(people[idxClient].getCaution() - montant);
                            frame.dispose();
                        }
                        else cautionPanel.setText("Action non permise - changez les données");
                    }
                }
            }
        });
    }

    public void refreshPanel() {
        if (idxClient == -1 || idxSelect == -1 || montant == 0) {
            String toPrint = "";
            if (idxClient == -1) toPrint += "Sélectionner un client dans la base de donnée\n";
            else toPrint += people[idxClient] + " a une caution de " + people[idxClient].getCaution() + "€\n";
            if (idxSelect == -1) toPrint += "Sélectionner une action\n";
            if (montant == 0) toPrint += "Sélectionner un montant";
            cautionPanel.setText(toPrint);
        }
        else {
            cautionPanel.setText(addOrRemove[idxSelect] + " " + montant + "€ à " + people[idxClient] + " ?");
        }
    }

}
