public class CPU extends Thread{

    private final static long EXECUTION_TIME;
    private CPUProcess process;
    private static CPUQueue[] cpuQueues;

    static {
        EXECUTION_TIME = 500;
    }

    public CPU(CPUQueue[] cpuQueues) {
        CPU.cpuQueues = cpuQueues;
    }

    public synchronized void requireProcess() {
        if(cpuQueues[0].isEmplty() && cpuQueues[1].isEmplty()) return;

        if(cpuQueues[0].getSize() > cpuQueues[1].getSize()) setProcess(cpuQueues[0].remove());
        else setProcess(cpuQueues[1].remove());
    }

    public synchronized void setProcess(CPUProcess process) {
        this.process = process;
    }

    public boolean isBusy() {
        return process == null;
    }

    @Override
    public void run() {
        System.out.println("CPU: " + this + " started");
        while (!Thread.interrupted()) {
            try {
                if (process != null) {
                    System.out.println(this + " started processing of:" + process);
                    Thread.sleep(EXECUTION_TIME);
                    System.out.println(this + " finished processing of:" + process);
                    setProcess(null);
                }
                else {
                    requireProcess();
                }
            } catch (InterruptedException ignored) {}
        }
    }
}
