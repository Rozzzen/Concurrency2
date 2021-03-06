public class CPU extends Thread{

    private final static long EXECUTION_TIME;
    private CPUProcess process;
    private static CPUQueue[] cpuQueues;
    private boolean run = true;

    static {
        EXECUTION_TIME = (long)(Math.random() * 500 + 700);
    }

    public void stopThread() {
        run = false;
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
        System.out.println("CPU: " + this + " started with execution time: " + EXECUTION_TIME);
        while (!Thread.interrupted() && run) {
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
            } catch (InterruptedException ignored) {
                System.out.println("An unexpected error occured");
                return;
            }
        }
        System.out.println("CPU: " + this + " has been terminated");
    }
}
