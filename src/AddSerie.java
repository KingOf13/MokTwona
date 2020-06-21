import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSerie {
    private JTextArea resumeArea;
    private JTextField nameField;
    private JTextArea lastPublishedArea;
    private JTextField lastPublishedField;
    private JCheckBox endedBox;
    private JButton addButton;
    public JPanel rootPanel;
    private JFrame frame;
    private String name;
    private int lastPublished = 0;

    public AddSerie(JFrame frame) {
        this.frame = frame;
        lastPublishedArea.setText("Dernier tome publié");
        endedBox.setText("Série finie d'être publiée ?");
        addButton.setText("Ajouter la série");
        addButton.setEnabled(false);

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameField.getText().trim();
                setResumeArea();
            }
        });

        lastPublishedField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lastPublished = Integer.parseInt(lastPublishedField.getText().trim());
                } catch (NumberFormatException ex) {
                    lastPublished = 0;
                    lastPublishedField.setText("");
                }
                setResumeArea();
            }
        });

        endedBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setResumeArea();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Utils.confirmationDialog("Ajouter la série " + name + " à la mangathèque ?"))
                new Serie(name, 0, lastPublished, endedBox.isSelected());
                frame.dispose();
            }
        });

    }

    private void setResumeArea() {
        String toPrint = "";
        if (nameField.getText().trim().equals("")) {
            toPrint +="Spécifier un titre à la série\n";
            addButton.setEnabled(false);
        }
        else {
            toPrint += name + "\n";
            addButton.setEnabled(true);
        }
        if (!lastPublishedField.getText().trim().equals("")) {
            toPrint += "Dernier tome publié : " + lastPublished + "\n";
        }
        if (endedBox.isSelected()) toPrint+= "Série terminée";
        resumeArea.setText(toPrint);
    }
}
