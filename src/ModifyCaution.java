import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ModifyCaution {
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JTextPane cautionPanel;
    private JButton zeroButton;
    private JButton fiveButton;
    private JButton tenButton;
    private JButton confirmerButton;
    private JFrame frame;
    private int montant = -1;
    private boolean notSet = true;
    private int idxClient = -1;
    private Person[] people;

    public ModifyCaution(JFrame frame) {
        this.frame = frame;
        people = MokTwona.db.getPeople();
        ComboBoxModel model2 = new DefaultComboBoxModel(people);
        clientBox.setModel(model2);
        clientBox.setSelectedIndex(-1);
        refreshPanel();
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idxClient = clientBox.getSelectedIndex();
                refreshPanel();
            }
        });
        zeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                montant = 0;
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
                if (idxClient == -1 || montant == -1) {
                    refreshPanel();
                }
                else {
                    int toPay = montant-people[idxClient].getCaution();
                    if (toPay == 0) {
                        if (Utils.confirmationDialog("Ne pas modifier la caution de " + people[idxClient] + " ?")) frame.dispose();
                    }
                    else if (toPay > 0) {
                        if (!Utils.confirmationDialog("Mettre la caution de " + people[idxClient] + " à " + montant +  "€ ?")) return;
                        MokTwona.db.add(new Transaction(LocalDateTime.now(), toPay, "Ajout de caution", people[idxClient]));
                        people[idxClient].setCaution(montant);
                        frame.dispose();
                    }
                    else {
                        if (!Utils.confirmationDialog("Mettre la caution de " + people[idxClient] + " à " + montant +  "€ ?")) return;
                        MokTwona.db.add(new Transaction(LocalDateTime.now(), toPay, "Retrait de caution", people[idxClient]));
                        people[idxClient].setCaution(montant);
                        frame.dispose();

                    }
                    /*if (idxSelect == 0) { // Ajouter
                        if ((montant + people[idxClient].getCaution()) <= 10) {

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
                    }*/
                }
            }
        });
    }

    public void refreshPanel() {
        if (idxClient == -1 || montant == -1) {
            String toPrint = "";
            if (idxClient == -1) toPrint += "Sélectionner un client dans la base de donnée\n";
            else toPrint += people[idxClient] + " a une caution de " + people[idxClient].getCaution() + "€\n";
            if (montant == 0) toPrint += "Sélectionner un montant";
            cautionPanel.setText(toPrint);
        }
        else {
            cautionPanel.setText("Mettre la caution de " + people[idxClient] + " (actuellement " + people[idxClient].getCaution() + "€) à " + montant + "€ ?");
        }
    }

}
