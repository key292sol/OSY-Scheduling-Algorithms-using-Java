package schedulers;

public class Sjf extends Scheduler {
    public Sjf() {
        super();
        myName = "SJF";
        changeRunning();
    }

    @Override
    protected void addNewProcess(Process p) {
        if (readyList.isEmpty()) {
            readyList.add(p);
        } else {
            int i = 0, size = readyList.size();
            while (i < size && p.remainingTime >= readyList.get(i).remainingTime) {
                i++;
            }
            readyList.add(i, p);
        }

        if (runningProcess == null) {
            runningProcess = readyList.remove(0);
        } else if (runningProcess.remainingTime > readyList.get(0).remainingTime) {
            Process pro = runningProcess;
            runningProcess = null;
            addNewProcess(pro);
        }
    }
}
