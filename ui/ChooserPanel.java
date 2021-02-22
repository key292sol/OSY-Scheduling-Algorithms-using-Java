package ui;

import schedulers.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooserPanel extends JPanel implements ActionListener {
    private MainFrame myFrame;
    private JComboBox<String> ch;
    private JButton submit;

    public ChooserPanel(MainFrame f) {
        myFrame = f;
        setLayout(new GridLayout(1, 2, 5, 5));

        String elms[] = {"FCFS", "SJF", "Priority", "Round Robin"};
        ch = new JComboBox<>(elms);

        submit = new JButton("Schedule");
        submit.addActionListener(this);

        add(ch);
        add(submit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (ch.getSelectedIndex()) {
            case 0: myFrame.schedule(new Fcfs()); break;
            case 1: myFrame.schedule(new Sjf()); break;
            case 2: myFrame.schedule(new PriorityScheduler()); break;
            case 3: myFrame.schedule(new RoundRobin()); break;
            default:
                System.out.println("Default value in ChooserPanel combobox");
                break;
        }
    }
}
