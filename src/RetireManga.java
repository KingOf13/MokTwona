import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class RetireManga {
    public JPanel rootPanel;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JComboBox mangaBox;
    private JButton créditsButton;
    private JTextPane resumePanel;
    private JCheckBox removeLoanedCheckBox;
    private JButton removeButton;
    private JFrame frame;
    private Serie[] series;
    private Manga[] tomes;
    private Panier panier = new Panier();

    public RetireManga(JFrame frame) {
        this.frame = frame;
        panel.setLayout(new GridLayout(0, 4, 10, 20));
        series = MokTwona.db.getSeries();
        mangaBox.setModel(new DefaultComboBoxModel(series));
        mangaBox.setSelectedIndex(-1);
        removeButton.setText("Retirer les mangas de la mangathèque");
        mangaBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        removeLoanedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panier.remove();
                frame.dispose();
            }
        });
    }

    private void refreshPanel() {
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
            if (tomes[i].isLoue() && !removeLoanedCheckBox.isSelected()) cb.setEnabled(false);
            panel.add(cb);
        }
        panel.revalidate();
        panel.repaint();
    }

    public void refreshAffichage() {
        resumePanel.setText(panier.toString());
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
            if (cb.isSelected() && panier.addToCart(tome));
            else panier.removeFromCart(tome);
            refreshAffichage();
        }
    }

    private class Panier {
        private ArrayList<Manga> mPanier = new ArrayList<Manga>();

        private Panier() {
        }

        public boolean addToCart(Manga manga) {
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
            if (mPanier.isEmpty()) return "";
            Collections.sort(mPanier);
            String toPrint = "Mangas à supprimer de la bibliothèque : \n";
            for (Manga manga: mPanier) {
                toPrint += manga.completeString() + " appartenant à " + manga.getProprio() +"\n";
            }
            return toPrint;
        }

        public void remove() {
            for (Manga m: mPanier) {
                Serie s = m.getSerie();
                if (removeLoanedCheckBox.isSelected() && m.isLoue()) MokTwona.db.removeFromPret(m);
                if (removeLoanedCheckBox.isSelected() || !m.isLoue()) MokTwona.db.remove(m);
                if (s.isEmpty()) MokTwona.db.remove(s);
            }
        }
    }
}
