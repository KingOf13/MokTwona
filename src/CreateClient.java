import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class CreateClient {
    private JTextField nameField;
    private JTextPane nomTextPane;
    private JTextField prenomField;
    private JTextPane prénomTextPane;
    public JPanel rootPanel;
    private JButton ajouterLeClientButton;
    private JTextField adressField;
    private JTextField mailField;
    private JTextField gsmField;
    private JTextField addressField1;
    private JTextField addressField2;
    private JFrame frame;

    public CreateClient(JFrame frame) {
        this.frame = frame;
        ajouterLeClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createClient();
            }
        });
    }

    private void createClient() {
        String prenom = prenomField.getText().trim();
        String name = nameField.getText().trim();
        String mail = mailField.getText().trim();
        String address = addressField1.getText().trim() + "\n" + addressField2.getText().trim();
        String gsm = gsmField.getText().trim();
        String message = "Ajouter ce client ?\nNom : " + name + ", " + prenom + "\n";
        message += "Adresse : " + address + "\n";
        message += "E-mail : " + mail + "\n";
        message += "GSM : " + gsm + "\n";
        if (!Utils.confirmationDialog(message))
        MokTwona.db.add(new Person(name, prenom, mail, gsm, address, LocalDateTime.now(), 0, 1));
        frame.dispose();
    }
}
