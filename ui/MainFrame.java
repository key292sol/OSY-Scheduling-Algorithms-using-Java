package ui;

import schedulers.*;

import javax.swing.JFrame;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainFrame extends JFrame {
    private AllProcessInputs inputter = new AllProcessInputs();
    private ChooserPanel bPanel = new ChooserPanel(this);
    private OutputPanel outPanel = new OutputPanel(this);
    private Scheduler lastScheduler;

    public MainFrame() {
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 0, 5, 0);
        
        // Add the Panel of Inputs
        constraints.gridy = 0;
        constraints.weighty = 0.5;
        add(inputter, constraints);
        
        // Add the Panel for choosing the algo
        constraints.gridy = 1;
        constraints.weighty = 0.005;
        add(bPanel, constraints);

        // Add the output table panel
        constraints.gridy = 2;
        constraints.weighty = 0.5;
        add(outPanel, constraints);

        setSize(500, 550);
        setVisible(true);
        setTitle("Scheduling Algorithms");
        setLocationRelativeTo(null);

        tester();
    }

    private void tester() {
        int[] bursts = {9, 4, 7, 6};
        int[] pris   = {0, 0, 0, 0};
        int[] arris  = {0, 0, 0, 0};
        int q = 3;
        while (inputter.processInfos.size() < bursts.length) {
            inputter.addNewProcess();
        }
        int i = 0;
        for (ProcessInfoPanel pip : inputter.processInfos) {
            pip.burstTime.setText(Integer.toString(bursts[i]));
            pip.arrivalTime.setText(Integer.toString(arris[i]));
            pip.priority.setText(Integer.toString(pris[i]));
            i++;
        }
        if (q != -1) {
            inputter.quantumTime.setText(Integer.toString(q));
        }

        Scheduler s;
        s = new Fcfs();
        // s = new Sjf();
        // s = new PriorityScheduler();
        // s = new RoundRobin();
        schedule(s);
        // scheduleTill(s, 10);
    }

    public void schedule(Scheduler s) {
        s.setProcesses(inputter);
        if (!s.originalOrder.isEmpty()) {
            lastScheduler = s;
            s.allSteps();
            outPanel.setTableValues(s.originalOrder, s.myName, s.curTime - 1);
        }
    }

    public void scheduleTill(Scheduler s, int time) {
        s.scheduleTill(time);
        outPanel.setTableValues(s.getSeenProcesses(), s.myName, -1);
    }

    public void scheduleTill(int time) {
        scheduleTill(lastScheduler, time);
    }
}
