package contextSwitching;

public class ProcessControlBlock {
    private SimProcess process;
    private int curInst;
    private int[] regs = new int[4];

    public ProcessControlBlock(SimProcess proc, int cInst, int r1, int r2, int r3, int r4){
        this.process = proc;
        setCurInst(cInst);
        setRegisterValue(1, r1);
        setRegisterValue(2, r2);
        setRegisterValue(3, r3);
        setRegisterValue(4, r4);
    }

    public SimProcess getProcess(){
        return this.process;
    }

    public int getCurInst() {
        return curInst;
    }

    public void setCurInst(int curInst) {
        this.curInst = curInst;
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

}
