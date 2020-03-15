import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ProlongationManga {
    private JComboBox clientBox;
    private JTextPane prolongerTextPane;
    private JButton prolongerButton;
    private JPanel panel;
    public JPanel rootPanel;
    private JTextPane clientPanel;
    private JFrame frame;

    private boolean[] prolongationArray;

    public ProlongationManga(JFrame frame) {
        this.frame = frame;

        panel.setLayout(new GridLayout(6, 1));
        clientBox.setModel(new DefaultComboBoxModel(Example.exName));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toPrint = "";
                int idx = clientBox.getSelectedIndex();
                clientPanel.setText(Example.exName[idx] + " a " + Example.exCredit[idx] + " crédit(s) et " + Example.exCaution[idx]
                        + "€ de caution sur son compte.");
                panel.removeAll();
                prolongationArray = new boolean[Example.exPrets[idx].length];
                String[] pret = Example.exPrets[idx];
                LocalDate[] date = Example.exDate[idx];
                for (int i = 0; i < Example.exPrets[idx].length; i++) {
                    toPrint = pret[i] + " - ";
                    toPrint += Utils.formatDate(Utils.ecartDate(date[i]));
                    panel.add(new MyCheckBox(i, prolongationArray, toPrint));
                }
                panel.revalidate();
                panel.repaint();
            }
        });
    }
}
