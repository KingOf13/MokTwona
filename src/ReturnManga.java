import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReturnManga {
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JTextArea mangaArea;
    private JTextArea timeArea;
    private JButton rendreButton;
    private JButton rendreLouerLaSuiteButton;
    private JPanel panel;
    private JFrame frame;
    private boolean[] returnArray;

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

    public ReturnManga(JFrame frame) {
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
                returnArray = new boolean[exPrets[idx].length];
                String[] pret = exPrets[idx];
                LocalDate[] date = exDate[idx];
                for (int i = 0; i < exPrets[idx].length; i++) {
                    toPrint = pret[i] + " - ";
                    int cmp = ecartDate(date[i]);
                    if (cmp < 0 ) toPrint += (-cmp) + " jour(s) de retard !";
                    else toPrint += cmp + " jour(s) avant la fin de la location";
                    panel.add(new MyCheckBox(i, returnArray, toPrint));
                }
                panel.revalidate();
                panel.repaint();
            }
        });
        rendreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (boolean b: returnArray) System.out.println(b);
                frame.dispose();
            }
        });
    }



    public static int ecartDate(LocalDate fin) {
        Period period;
        fin = LocalDate.from(fin.atStartOfDay());
        LocalDate tday = LocalDate.from(LocalDate.now().atStartOfDay());
        period = Period.between(tday, fin);
        int cmp = period.getDays();
        return cmp;
    }

    private class MyCheckBox extends JCheckBox {
        int idx;
        boolean[] array;
        public MyCheckBox(int idx, boolean[] array, String str) {
            super(str);
            this.idx = idx;
            this.array = array;
            addListener();
        }

        public void addListener() {
            this.addActionListener(new MyCBListener(array, this));
        }
    }

    private class MyCBListener implements ActionListener {
        private boolean[] array;
        private MyCheckBox mcb;

        public MyCBListener(boolean[] array, MyCheckBox mcb) {
            super();
            this.array = array;
            this.mcb = mcb;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            array[mcb.idx] = mcb.isSelected();
        }
    }
}
