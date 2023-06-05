package raf.project.desktop_view;

import javax.swing.*;

public class MainFrame extends JFrame {

    JTable table;

    public MainFrame() {

        setSize( (int) (getToolkit().getScreenSize().getWidth() * 75/100), (int)getToolkit().getScreenSize().getHeight() * 75/100);
        setLocationRelativeTo(null);
        setVisible(true);
        //
        // setContentPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new ResultTable(),new JPanel(),));

    }
}
