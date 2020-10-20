public class CPUProcessFlow extends Thread {

    private static CPU[] cpu;
    private static CPUQueue cpuQueue;
    private static CPUProcess[][] cpuProcess;
    private static int counter = 0;
    private final int id = counter++;

    public int getThreadId() {
        return id;
    }

    CPUProcessFlow(CPU[] cpu, CPUQueue cpuQueue, CPUProcess[][] cpuProcess) {
        CPUProcessFlow.cpu = cpu;
        CPUProcessFlow.cpuQueue = cpuQueue;
        CPUProcessFlow.cpuProcess = cpuProcess;
    }

    public void setMaxLength() {
        if(cpuQueue.getSize() > Constant.MAX_LENGTH) Constant.MAX_LENGTH = cpuQueue.getSize();
    }

    @Override
    public void run() {
        System.out.println("-----Process flow #" + id + ": " + this + " started");
        for (int i = 0; i < Constant.NUMBER_OF_PROCESS; i++) {
            cpuProcess[id][i] = new CPUProcess(getThreadId());
            cpuProcess[id][i].start();
            try {
                cpuProcess[id][i].join();
                System.out.println("-----------------------------------New Proccess generated: " + cpuProcess[id][i] + " in " + cpuProcess[id][i].getGenerationTime() + " seconds by " + id + " thread");
            } catch (InterruptedException ignored) {}
            if(id == 1) { //vtoroi potok
                if(cpu[1].isFree()) cpu[1].setProcess(cpuProcess[id][i]);
                else {
                    cpuQueue.add(cpuProcess[id][i]);
                    setMaxLength();
                }
            }
            else if(id == 0) { //perviy potok
                if(cpu[0].isFree()) cpu[0].setProcess(cpuProcess[id][i]);
                else if(cpu[0].getProcess().getGeneratedBy() == 1) {
                    cpu[0].interrupt();
                    Constant.INTERRUPTED_PROCESSES++;
                }
                else if(cpu[1].isFree()) cpu[1].setProcess(cpuProcess[id][i]);
                else {
                    System.out.println("Process " + cpuProcess[id][i] + " has been deleted");
                    cpuProcess[id][i] = null;
                    Constant.DELETED_PROCESSES++;
                }
            }
        }
    }
}
