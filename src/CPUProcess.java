public class CPUProcess extends Thread {

    private final long GENERATION_TIME;
    private final int GENERATED_BY;

    public int getGeneratedBy() {
        return GENERATED_BY;
    }

    CPUProcess(int id) {
        GENERATED_BY = id;
        GENERATION_TIME = (long)(Math.random() * 500);
    }

    public long getGenerationTime() {
        return GENERATION_TIME;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(GENERATION_TIME);
        } catch (InterruptedException ignored) {}
    }
}
