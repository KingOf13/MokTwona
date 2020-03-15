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
    private final String[] addOrRemove = {"Ajouter", "Retirer"};
    private int montant = 0;
    private boolean notSet = true;
    private int idxClient = -1;
    private int idxSelect = -1;

    public ModifyCaution(JFrame frame) {
        this.frame = frame;
        ComboBoxModel model1 = new DefaultComboBoxModel(addOrRemove);
        ComboBoxModel model2 = new DefaultComboBoxModel(Example.exName);
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
                    System.out.println(addOrRemove[idxSelect] + " " + montant + "€ à " + Example.exName[idxClient]);
                    frame.dispose();
                }
            }
        });
    }

    public void refreshPanel() {
        if (idxClient == -1 || idxSelect == -1 || montant == 0) {
            String toPrint = "";
            if (idxClient == -1) toPrint += "Sélectionner un client dans la base de donnée\n";
            else toPrint += Example.exName[idxClient] + " a une caution de " + Example.exCaution[idxClient] + "€\n";
            if (idxSelect == -1) toPrint += "Sélectionner une action\n";
            if (montant == 0) toPrint += "Sélectionner un montant";
            cautionPanel.setText(toPrint);
        }
        else {
            cautionPanel.setText(addOrRemove[idxSelect] + " " + montant + "€ à " + Example.exName[idxClient] + " ?");
        }
    }

}
