package contextSwitching;

public class SimProcessor {
    private SimProcess curProcess;
    private int[] regs = new int[4];
    private int curInstruction;

    public SimProcessor(SimProcess proc, int a, int b, int c, int d, int instr){
        setCurProcess(proc);
        setRegisterValue(1, a);
        setRegisterValue(2, b);
        setRegisterValue(3, c);
        setRegisterValue(4, d);
        setCurInstruction(instr);
    }

    public ProcessState executeNextInstruction(){
        ProcessState state = this.curProcess.execute(this.curInstruction);
        this.curInstruction++;
        for(int i = 1; i < 5; i++){
            setRegisterValue(i, (int)(Math.random() * 100));
        }
        return state;
    }

    public SimProcess getCurProcess() {
        return curProcess;
    }

    public void setCurProcess(SimProcess curProcess) {
        this.curProcess = curProcess;
    }

    public int getRegisterValue(int regNum) {
        if(regNum >= 1 && regNum <= 4){
            return regs[regNum - 1];
        }else{
            System.out.println("That is not a valid register.");
            return 000000;
        }
    }

    public void setRegisterValue(int regNum, int value) {
        if(regNum >= 1 && regNum <= 4){
            this.regs[regNum - 1] = value;
        }else{
            System.out.println("That is not a valid register.");
        }
    }

    public int getCurInstruction() {
        return curInstruction;
    }

    public void setCurInstruction(int curInstruction) {
        this.curInstruction = curInstruction;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Process: " + getCurProcess().getProcName() + ". ");
        builder.append("Register 1: " + getRegisterValue(1) + ". ");
        builder.append("Register 2: " + getRegisterValue(2) + ". ");
        builder.append("Register 3: " + getRegisterValue(3) + ". ");
        builder.append("Register 4: " + getRegisterValue(4) + ". ");
        builder.append("Current Instruction: " + getCurInstruction() + ". ");
        return builder.toString();
    }
}
