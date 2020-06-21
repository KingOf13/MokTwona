import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AddCredit {
    private JTextPane clientTextPane;
    private JComboBox clientBox;
    private JTextField amountField;
    private JTextPane convertPane;
    private JButton convertButton;
    private JButton buyButton;
    public JPanel rootPanel;
    private JFrame frame;
    private boolean admissible = false;
    private int creditToAdd = 0;
    private int selected = -1;
    private Person[] people;
    private double amount = 0;

    public AddCredit(JFrame frame) {
        this.frame = frame;
        people = MokTwona.db.getPeople();
        DefaultComboBoxModel model = new DefaultComboBoxModel(people);
        clientBox.setModel( model );
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = clientBox.getSelectedIndex();
            }
        });
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    amount = Double.parseDouble(amountField.getText().trim());
                    creditToAdd = convert(amount);
                    if (selected == -1) {
                        convertPane.setText("Selectionnez quelqu'un à qui ajouter des crédits!");
                    }
                    else {
                        admissible = true;
                        convertPane.setText(people[selected].toString() + " achète " + creditToAdd
                                + " crédit(s) pour " + amount + "€");
                    }
                }
                catch (NumberFormatException ex) {
                    convertPane.setText("Entrez un montant valide");
                    admissible = false;
                }
            }
        });
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (admissible) {
                    if (!Utils.confirmationDialog("Ajouter " + creditToAdd + " crédits à " + people[selected] + " pour " + amount + "€ ?")) return;
                    MokTwona.db.add(new Transaction(LocalDateTime.now(), amount, "Achat de crédit", people[selected]));
                    people[selected].addCredit(creditToAdd);
                    frame.dispose();
                }
            }
        });
    }

    public static int convert(double montant) {
        if (montant <= 5) {
            int ret = (int) Math.ceil(montant * 3);
            return ret;
        }
        else if (montant < 10) {
            int ret = (int) Math.ceil(montant/0.3);
            return ret;
        }
        else if (montant<20){
            int ret = (int) Math.ceil(montant*4);
            return ret;
        }
        else {
            int ret = (int) Math.ceil(montant*5);
            return ret;
        }
    }

}
