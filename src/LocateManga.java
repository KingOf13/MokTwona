import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JFrame frame;
    private int caution = -1, credit = -1;

    private ArrayList<Pair> panier = new ArrayList<Pair>();


    public LocateManga(JFrame frame) {
        this.frame = frame;
        panel.setLayout(new GridLayout(0, 4, 10, 20));
        locatePanel.setText("Sélectionner une personne et des mangas");
        clientBox.setModel(new DefaultComboBoxModel(Example.exName));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = clientBox.getSelectedIndex();
                credit = Example.exCredit[idx];
                caution = Example.exCaution[idx];
                locatePanel.setText(Example.exName[idx] + " a " + credit + " crédit(s) et " + caution + "€ de caution sur son compte.");
                refreshPanier();
            }
        });
        locateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (credit >= panier.size() && panierCautionOk(panier, caution)) frame.dispose();
            }
        });
        mangaBox.setModel(new DefaultComboBoxModel(Example.exManga));
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
                for (int i = 0; i < Example.exLastPossessed[mangaBox.getSelectedIndex()]; i++) {
                    Pair pair = null;
                    if (panier.contains(new Pair(mangaBox.getSelectedIndex(), i+1)))
                        pair = panier.get(panier.indexOf(new Pair(mangaBox.getSelectedIndex(), i+1)));
                    JCheckBox cb = new JCheckBox("" + (i+1));
                    if (pair == null)
                        cb.addActionListener(new MyListener(cb, panier, new Pair(mangaBox.getSelectedIndex(), i+1)));
                    else {
                        cb.setSelected(true);
                        cb.addActionListener(new MyListener(cb, panier, pair));
                    }
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
                frame.setContentPane(new AddCredit(frame).rootPanel);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.setIconImage(ImageIO.read(new File("coin.png")));
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
    }

    private boolean panierCautionOk(ArrayList<Pair> panier, int caution) {
        if (caution == 0) return false;
        else if (caution == 5 && panier.size() <= 2) return true;
        else if (caution == 10 && panier.size() <= 5) return true;
        else return false;
    }

    public class MyListener implements ActionListener {
        JCheckBox cb;
        ArrayList<Pair> panier;
        Pair tome;


        public MyListener(JCheckBox cb, ArrayList<Pair> panier, Pair tome) {
            this.cb = cb;
            this.panier = panier;
            this.tome = tome;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cb.isSelected()) panier.add(tome);
            else panier.remove(tome);
            refreshPanier();
        }
    }


    public void refreshPanier() {
        Collections.sort(panier);
        String toPrint = "";
        if (clientBox.getSelectedIndex() == -1)  toPrint = "Panier anonyme\n";
        else if (panier.size() > 0) toPrint += "Panier de " + Example.exName[clientBox.getSelectedIndex()] + " : \n";
        else toPrint = "Panier vide";
        for (Pair p: panier) {
            toPrint += Example.exManga[p.manga] + " tome " + p.numero +"\n";
        }
        if (clientBox.getSelectedIndex()>-1) {
            if (Example.exCaution[clientBox.getSelectedIndex()] == 5 && panier.size() > 2)
                toPrint += "Attention, caution trop faible !\n";
            if (Example.exCaution[clientBox.getSelectedIndex()] == 0)
                toPrint += "Attention, pas de caution !\n";
            if (panier.size() > Example.exCredit[clientBox.getSelectedIndex()])
                toPrint += "Attention, pas assez de crédits !\n";
        }
        if (panier.size() > 5) toPrint += "Pas plus de 5 mangas à la fois";
        panierPane.setText(toPrint);

    }

    private class Pair implements Comparable<Pair> {
        public int manga;
        public int numero;

        public Pair(int manga, int numero) {
            this.manga = manga;
            this.numero = numero;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                Pair oPair = (Pair) obj;
                return oPair.numero == numero  && oPair.manga == manga;
            }
            return false;
        }

        @Override
        public int compareTo(Pair o) {
            if (manga == o.manga) return numero-o.numero;
            else return manga - o.manga;
        }
    }
}
