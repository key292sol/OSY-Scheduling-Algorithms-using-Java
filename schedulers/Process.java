package schedulers;

public class Process {
    public String name;
    public int burstTime, arrivalTime, priority;
    public int remainingTime;
    public int waitingTime, turnaroundTime;

    public Process(String _name, int burst, int arrTime, int _priority) {
        name = _name;
        burstTime = burst;
        arrivalTime = arrTime;
        priority = _priority;
        reset();
    }

    public void reset() {
        remainingTime = burstTime;
        waitingTime = 0;
        turnaroundTime = 0;
    }

    public Object[] getValues() {
        Object[] vals = {name, burstTime, priority, arrivalTime, waitingTime, turnaroundTime};
        return vals;
    }
}
