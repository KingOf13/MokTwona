import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProlongationManga {
    private JComboBox clientBox;
    private JTextPane prolongerTextPane;
    private JButton prolongerButton;
    private JPanel panel;
    public JPanel rootPanel;
    private JTextPane clientPanel;
    private JCheckBox freeBox;
    private JFrame frame;
    private Person[] people;
    private Pret[] prets = new Pret[0];

    private boolean[] prolongationArray;

    public ProlongationManga(JFrame frame) {
        this.frame = frame;
        people = MokTwona.db.getPeople();
        freeBox.setText("Annuler les frais de prolongation");

        panel.setLayout(new GridLayout(6, 1));
        clientBox.setModel(new DefaultComboBoxModel(people));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toPrint = "";
                int idx = clientBox.getSelectedIndex();
                clientPanel.setText(people[idx]+ " a " + people[idx].getCredit() + " crédit(s) et " + people[idx].getCaution()
                        + "€ de caution sur son compte.");
                panel.removeAll();
                prets = people[idx].getPrets();
                prolongationArray = new boolean[prets.length];
                for (int i = 0; i < prets.length; i++) panel.add(new MyCheckBox(i, prolongationArray, prets[i].compactString()));
                panel.revalidate();
                panel.repaint();
            }
        });
        prolongerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientBox.getSelectedIndex() == -1) return;
                int amount = 0;
                for (int i = 0; i < prets.length; i++)
                    if (prolongationArray[i]) {
                        int penalties = prets[i].penalties();
                        if (prets[i].prolongate()) amount += penalties + 1;
                    }
                if (!freeBox.isSelected()) people[clientBox.getSelectedIndex()].addCredit(-amount);
                frame.dispose();
            }
        });
    }
}
