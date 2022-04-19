package contextSwitching;

public class SimProcess{
    private int pid;
    private String procName;
    private int totalInstructions;

    public SimProcess(int id, String pName, int totInst){
        pid = id;
        procName = pName;
        totalInstructions = totInst;
    }

    public ProcessState execute(int i){
        double random = Math.random() * 100;
        System.out.println("PID: " + pid + ", Process: " + procName + ", Instruction Number: " + i + ". ");
        if(i >= totalInstructions){
            return ProcessState.FINISHED;
        }else if(random < 15){
            return ProcessState.BLOCKED;
        }else{
            return ProcessState.READY;
        }
    }

    public int getTotalInstructions() {
        return totalInstructions;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public void setTotalInstructions(int totalInstructions) {
        this.totalInstructions = totalInstructions;
    }
}
