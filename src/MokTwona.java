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
    private JButton prolongationButton;
    private JButton addSerieButton;
    private JButton loanedByButton;
    private JButton modifyClientButton;
    private JButton updateCurrentSeriesButton;
    private JButton missingMangaButton;
    private JButton updateSeriesButton;
    private JButton statsButton;
    private JButton lateButton;
    private JButton possessedButton;
    private JFrame frame;
    public static final Database db = Database.initDB();

    private final int size = 75;

    public MokTwona(JFrame frame) {
        this.frame = frame;
        setButton(addClientButton, "src/img/person.png", size);
        setButton(addCreditButton, "src/img/coin.png", size);
        setButton(cautionButton, "src/img/banknote.png", size);
        setButton(locationButton, "src/img/location.png", size);
        setButton(returnButton, "src/img/return.png", size);
        setButton(addSerieButton, "src/img/add.png", size);
        setButton(addMangaButton, "src/img/add.png", size);
        setButton(removeMangaButton, "src/img/remove.png", size);
        setButton(transactionButton, "src/img/transaction.png", size);
        setButton(prolongationButton, "src/img/prolongation.png", (int)(size*1.15), size);
        setButton(modifyClientButton, "src/img/person.png", size);
        setButton(missingMangaButton, "src/img/missing.png", size);
        setButton(loanedByButton, "src/img/loaned.png", size);
        setButton(possessedButton, "src/img/books.png", size);
        setButton(updateSeriesButton, "src/img/update.png", size);
        setButton(statsButton, "src/img/stats.png", size);
        setButton(lateButton, "src/img/late.png", size);
        setButton(updateCurrentSeriesButton, "src/img/update.png", size);

        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter un client");
                frame.setContentPane(new CreateClient(frame).rootPanel);
                frame.setPreferredSize(new Dimension(430, 225));
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/person.png")));
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
                    frame.setIconImage(ImageIO.read(new File("src/img/coin.png")));
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
                    frame.setIconImage(ImageIO.read(new File("src/img/banknote.png")));
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
                    frame.setIconImage(ImageIO.read(new File("src/img/location.png")));
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
                    frame.setIconImage(ImageIO.read(new File("src/img/return.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        prolongationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Prolonger des mangas");
                frame.setContentPane(new ProlongationManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/prolongation.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        addMangaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter un mangas");
                frame.setContentPane(new AddManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/add.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        addSerieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter une série");
                frame.setContentPane(new AddSerie(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/add.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Transactions");
                frame.setContentPane(new ShowTransactions(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/transaction.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        removeMangaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Supprimer un manga");
                frame.setContentPane(new RetireManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/remove.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        modifyClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Modifier un client");
                frame.setContentPane(new ModifyClient(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/person.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        missingMangaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Trouver les mangas manquants");
                frame.setContentPane(new FindMissingManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/missing.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        loanedByButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Trouver les mangas en prêts");
                frame.setContentPane(new GetLoanedManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/loaned.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_VERT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        possessedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Mangas possédés par le kot");
                frame.setContentPane(new PossessedManga(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/books.png")));
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
            frame.setIconImage(ImageIO.read(new File("src/img/kotmanga.png")));
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
