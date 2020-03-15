import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ReturnManga {
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JTextArea mangaArea;
    private JTextArea timeArea;
    private JButton rendreButton;
    private JButton rendreEtLouerLaButton;
    private JPanel panel;
    private JFrame frame;
    private boolean[] returnArray;

    public ReturnManga(JFrame frame) {
        this.frame = frame;
        panel.setLayout(new GridLayout(6, 1));
        rendreEtLouerLaButton.setEnabled(false);

        clientBox.setModel(new DefaultComboBoxModel(Example.exName));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toPrint = "";
                int idx = clientBox.getSelectedIndex();
                panel.removeAll();
                returnArray = new boolean[Example.exPrets[idx].length];
                String[] pret = Example.exPrets[idx];
                LocalDate[] date = Example.exDate[idx];
                for (int i = 0; i < Example.exPrets[idx].length; i++) {
                    toPrint = pret[i] + " - ";
                    toPrint += Utils.formatDate(Utils.ecartDate(date[i]));
                    panel.add(new MyCheckBox(i, returnArray, toPrint));
                }
                panel.revalidate();
                panel.repaint();
            }
        });
        rendreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
