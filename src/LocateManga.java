import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class LocateManga {
    private JTree mangaTree;
    public JPanel rootPanel;
    private JComboBox clientBox;
    private JButton locateButton;
    private JTextPane locatePanel;
    private JFrame frame;
    private final String[] exName = {"Pierre", "Paul", "Jean", "Jacques"};
    private final int[] exCredit = {1, 3, 42, 69};
    private final int[] exCaution = {10, 5, 0, 10};
    private int caution = -1, credit = -1;


    public LocateManga(JFrame frame) {
        this.frame = frame;
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("One Piece");
        for (int i =1; i <= 93; i++) node1.add(new DefaultMutableTreeNode(new JCheckBox("One Piece " + i), false));
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Naruto");
        for (int i =1; i <= 73; i++) node2.add(new DefaultMutableTreeNode(new JCheckBox("Naruto " + i), false));
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("Bleach");
        for (int i =1; i <= 75; i++) node3.add(new DefaultMutableTreeNode(new JCheckBox("Bleach " + i), false));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Mangas");
        root.add(node1);
        root.add(node2);
        root.add(node3);
        RenduComposant rc = new RenduComposant();
        EditComposant ec = new EditComposant();
        DefaultTreeModel model = new DefaultTreeModel(root);
        mangaTree.setModel(model);
        mangaTree.setCellRenderer(rc);
        mangaTree.setCellEditor(ec);
        mangaTree.setEditable(true);
        locatePanel.setText("Sélectionner une personne et des mangas");
        clientBox.setModel(new DefaultComboBoxModel(exName));
        clientBox.setSelectedIndex(-1);
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = clientBox.getSelectedIndex();
                credit = exCredit[idx];
                caution = exCaution[idx];
                locatePanel.setText(exName[idx] + " a " + credit + " crédit(s) et " + caution + "€ de caution sur son compte.");
            }
        });
        locateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    class RenduComposant implements TreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object obj, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){
            DefaultMutableTreeNode dmtcr = (DefaultMutableTreeNode)obj;
            if(dmtcr.getUserObject() instanceof JCheckBox){
                JCheckBox toto = (JCheckBox)dmtcr.getUserObject();
                return toto;
            } else {
                JLabel toto = new JLabel((String)dmtcr.getUserObject());
                return toto;
            }
        }
    }

    class EditComposant implements TreeCellEditor {
        public void addCellEditorListener(CellEditorListener l){
        }
        public void cancelCellEditing() {
        }
        public Object getCellEditorValue(){
            return this;
        }
        public boolean isCellEditable(EventObject evt){
            if(evt instanceof MouseEvent){
                MouseEvent mevt = (MouseEvent) evt;
                if (mevt.getClickCount() == 1){
                    return true;
                }
            }
            return false;
        }
        public void removeCellEditorListener(CellEditorListener l){
        }
        public boolean shouldSelectCell(EventObject anEvent){
            return true;
        }
        public boolean stopCellEditing(){
            return false;
        }
        public Component getTreeCellEditorComponent(JTree tree, Object obj, boolean isSelected, boolean expanded, boolean leaf, int row){
            DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)obj;
            if ( dmtn.getUserObject() instanceof JCheckBox ) {
                JCheckBox tata=(JCheckBox)dmtn.getUserObject();
                tata.setEnabled(true);
                return tata;
            }
            return null;
        }
    }
}
