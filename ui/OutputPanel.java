package ui;

import schedulers.Process;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.table.DefaultTableModel;

public class OutputPanel extends JPanel {
    private JTable opTable;
    private DefaultTableModel model;
    private JScrollPane jsp;
    private JLabel totalAvgText;
    private UpperPanel upperPanel;
    private MainFrame myFrame;

    public OutputPanel(MainFrame frame) {
        myFrame = frame;
        setLayout(new BorderLayout(0, 5));
        setBorder(new LineBorder(Color.BLACK, 1));

        model = new DefaultTableModel();
        addColumns(AllProcessInputs.colNames);
        addColumns(new String[] { "Waiting", "Turnaround" });

        opTable = new JTable(model);
        jsp = new JScrollPane(opTable);
        add(jsp, BorderLayout.CENTER);

        upperPanel = new UpperPanel();
        add(upperPanel, BorderLayout.NORTH);

        totalAvgText = new JLabel("", JLabel.CENTER);
        add(totalAvgText, BorderLayout.SOUTH);
    }

    private void addColumns(String[] colNames) {
        for (String col : colNames) {
            model.addColumn(col);
        }
    }

    // @param The processes after the scheduling is done
    public void setTableValues(ArrayList<Process> processes, String scheduler, int time) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        for (Process p : processes) {
            model.addRow(p.getValues());
        }

        if (time != -1) {
            upperPanel.schName.setText("Scheduled using: " + scheduler);
            upperPanel.timeSlider.setMaximum(time);
            upperPanel.timeSlider.setValue(time);
            upperPanel.endTime.setText(Integer.toString(time));

            float totalTurn = 0, totalWait = 0;
            for (Process p : processes) {
                totalTurn += p.turnaroundTime;
                totalWait += p.waitingTime;
            }

            String str = 
            "<html>" + "<center>" +
            "Total waiting time: "   + (int) totalWait              + " | Total turnaround time: "   + (int) totalTurn + "<br>" + 
            "Average waiting time: " + totalWait / processes.size() + " | Average turnaround time: " + totalTurn / processes.size() + 
            "</center>" + "</html>";
            totalAvgText.setText(str);
        }
    }

    private class UpperPanel extends JPanel {
        JLabel schName, endTime;
        JSlider timeSlider;

        public UpperPanel() {
            setLayout(new GridLayout(2, 1, 5, 5));
            schName = new JLabel("Scheduled using:", JLabel.CENTER);
            endTime = new JLabel("0");
            timeSlider = new JSlider(0, 50, 0);

            timeSlider.addMouseListener(new SliderMouseListener());

            add(schName);

            JPanel slidePanel = new JPanel();
            slidePanel.setBorder(new EmptyBorder(0, 5, 0, 7));
            slidePanel.setLayout(new BorderLayout(3, 3));
            slidePanel.add(new JLabel("0"), BorderLayout.WEST);
            slidePanel.add(timeSlider, BorderLayout.CENTER);
            slidePanel.add(endTime, BorderLayout.EAST);

            add(slidePanel);
        }
    }

    private class SliderMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            int time = ((JSlider) e.getSource()).getValue();
            myFrame.scheduleTill(time);
        }
    }
}
