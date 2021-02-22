package schedulers;

import ui.AllProcessInputs;
import ui.ProcessInfoPanel;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public abstract class Scheduler {
    protected ArrayList<Process> readyList, nextQueue;
    protected Process runningProcess;
    
    public int curTime;
    public String myName;
    public ArrayList<Process> originalOrder;

    public Scheduler() {
        readyList = new ArrayList<>();
        nextQueue = new ArrayList<>();
        originalOrder = new ArrayList<>();
    }

    public void setProcesses(AllProcessInputs inputter) {
        try {
            createProcesses(inputter.processInfos);
        } catch (NegativeValueException e) {
            someExceptionOccured("Negative values not allowed");
        } catch (NumberFormatException e) {
            someExceptionOccured("Number format exception occured: " + e.getMessage());
        } catch (NonameException e) {
            someExceptionOccured("No name entered for a process");
        } catch (Exception e) {
            someExceptionOccured("Some error occured in taking the inputs");
        }
    }

    protected void someExceptionOccured(String exception) {
        readyList.clear();
        nextQueue.clear();
        originalOrder.clear();
        JOptionPane.showMessageDialog(null, exception);
    }

    protected void createProcesses(ArrayList<ProcessInfoPanel> processInfos) throws NegativeValueException, NonameException, NumberFormatException {
        for (ProcessInfoPanel infoPanel : processInfos) {
            int burst    = Integer.parseInt(infoPanel.burstTime.getText());
            int arrTime  = Integer.parseInt(infoPanel.arrivalTime.getText());
            int priority = Integer.parseInt(infoPanel.priority.getText());

            if (burst < 0 || arrTime < 0 || priority < 0) {
                throw new NegativeValueException();
            }

            String name = infoPanel.name.getText().trim();
            if (name.length() == 0) {
                throw new NonameException();
            }

            Process p = new Process(name, burst, arrTime, priority);
            originalOrder.add(p);
            nextQueue.add(p);
        }
    }

    // Sort processes in nextQueue according to their arrival time
    protected void sortAccArrival() {
        int size = nextQueue.size();
        for (int i = 0; i < size; i++) {
            Process smallest = nextQueue.get(i);
            int index = i;
            for (int j = i+1; j < size; j++) {
                if (smallest.arrivalTime > nextQueue.get(j).arrivalTime) {
                    smallest = nextQueue.get(j);
                    index = j;
                }
            }
            Process p = nextQueue.get(i);
            nextQueue.set(i, smallest);
            nextQueue.set(index, p);
        }
    }
    
    // Add new process to the ready list and sort it
    protected abstract void addNewProcess(Process p);

    // Goto the next step in time and do the scheduling
    public void nextStep() {
        while (!nextQueue.isEmpty() && nextQueue.get(0).arrivalTime == curTime) {
            addNewProcess(nextQueue.remove(0));
        }

        if (shouldChangeRunning()) {
            changeRunning();
        }
        incrementTimes();
    }

    protected boolean shouldChangeRunning() {
        return (runningProcess != null && runningProcess.remainingTime == 0);
    }

    protected void changeRunning() {
        runningProcess = (readyList.isEmpty()) ? null : readyList.remove(0);
    }

    protected void incrementTimes() {
        curTime++;

        if (runningProcess != null) {
            runningProcess.turnaroundTime++;
            runningProcess.remainingTime--;
        }

        for (Process p : readyList) {
            p.waitingTime++;
            p.turnaroundTime++;
        }
    }

    public void allSteps() {
        while (!nextQueue.isEmpty() || !readyList.isEmpty() || runningProcess != null) {
            nextStep();
        }
    }

    public void scheduleTill(int time) {
        readyList.clear();
        nextQueue.clear();
        runningProcess = null;

        for (Process process : originalOrder) {
            nextQueue.add(process);
            process.reset();
        }

        curTime = 0;
        sortAccArrival();

        while (curTime < time) {
            nextStep();
        }
    }

    public ArrayList<Process> getSeenProcesses() {
        ArrayList<Process> pros = new ArrayList<>();
        for (Process process : originalOrder) {
            if (!nextQueue.contains(process)) {
                pros.add(process);
            }
        }
        return pros;
    }
}


class NegativeValueException extends NumberFormatException {
    private static final long serialVersionUID = 1L;
}
class NonameException extends Exception {
    private static final long serialVersionUID = 1L;
}