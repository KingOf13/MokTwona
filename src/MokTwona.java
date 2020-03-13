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
        setButton(addClientButton, "person.png", size);
        setButton(addCreditButton, "coin.png", size);
        setButton(cautionButton, "banknote.png", size);
        setButton(locationButton, "location.png", size);
        setButton(returnButton, "return.png", size);
        setButton(addMangaButton, "add.png", size);
        setButton(removeMangaButton, "remove.png", size);
        setButton(transactionButton, "transaction.png", size);

        /*JFrame frameTemp = new JFrame("Ajouter un client");
        frameTemp.setPreferredSize(new Dimension(425, 225));
        addClientButton.addActionListener(new MyListener("person.png", new CreateClient(frameTemp).rootPanel, frameTemp));
        frameTemp = new JFrame("Acheter des crédits");
        addCreditButton.addActionListener(new MyListener("coin.png", new AddCredit(frameTemp).rootPanel, frameTemp));
        frameTemp = new JFrame("Modifier des cautions");
        cautionButton.addActionListener(new MyListener("banknote.png", new ModifyCaution(frameTemp).rootPanel, frameTemp));
        frameTemp = new JFrame("Louer des mangas");
        locationButton.addActionListener(new MyListener("location.png", new LocateManga(frameTemp).rootPanel, frameTemp, true, JFrame.MAXIMIZED_VERT));
        frameTemp = new JFrame("Rendre des mangas");
        returnButton.addActionListener(new MyListener("return.png", new ReturnManga(frameTemp).rootPanel, frameTemp));*/
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter un client");
                frame.setContentPane(new CreateClient(frame).rootPanel);
                frame.setPreferredSize(new Dimension(430, 225));
                try {
                    frame.setIconImage(ImageIO.read(new File("person.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                try {
                    frame.setIconImage(ImageIO.read(new File("coin.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                try {
                    frame.setIconImage(ImageIO.read(new File("banknote.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        locationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Louer des mangas");
                frame.setContentPane(new LocateManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("location.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Rendre des mangas");
                frame.setContentPane(new ReturnManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("return.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    private void setButton(JButton button, String imgPath, int size) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        button.setIcon(icon);
    }

    private void setButton(JButton button, String imgPath, int width, int height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        button.setIcon(icon);
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



/*
    private class MyListener implements ActionListener {
        String path;
        Container rootPanel;
        String title;
        boolean size = false;
        int state = -1;
        JFrame frame;


        public MyListener(String path, Container rootPanel, JFrame frame) {
            super();
            this.path = path;
            this.rootPanel = rootPanel;
            this.title = title;
            this.frame = frame;
        }
        public MyListener(String path, Container rootPanel, JFrame frame, boolean size, int state) {
            super();
            this.path = path;
            this.rootPanel = rootPanel;
            this.title = title;
            this.size = size;
            this.state = state;
            this.frame = frame;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setContentPane(rootPanel);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                frame.setIconImage(ImageIO.read(new File(path)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.pack();
            if (size) frame.setExtendedState(state);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
*/


}
