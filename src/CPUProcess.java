public class CPUProcess extends Thread {

    private final long GENERATION_TIME;

    CPUProcess() {
        GENERATION_TIME = (long)(Math.random() * 500);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(GENERATION_TIME);
        } catch (InterruptedException ignored) {}
        System.out.println("New Proccess generated: " + this + "in " + GENERATION_TIME + " seconds");
    }
}
