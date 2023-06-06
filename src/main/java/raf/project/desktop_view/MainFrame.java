package raf.project.desktop_view;

import raf.project.app.service.QueryService;
import raf.project.app.error.GrammarError;
import raf.project.app.error.SyntaxError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        instructionLabel.setSize(50,30);

        JButton runButton = new JButton("Run query");
        runButton.setBackground(new Color(144, 213, 96));
        runButton.setSize(100,50);
        runButton.setFocusPainted(false);
        runButton.setBorder(null);
            runButton.setPreferredSize(new Dimension(100,25));
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    table.displayResultSet(QueryService.MY_INSTANCE.runQuery(queryArea.getText()));

                } catch (SyntaxError | GrammarError ex) {
                    ex.printStackTrace();
                }            }

            @Override
            public void mousePressed(MouseEvent e) {
                runButton.setBackground(new Color(144, 213, 96));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                runButton.setBackground(new Color(65, 143, 15));
               // mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                runButton.setBackground(new Color(52, 103, 19));
                runButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                runButton.setBackground(new Color(144, 213, 96));
                runButton.setForeground(Color.BLACK);

            }
        });

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 20 ));
        northPanel.setBackground(new Color(255, 255, 255, 86));

        northPanel.add(instructionLabel);
        northPanel.add(runButton);

        getContentPane().add(northPanel, BorderLayout.NORTH);

        queryArea = new JTextArea();
        queryArea.setSize( 100, 100);
        getContentPane().add(queryArea, BorderLayout.CENTER);



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
