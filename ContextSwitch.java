package contextSwitching;

import java.util.ArrayList;
import java.util.Iterator;

public class ContextSwitch {

    //creates variables to be accessed in all methods
    final static int QUANTUM = 5;
    private static ArrayList<ProcessControlBlock> blocked = new ArrayList<>();
    private static ArrayList<ProcessControlBlock> ready = new ArrayList<>();

    public static void main(String[] args) {
        //creates 10 programs with pcbs
        SimProcess process1 = new SimProcess(8473, "Adobe", 298);
        ProcessControlBlock pcb1 = new ProcessControlBlock(process1, 0, 0, 0, 0, 0);

        SimProcess process2 = new SimProcess(9330, "Google Chrome", 152);
        ProcessControlBlock pcb2 = new ProcessControlBlock(process2, 0, 0, 0, 0, 0);

        SimProcess process3 = new SimProcess(6411, "McAfee Antivirus", 396);
        ProcessControlBlock pcb3 = new ProcessControlBlock(process3, 0, 0, 0, 0, 0);

        SimProcess process4 = new SimProcess(7793, "Safari", 183);
        ProcessControlBlock pcb4 = new ProcessControlBlock(process4, 0, 0, 0, 0, 0);

        SimProcess process5 = new SimProcess(1428, "IntelliJ", 221);
        ProcessControlBlock pcb5 = new ProcessControlBlock(process5, 0, 0, 0, 0, 0);

        SimProcess process6 = new SimProcess(7539, "File Explorer", 302);
        ProcessControlBlock pcb6 = new ProcessControlBlock(process6, 0, 0, 0, 0, 0);

        SimProcess process7 = new SimProcess(2901, "Weather", 176);
        ProcessControlBlock pcb7 = new ProcessControlBlock(process7, 0, 0, 0, 0, 0);

        SimProcess process8 = new SimProcess(3193, "Location Services", 106);
        ProcessControlBlock pcb8 = new ProcessControlBlock(process8, 0, 0, 0, 0, 0);

        SimProcess process9 = new SimProcess(5610, "Microsoft Edge", 163);
        ProcessControlBlock pcb9 = new ProcessControlBlock(process9, 0, 0, 0, 0, 0);

        SimProcess process10 = new SimProcess(4002, "Windows Media Player", 184);
        ProcessControlBlock pcb10 = new ProcessControlBlock(process10, 0, 0, 0, 0, 0);

        SimProcessor processor = new SimProcessor(process1, 0, 0, 0, 0, 0);

        // adds processes to the ready and blocked arrayLists
        ready.add(pcb1);
        ready.add(pcb2);
        ready.add(pcb3);
        ready.add(pcb4);
        ready.add(pcb5);
        ready.add(pcb6);
        ready.add(pcb7);
        ready.add(pcb8);
        ready.add(pcb9);
        blocked.add(pcb10);

        int quantumCounter = 0;

        //runs the "processor" 3000 times
        for (int i = 1; i <= 3000; i++) {
            System.out.print("Step " + i + ": ");

            ProcessState state = processor.executeNextInstruction();

            quantumCounter++;

            //determines the next step
            if (state == ProcessState.FINISHED) {
                //context switch

                //jumps to the next step, which is removing a process
                wakeUpBlocked();
                i++;
                System.out.println("Step " + i + ": Process Finished. Removing" + processor.toString());
                //updates the pcb
                ProcessControlBlock curPCB = ready.get(0);
                curPCB.setRegisterValue(1, processor.getRegisterValue(1));
                curPCB.setRegisterValue(2, processor.getRegisterValue(2));
                curPCB.setRegisterValue(3, processor.getRegisterValue(3));
                curPCB.setRegisterValue(4, processor.getRegisterValue(4));
                curPCB.setCurInst(processor.getCurInstruction());
                //removes the pcb from ready
                ready.remove(0);

                //checks if processor is idling, if not it restores the process and jumps a step
                if(ready.isEmpty()){
                    wakeUpBlocked();
                    i++;
                    System.out.println("Step " + i + ": Processor Idling");
                }else{
                    ProcessControlBlock newPCB = ready.get(0);
                    processor.setCurProcess(newPCB.getProcess());
                    processor.setRegisterValue(1, newPCB.getRegisterValue(1));
                    processor.setRegisterValue(2, newPCB.getRegisterValue(2));
                    processor.setRegisterValue(3, newPCB.getRegisterValue(3));
                    processor.setRegisterValue(4, newPCB.getRegisterValue(4));
                    processor.setCurInstruction(newPCB.getCurInst());
                    wakeUpBlocked();
                    i++;
                    System.out.println("Step " + i + ": Restoring Process: " + processor.toString());
                }
                quantumCounter = 0;
            } else if (state == ProcessState.BLOCKED) {
                //context switch

                //jumps to the next step, which is removing a process
                wakeUpBlocked();
                i++;
                System.out.println("Step " + i + ": Process Blocked. Removing " + processor.toString());
                //updates the pcb
                ProcessControlBlock curPCB = ready.get(0);
                curPCB.setRegisterValue(1, processor.getRegisterValue(1));
                curPCB.setRegisterValue(2, processor.getRegisterValue(2));
                curPCB.setRegisterValue(3, processor.getRegisterValue(3));
                curPCB.setRegisterValue(4, processor.getRegisterValue(4));
                curPCB.setCurInst(processor.getCurInstruction());
                //add pcb to blocked and removes it from ready
                blocked.add(curPCB);
                ready.remove(0);
                // either the processor idles or it restores a new process
                if(ready.isEmpty()){
                    wakeUpBlocked();
                    i++;
                    System.out.println("Step " + i + ": Processor Idling");
                }else{
                    ProcessControlBlock newPCB = ready.get(0);
                    processor.setCurProcess(newPCB.getProcess());
                    processor.setRegisterValue(1, newPCB.getRegisterValue(1));
                    processor.setRegisterValue(2, newPCB.getRegisterValue(2));
                    processor.setRegisterValue(3, newPCB.getRegisterValue(3));
                    processor.setRegisterValue(4, newPCB.getRegisterValue(4));
                    processor.setCurInstruction(newPCB.getCurInst());
                    wakeUpBlocked();
                    i++;
                    System.out.println("Step " + i + ": Restoring Process: " + processor.toString());
                }
                quantumCounter = 0;
            } else if (quantumCounter >= QUANTUM) {
                //context switch

                //jumps a step and removes a process
                wakeUpBlocked();
                i++;
                System.out.println("Step " + i + ": Quantum Expired. Removing " + processor.toString());
                //updates pcb
                ProcessControlBlock curPCB = ready.get(0);
                curPCB.setRegisterValue(1, processor.getRegisterValue(1));
                curPCB.setRegisterValue(2, processor.getRegisterValue(2));
                curPCB.setRegisterValue(3, processor.getRegisterValue(3));
                curPCB.setRegisterValue(4, processor.getRegisterValue(4));
                curPCB.setCurInst(processor.getCurInstruction());
                //put the pcb at the end of the ready arrayList
                ready.add(curPCB);
                ready.remove(0);

                // either the processor idles or it restores a new process
                if(ready.isEmpty()){
                    wakeUpBlocked();
                    i++;
                    System.out.println("Step " + i + ": Processor Idling");
                }else{
                    ProcessControlBlock newPCB = ready.get(0);
                    processor.setCurProcess(newPCB.getProcess());
                    processor.setRegisterValue(1, newPCB.getRegisterValue(1));
                    processor.setRegisterValue(2, newPCB.getRegisterValue(2));
                    processor.setRegisterValue(3, newPCB.getRegisterValue(3));
                    processor.setRegisterValue(4, newPCB.getRegisterValue(4));
                    processor.setCurInstruction(newPCB.getCurInst());
                    wakeUpBlocked();
                    i++;
                    System.out.println("Step " + i + ": Restoring Process: " + processor.toString());
                }
                quantumCounter = 0;
            } else {
                //wakes up blocked
                wakeUpBlocked();
            }
        }
    }

    //wakes up processes on the ready list with 30% probability
    public static void wakeUpBlocked(){
        Iterator<ProcessControlBlock> iterator = blocked.iterator();
        while(iterator.hasNext()){
            ProcessControlBlock pcb = iterator.next();
            double random = Math.random() * 10;
            if (random >= 7) {
                ready.add(pcb);
                iterator.remove();
            }
        }
    }

}

