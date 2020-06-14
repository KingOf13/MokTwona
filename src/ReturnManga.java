import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnManga {
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JTextArea mangaArea;
    private JTextArea timeArea;
    private JButton rendreButton;
    private JButton rendreEtLouerLaButton;
    private JPanel panel;
    private JCheckBox noChargeBox;
    private JFrame frame;
    private boolean[] returnArray;
    private Pret[] prets = new Pret[0];
    private Person[] people;

    public ReturnManga(JFrame frame) {
        this.frame = frame;
        people = MokTwona.db.getPeople();
        panel.setLayout(new GridLayout(6, 1));
        rendreEtLouerLaButton.setEnabled(false);
        noChargeBox.setText("Ne pas appliquer les pénalités de retard");

        DefaultComboBoxModel model = new DefaultComboBoxModel(people);
        clientBox.setModel(model);
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toPrint = "";
                int idx = clientBox.getSelectedIndex();
                panel.removeAll();
                prets = people[idx].getPrets();
                returnArray = new boolean[prets.length];
                for (int i = 0; i < prets.length; i++) {
                    panel.add(new MyCheckBox(i, returnArray, prets[i].compactString()));
                }
                panel.revalidate();
                panel.repaint();
            }
        });
        rendreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int penalties = 0;
                for (int i = 0; i < returnArray.length; i ++) if (returnArray[i]) penalties += prets[i].conclude();
                if (!noChargeBox.isSelected()) people[clientBox.getSelectedIndex()].addCredit(-penalties);
                frame.dispose();
            }
        });
    }
}
