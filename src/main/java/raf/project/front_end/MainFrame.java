package raf.project.front_end;

import javax.swing.*;

public class MainFrame extends JFrame {

    JTable table;

    public MainFrame() {

        setSize( (int) (getToolkit().getScreenSize().getWidth() * 75/100), (int)getToolkit().getScreenSize().getHeight() * 75/100);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
