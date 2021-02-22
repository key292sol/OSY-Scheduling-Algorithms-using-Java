package schedulers;

public class Fcfs extends Scheduler {
    public Fcfs() {
        super();
        myName = "FCFS";
    }
    
    @Override
    protected void addNewProcess(Process p) {
        readyList.add(p);
        if (runningProcess == null) {
            runningProcess = readyList.remove(0);
        }
    }
}
