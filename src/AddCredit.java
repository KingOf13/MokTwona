import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private final String[] exName = {"Pierre", "Paul", "Jean", "Jacques"};
    private int selected = -1;

    public AddCredit(JFrame frame) {
        this.frame = frame;
        DefaultComboBoxModel model = new DefaultComboBoxModel( exName );
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
                    Double amount = Double.parseDouble(amountField.getText().trim());
                    if (selected == -1) {
                        convertPane.setText("Selectionnez quelqu'un à qui ajouter des crédits!");
                    }
                    else {
                        admissible = true;
                        convertPane.setText(amount + "");
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
                    frame.dispose();
                }
            }
        });
    }

}
