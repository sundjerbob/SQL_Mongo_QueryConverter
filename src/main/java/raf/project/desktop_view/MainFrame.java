package raf.project.desktop_view;

import raf.project.app.service.QueryService;
import raf.project.error.GrammarError;
import raf.project.error.SyntaxError;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    ResultTable table;
    JTextArea queryArea;

    public MainFrame() {

        setTitle("SQL to MQL Converter");
        setSize( (int) (getToolkit().getScreenSize().getWidth() * 75/100), (int)getToolkit().getScreenSize().getHeight() * 75/100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("Type in the query in SQL:");
        instructionLabel.setBounds(50,50, 50,30);

        JButton runButton = new JButton("Run query");
        runButton.setBounds(50,50,50,30);


        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
        northPanel.add(instructionLabel);
        northPanel.add(runButton);

        getContentPane().add(northPanel, BorderLayout.NORTH);

        queryArea = new JTextArea();
        queryArea.setBounds(100, 100, 100, 100);
        getContentPane().add(queryArea, BorderLayout.CENTER);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    QueryService.MY_INSTANCE.runQuery(queryArea.getText());
                } catch (SyntaxError | GrammarError ex) {
                    ex.printStackTrace();
                }
            }
        });

        table = new ResultTable();
        JScrollPane southPanel = new JScrollPane(table);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        //JScrollPane upperScrollPane = new JScrollPane();
        //JScrollPane lowerScrollPane = new JScrollPane(table);

        //JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperScrollPane, lowerScrollPane);
        //splitPane.setDividerLocation(500);

        //getContentPane().setLayout(new FlowLayout());
       // getContentPane().add(splitPane);







        //upperScrollPane.add(panel);



        setVisible(true);

    }

    public ResultTable getTable() {
        return table;
    }

    public void setTable(ResultTable table) {
        this.table = table;
    }
}
