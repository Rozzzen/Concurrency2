import java.util.Arrays;

public class Main {

    final static int NUMBER_OF_CPU = 2;
    final static int NUMBER_OF_QUEUE = 2;
    final static int NUMBER_OF_PROCESS = 25;

    public static void main(String[] args) throws InterruptedException {

        CPU[] cpu = new CPU[NUMBER_OF_CPU];
        CPUQueue[] cpuQueue = new CPUQueue[NUMBER_OF_QUEUE];
        CPUProcess[] cpuProcess = new CPUProcess[NUMBER_OF_PROCESS];


        for (int i = 0; i < NUMBER_OF_QUEUE; i++) {
            cpuQueue[i] = new CPUQueue();
        }

        CPUQueue cpuQueue1 = cpuQueue[0];
        CPUQueue cpuQueue2 = cpuQueue[1];

        for (int i = 0; i < NUMBER_OF_CPU; i++) {
            cpu[i] = new CPU(cpuQueue);
            cpu[i].start();
        }
        for (int i = 0; i < NUMBER_OF_PROCESS; i++) {
            cpuProcess[i] = new CPUProcess();
            cpuProcess[i].start();
            cpuProcess[i].join();
            if(cpuQueue1.getSize() < cpuQueue2.getSize()) cpuQueue1.add(cpuProcess[i]);
            else cpuQueue2.add(cpuProcess[i]);
        }
    }
}
