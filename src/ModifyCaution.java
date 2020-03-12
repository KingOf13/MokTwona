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
    private JFrame frame;
    private final String[] exName = {"Pierre", "Paul", "Jean", "Jacques"};
    private final String[] addOrRemove = {"Ajouter", "Retirer"};
    private int[] exCaution = {10, 5 , 0, 5};

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
    }

}
