public class CPU extends Thread{

    private final static long EXECUTION_TIME;
    private CPUProcess process;
    private static CPUQueue cpuQueue;
    private boolean run = true;

    static {
        EXECUTION_TIME = (long)(Math.random() * 500 + 700);
    }

    public void stopThread() {
        run = false;
    }

    public CPU(CPUQueue cpuQueue) {
        CPU.cpuQueue = cpuQueue;
    }

    public synchronized void requireProcess() {
        if(!cpuQueue.isEmplty()) process = cpuQueue.remove();
    }

    public synchronized void setProcess(CPUProcess process) {
        this.process = process;
    }

    public boolean isFree() {
        return process == null;
    }

    public CPUProcess getProcess() {
        return process;
    }

    @Override
    public void run() {
        System.out.println("-----CPU: " + this + " started with execution time: " + EXECUTION_TIME);
        while (!Thread.interrupted() && run) {
            try {
                if (process != null) {
                    System.out.println("" + this + " started processing of:" + process);
                    Thread.sleep(EXECUTION_TIME);
                    System.out.println("" + this + " finished processing of:" + process);
                    setProcess(null);
                }
                else {
                    requireProcess();
                }
            } catch (InterruptedException ignored) {
                System.out.println("" + this + " has been interrupted");
                cpuQueue.add(process);
                setProcess(null);
            }
        }
        System.out.println("CPU: " + this + " has been terminated");
    }
}
