package schedulers;

import ui.AllProcessInputs;

public class RoundRobin extends Scheduler {
    private int quantumTime, curProcessTime;

    @Override
    public void setProcesses(AllProcessInputs inputter) {
        super.setProcesses(inputter);
        this.setQuantumTime(inputter);
    }

    @Override
    protected boolean shouldChangeRunning() {
        return super.shouldChangeRunning() || curProcessTime == 0;
    }

    @Override
    protected void incrementTimes() {
        super.incrementTimes();
        curProcessTime--;
    }

    @Override
    protected void changeRunning() {
        if (runningProcess.remainingTime > 0) {
            readyList.add(runningProcess);
        }
        curProcessTime = quantumTime;
        super.changeRunning();
    }

    @Override
    protected void addNewProcess(Process p) {
        if (readyList.size() < 2) {
            readyList.add(p);
        } else {
            int i = 1;
            while (i < readyList.size() && readyList.get(i).arrivalTime >= readyList.get(i-1).arrivalTime) {
                i++;
            }
            readyList.add(i, p);
        }

        if (runningProcess == null) {
            runningProcess = readyList.remove(0);
        }
    }

    private void setQuantumTime(AllProcessInputs inputter) {
        String q = inputter.quantumTime.getText().trim();
        if (q.equals("")) {
            someExceptionOccured("No Quantum Time entered");
            myName = "";
        } else {
            quantumTime = Integer.parseInt(q);
            curProcessTime = quantumTime;
            myName = "Round Robin, Quantum Time = " + quantumTime;
        }
    }
}
