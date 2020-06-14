import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ShowTransactions {
    public JPanel rootPanel;
    private JTextPane transactionPane;
    private JButton printButton;
    private static final String HEADER = "DATE \t\t|\t TYPE \t\t|\t PERSONNE \t|\t MONTANT";
    private JFrame frame;
    private Transaction[] transactions;
    private String toPrint = "";
    private double amount = 0;

    public ShowTransactions(JFrame frame) {
        this.frame = frame;
        transactions = MokTwona.db.getTransactions();
        if (transactions.length == 0) {
            toPrint = "Pas de transactions dans la DB";
            printButton.setEnabled(false);
        }
        else {
            printButton.setEnabled(true);
            toPrint += HEADER + "\n";
            for (Transaction t: transactions) {
                toPrint += t.toString() + "\n";
                amount += t.getMontant();
            }
            toPrint += "TOTAL : " + amount + "€";
        }
        transactionPane.setText(toPrint);
        printButton.setText("Imprimer dans un fichier et effacer");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"))+ "_resumed_transactions.txt";
                    BufferedWriter buf = new BufferedWriter(new FileWriter(fileName));
                    DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                            .withZone(OffsetDateTime.now( ZoneId.systemDefault() ).getOffset());
                    buf.write("Imprimé le " + LocalDateTime.now().format(f) + "\n");
                    buf.write(toPrint);
                    buf.close();
                    MokTwona.db.removeAllTransactions();
                    frame.dispose();
                } catch (IOException ex) {

                }
            }
        });
    }
}
