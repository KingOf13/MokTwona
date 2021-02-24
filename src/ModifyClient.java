import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyClient {
    private JComboBox clientBox;
    private JButton modifierLeClientButton;
    private JTextPane adresseTextPane;
    private JTextField addressField1;
    public JPanel rootPanel;
    private JTextField addressField2;
    private JTextField mailField;
    private JTextField gsmField;
    private JButton deleteClient;
    private JFrame frame;
    private Person[] people;
    private int idx = -1;

    public ModifyClient(JFrame frame) {
        this.frame = frame;
        people = MokTwona.db.getPeople();
        clientBox.setModel(new DefaultComboBoxModel(people));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idx = clientBox.getSelectedIndex();
                modifierLeClientButton.setEnabled(true);
                deleteClient.setEnabled(true);
                String[] address = people[idx].getAddress().split("\n");
                if (address.length == 0);
                else if (address.length == 1) addressField1.setText(address[0]);
                else {
                    addressField1.setText(address[0]);
                    addressField2.setText(address[1]);
                }
                mailField.setText(people[idx].getEmail());
                gsmField.setText(people[idx].getGsm());
            }
        });
        modifierLeClientButton.setEnabled(false);
        modifierLeClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                people[idx].modify(addressField1.getText().trim() + "\n" + addressField2.getText().trim(), mailField.getText().trim(), gsmField.getText().trim());
                frame.dispose();
            }
        });
        deleteClient.setEnabled(false);
        deleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = people[idx].getPrenom() + " " + people[idx].getNom();
                if (Utils.confirmationDialog("Voulez-vous supprimer " + nom + " du programme ?"));
                if (MokTwona.db.remove(people[idx])) Utils.informationDialog(nom + " a été retiré du prgramme");
                else Utils.informationDialog(nom + " n'a pas été retiré du programme !");
                frame.dispose();
            }
        });
    }

}
