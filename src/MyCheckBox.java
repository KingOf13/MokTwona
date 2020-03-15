import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCheckBox extends JCheckBox {
    int idx;
    boolean[] array;

    public MyCheckBox(int idx, boolean[] array, String str) {
        super(str);
        this.idx = idx;
        this.array = array;
        addListener();
    }

    public void addListener() {
        this.addActionListener(new MyCBListener(array, this));
    }

    public class MyCBListener implements ActionListener {
        private boolean[] array;
        private MyCheckBox mcb;

        public MyCBListener(boolean[] array, MyCheckBox mcb) {
            super();
            this.array = array;
            this.mcb = mcb;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            array[mcb.idx] = mcb.isSelected();
        }
    }
}