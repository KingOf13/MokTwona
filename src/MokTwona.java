import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MokTwona {
    private JPanel rootPanel;
    private JButton addClientButton;
    private JButton addCreditButton;
    private JButton cautionButton;
    private JButton locationButton;
    private JButton returnButton;
    private JButton addMangaButton;
    private JButton removeMangaButton;
    private JButton transactionButton;
    private JFrame frame;

    private final int size = 125;

    public MokTwona(JFrame frame) {
        this.frame = frame;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("person.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        addClientButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        addCreditButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("banknote.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        cautionButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("location.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        locationButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("return.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        returnButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("add.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        addMangaButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("remove.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        removeMangaButton.setIcon(icon);
        try {
            img = ImageIO.read(new File("transaction.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        transactionButton.setIcon(icon);
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter un client");
                frame.setContentPane(new CreateClient(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            }
        });
        addCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter des crédits");
                frame.setContentPane(new AddCredit(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        cautionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Modifier des cautions");
                frame.setContentPane(new ModifyCaution(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MokTwona");
        frame.setContentPane(new MokTwona(frame).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            frame.setIconImage(ImageIO.read(new File("kotmanga.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }


}
