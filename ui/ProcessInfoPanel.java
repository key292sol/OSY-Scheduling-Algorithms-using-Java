package ui;import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;

public class ProcessInfoPanel extends JPanel {
    public JTextField burstTime, priority, arrivalTime, name;

    public ProcessInfoPanel(String _name) {
        setLayout(new GridLayout(1, 4, 10, 10));

        name = new JTextField(_name);
        burstTime = new JTextField();
        priority = new JTextField();
        arrivalTime = new JTextField();

        add(name);
        add(burstTime);
        add(priority);
        add(arrivalTime);
    }
}
