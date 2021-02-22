package ui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AllProcessInputs extends JPanel implements ActionListener {
    public static String[] colNames = {"Process", "Burst Times", "Priorities", "Arrival"};

    public ArrayList<ProcessInfoPanel> processInfos;
    public JTextField quantumTime;
    
    private int processCount = 0;
    private GridBagConstraints myConstraints = new GridBagConstraints();
    private JPanel inpPanel = new JPanel();
    private JScrollPane myScrollPane;
    private JButton addBut, remBut;

    public AllProcessInputs() {
        setLayout(new BorderLayout(5, 5));
        // setBorder(new LineBorder(Color.BLACK, 1));

        myConstraints.weightx = 1;
        myConstraints.weighty = 1;
        myConstraints.gridx = 0;
        myConstraints.gridy = GridBagConstraints.RELATIVE;
        myConstraints.fill = GridBagConstraints.BOTH;

        myScrollPane = new JScrollPane(inpPanel);
        add(myScrollPane, BorderLayout.CENTER);
        inpPanel.setLayout(new GridBagLayout());

        processInfos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            addNewProcess();
        }

        addColHeadings();
        addAddRemButtons();
    }

    public void addNewProcess(String name) {
        processCount++;
        ProcessInfoPanel p = new ProcessInfoPanel(name);
        processInfos.add(p);
        inpPanel.add(p, myConstraints);
        myScrollPane.updateUI();
    }

    public void addNewProcess() {
        this.addNewProcess("P" + Integer.toString(processCount + 1));
    }

    public void removeProcess() {
        if (processCount > 0) {
            processCount--;
            ProcessInfoPanel p = processInfos.get(processCount);
            inpPanel.remove(p);
            processInfos.remove(p);
            myScrollPane.updateUI();
        }
    }

    private void addColHeadings() {
        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, colNames.length));
        for (String col : colNames) {
            colPanel.add(new JLabel(col, JLabel.CENTER));
        }
        add(colPanel, BorderLayout.NORTH);
    }

    private void addAddRemButtons() {
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(1, 4));

        addBut = new JButton("Add New Process");
        addBut.addActionListener(this);
        jp.add(addBut);
        
        remBut = new JButton("Remove Last Process");
        remBut.addActionListener(this);
        jp.add(remBut);

        jp.add(new JLabel("Quantum Time:", JLabel.CENTER));

        quantumTime = new JTextField();
        jp.add(quantumTime);

        add(jp, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBut) {
            addNewProcess();
        } else {
            removeProcess();
        }
    }
}
