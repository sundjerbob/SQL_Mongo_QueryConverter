package raf.project.desktop_view;

import javax.swing.*;
import java.awt.*;

public class ResultTable extends JTable {

    //todo: ovde pisi tabelu M
    @Override
    public void paint(Graphics g) {
        setPreferredSize(new Dimension(getParent().getWidth(), getParent().getHeight() / 2));
        super.paint(g);
    }
}
