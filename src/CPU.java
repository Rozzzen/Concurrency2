public class CPU extends Thread{

    private final static long EXECUTION_TIME;
    private CPUProcess process;
    private boolean busy;
    private volatile CPUQueue[] cpuQueues;

    static {
        EXECUTION_TIME = 200;
    }

    public CPU(CPUQueue[] cpuQueues) {
        this.cpuQueues = cpuQueues;
    }

    public synchronized void requireProcess() {
        if(cpuQueues[0].getSize() < 1 && cpuQueues[1].getSize() < 1) return;
        
        if(cpuQueues[0].getSize() > cpuQueues[1].getSize()) setProcess(cpuQueues[0].remove());
        else setProcess(cpuQueues[1].remove());
    }

    public synchronized void setProcess(CPUProcess process) {
        this.process = process;
        busy = true;
    }

    @Override
    public void run() {
        System.out.println("CPU: " + this + " started");
        while (!Thread.interrupted()) {
            try {
                if (busy) {
                    if (process == null) {
                        throw new IllegalArgumentException("something wrong with CPU:" + this);
                    }
                    System.out.println(this + " started processing of:" + process);
                    Thread.sleep(EXECUTION_TIME);
                    System.out.println(this + " finished processing of:" + process);
                    setProcess(null);
                    busy = false;
                }
                else {
                    requireProcess();
                }
            } catch (InterruptedException ignored) {}
        }
    }
}
