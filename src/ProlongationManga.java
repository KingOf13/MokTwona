import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;

public class ProlongationManga {
    private JComboBox clientBox;
    private JTextPane prolongerTextPane;
    private JButton prolongerButton;
    private JPanel panel;
    public JPanel rootPanel;
    private JFrame frame;


    private String[] exName = {"Pierre", "Paul", "Jean", "Jacques"};

    private String[] exPretsPierre = {"Naruto tome 1", "Bleach tome 7", "One Piece tome 2"};
    private String[] exPretsPaul = {"Naruto tome 2", "Bleach tome 3", "One Piece tome 15"};
    private String[] exPretsJean = {"Naruto tome 7", "Bleach tome 52", "One Piece tome 88", "One Piece tome 89", "One Piece tome 90"};
    private String[] exPretsJacques = {"Naruto tome 6", "Bleach tome 1", "Bleach tome 2"};
    private String[][] exPrets = {exPretsPierre, exPretsPaul, exPretsJean, exPretsJacques};

    private LocalDate date1 = LocalDate.of(2020, 03, 22);
    private LocalDate date2 = LocalDate.of(2020, 03, 10);

    private LocalDate[] exDatePierre = {date1, date1, date1};
    private LocalDate[] exDatePaul = {date2, date2, date2};
    private LocalDate[] exDateJean = {date2, date2, date1, date1, date1};
    private LocalDate[] exDateJacques = {date1, date1, date1};
    private LocalDate[][] exDate = {exDatePierre, exDatePaul, exDateJean, exDateJacques};

    private boolean[] prolongationArray;

    public ProlongationManga(JFrame frame) {
        this.frame = frame;

        panel.setLayout(new GridLayout(6, 1));
        clientBox.setModel(new DefaultComboBoxModel(exName));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toPrint = "";
                int idx = clientBox.getSelectedIndex();
                panel.removeAll();
                prolongationArray = new boolean[exPrets[idx].length];
                String[] pret = exPrets[idx];
                LocalDate[] date = exDate[idx];
                for (int i = 0; i < exPrets[idx].length; i++) {
                    toPrint = pret[i] + " - ";
                    int cmp = Utils.ecartDate(date[i]);
                    if (cmp < 0 ) toPrint += (-cmp) + " jour(s) de retard !";
                    else toPrint += cmp + " jour(s) avant la fin de la location";
                    panel.add(new MyCheckBox(i, prolongationArray, toPrint));
                }
                panel.revalidate();
                panel.repaint();
            }
        });
    }
}
