import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LocateManga {


    public JPanel rootPanel;
    private JComboBox clientBox;
    private JButton locateButton;
    private JTextPane locatePanel;
    private JTextPane panierPane;
    private JScrollPane scrollPane;
    private JComboBox mangaBox;
    private JPanel panel;
    private JButton créditsButton;
    private JCheckBox freeCheckBox;
    private JCheckBox crewCheckBox;
    private JFrame frame;
    private int caution = -1, credit = -1;

    private Panier panier = new Panier(50);
    private Manga[] tomes = null;
    private Serie[] series = null;
    private Person[] people = null;

    public LocateManga(JFrame frame) {
        this.frame = frame;
        panel.setLayout(new GridLayout(0, 4, 10, 20));
        locatePanel.setText("Sélectionner une personne et des mangas");
        people = MokTwona.db.getPeople();
        clientBox.setModel(new DefaultComboBoxModel(people));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = clientBox.getSelectedIndex();
                credit = people[idx].getCredit();
                caution = people[idx].getCaution();
                locatePanel.setText(people[idx] + " a " + credit + " crédit(s) et " + caution + "€ de caution sur son compte.");
                refreshPanier();
            }
        });
        locateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((credit >= panier.size() || freeCheckBox.isSelected()) && (panierCautionOk(panier, caution) || crewCheckBox.isSelected())) {
                    panier.locate();
                    frame.dispose();
                }
            }
        });
        series = MokTwona.db.getSeries();
        mangaBox.setModel(new DefaultComboBoxModel(series));
        mangaBox.setSelectedIndex(-1);
        mangaBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    panel.removeAll();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                tomes = series[mangaBox.getSelectedIndex()].getTomes();
                for (int i = 0; i < tomes.length ; i++) {
                    JCheckBox cb = new JCheckBox(tomes[i].toString());
                    cb.addActionListener(new MyListener(cb, panier, tomes[i]));
                    if (panier.contains(tomes[i])) cb.setSelected(true);
                    if (tomes[i].isLoue()) cb.setEnabled(false);
                    panel.add(cb);
                }
                panel.revalidate();
                panel.repaint();
            }
        });
        créditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Ajouter des crédits");
                frame.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {}

                    @Override
                    public void windowClosing(WindowEvent e) {}

                    @Override
                    public void windowClosed(WindowEvent e) {
                        int idx = clientBox.getSelectedIndex();
                        credit = people[idx].getCredit();
                        caution = people[idx].getCaution();
                        locatePanel.setText(people[idx] + " a " + credit + " crédit(s) et " + caution + "€ de caution sur son compte.");
                        refreshPanier();
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {}

                    @Override
                    public void windowDeiconified(WindowEvent e) {}

                    @Override
                    public void windowActivated(WindowEvent e) {}

                    @Override
                    public void windowDeactivated(WindowEvent e) {}
                });
                frame.setContentPane(new AddCredit(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("src/img/coin.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.pack();
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                //frame.setDefaultCloseOperation(); // Ce serait top de mettre l'affichage à jour direct
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        freeCheckBox.setText("Location offerte");
        crewCheckBox.setText("Membre du kot");
    }

    private boolean panierCautionOk(Panier panier, int caution) {
        if (caution == 0) return false;
        else if (caution == 5 && panier.size() <= 2) return true;
        else if (caution == 10 && panier.size() <= 5) return true;
        else return false;
    }

    public class MyListener implements ActionListener {
        JCheckBox cb;
        Panier panier;
        Manga tome;


        public MyListener(JCheckBox cb, Panier panier, Manga tome) {
            this.cb = cb;
            this.panier = panier;
            this.tome = tome;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cb.isSelected() && panier.addToCart(tome, crewCheckBox.isSelected()));
            else panier.removeFromCart(tome);
            refreshPanier();
        }
    }


    public void refreshPanier() {
        panierPane.setText(panier.toString());

    }
    private class Panier {
        private ArrayList<Manga> mPanier = new ArrayList<Manga>();
        private final int capacity;

        private Panier(int capacity) {
            this.capacity = capacity;
        }

        public boolean addToCart(Manga manga, boolean crew) {
            if (mPanier.size() >= capacity && !crew) return false;
            return mPanier.add(manga);
        }

        public void removeFromCart(Manga manga) {
            mPanier.remove(manga);
        }

        public boolean contains(Manga manga) {
            return mPanier.contains(manga);
        }

        public int size() {
            return mPanier.size();
        }

        public void sort() {
            Collections.sort(mPanier);
        }

        @Override
        public String toString() {
            if (!mPanier.isEmpty()) Collections.sort(mPanier);
            String toPrint = "";
            if (clientBox.getSelectedIndex() == -1)  toPrint = "Panier anonyme\n";
            else if (mPanier.size() > 0) toPrint += "Panier de " + people[clientBox.getSelectedIndex()] + " : \n";
            else toPrint = "Panier vide\n";
            for (Manga manga: mPanier) {
                toPrint += manga.completeString() +"\n";
            }
            if (clientBox.getSelectedIndex()>-1) {
                if (people[clientBox.getSelectedIndex()].capacityRemaining() < mPanier.size())
                    toPrint += "Attention, caution trop faible !\n";
                if (people[clientBox.getSelectedIndex()].getCaution() == 0)
                    toPrint += "Attention, pas de caution !\n";
                if (mPanier.size() > people[clientBox.getSelectedIndex()].getCredit())
                    toPrint += "Attention, pas assez de crédits !\n";
            }
            return toPrint;
        }

        public void locate() {
            for (Manga m: mPanier) {
                m.locate(people[clientBox.getSelectedIndex()], crewCheckBox.isSelected(), freeCheckBox.isSelected());
            }
        }
    }
}
