public class CPUProcess extends Thread {

    private final long GENERATION_TIME;
    private static int counter;
    private final int PROCESS_ID;
    private CPUQueue[] cpuQueues;

    CPUProcess(CPUQueue []cpuQueues) {
        PROCESS_ID = counter++;
        GENERATION_TIME = 100;
        this.cpuQueues = cpuQueues;
    }

    public int getProcessId() {
        return PROCESS_ID;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(GENERATION_TIME);
        } catch (InterruptedException ignored) {}
        System.out.println("New Proccess generated: " + this);
    }
}
