import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CPUQueue {

    private final Queue<CPUProcess>queue;
    private final int MAX_SIZE = 25;

    CPUQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public Queue<CPUProcess> getQueue() {
        return queue;
    }

    public int getSize() {
        return queue.size();
    }

    public boolean isEmplty() {
        return queue.isEmpty();
    }

    public synchronized void add(CPUProcess process) {
        if (queue == null || process == null) {
            throw new IllegalArgumentException();
        }
        if (queue.size() > MAX_SIZE) {
            System.out.println("Queue overflow");
        }
        queue.add(process);
    }

    public synchronized CPUProcess remove() {
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        return queue.remove();
    }
}
