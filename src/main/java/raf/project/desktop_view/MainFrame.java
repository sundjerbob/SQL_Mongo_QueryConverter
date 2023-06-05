package raf.project.desktop_view;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    JTable table;

    public MainFrame() {

        setTitle("SQL to MQL Converter");
        setSize( (int) (getToolkit().getScreenSize().getWidth() * 75/100), (int)getToolkit().getScreenSize().getHeight() * 75/100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane upperScrollPane = new JScrollPane();
        JScrollPane lowerScrollPane = new JScrollPane(new ResultTable().displayResultSet(null));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, upperScrollPane, lowerScrollPane);
        splitPane.setDividerLocation(500);

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(splitPane);


        JLabel instructionLabel = new JLabel("Type in the query in SQL:");
        instructionLabel.setBounds(50,100, 100,30);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(instructionLabel);

        JButton runButton = new JButton("Run query");
        runButton.setBounds(50,50,100,30);
        panel.add(runButton);

        upperScrollPane.add(panel);


        /*JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        


        JButton runButton = new JButton("Run query");
        runButton.setBounds(50,50,100,30);
        getContentPane().add(runButton);

        JTextArea queryArea = new JTextArea();
        queryArea.setBounds(500, 500, 500, 500);
        getContentPane().add(instructionLabel);


        JScrollPane scrollPane = new JScrollPane(queryArea);
        panel.add(scrollPane);


        JLabel jTableLabel = new JLabel("ResultSet");
        jTableLabel.setBounds(50,50, 100,30);


        //JTable jTable = new JTable();



         */
        setVisible(true);

    }
}
