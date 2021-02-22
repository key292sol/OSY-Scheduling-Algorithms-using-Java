package schedulers;

public class PriorityScheduler extends Scheduler {
    public PriorityScheduler() {
        super();
        myName = "Priority";
    }

    @Override
    protected void addNewProcess(Process p) {
        if (readyList.isEmpty()) {
            readyList.add(p);
        } else {
            int i = 0, size = readyList.size();
            while (i < size && p.priority >= readyList.get(i).priority) {
                i++;
            }
            readyList.add(i, p);
        }

        if (runningProcess == null) {
            runningProcess = readyList.remove(0);
        } else if (runningProcess.priority > readyList.get(0).priority) {
            Process pro = runningProcess;
            runningProcess = null;
            addNewProcess(pro);
        }
    }
}
