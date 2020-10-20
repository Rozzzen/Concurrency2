public class Main {

    final static int NUMBER_OF_CPU = 2;
    final static int NUMBER_OF_QUEUE = 2;
    final static int NUMBER_OF_PROCESS = 24;

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
            if(cpu[0].isBusy()) cpu[0].setProcess(cpuProcess[i]);
            else if(cpu[1].isBusy()) cpu[1].setProcess(cpuProcess[i]);
            else {
                if(cpuQueue1.getSize() < cpuQueue2.getSize()) {
                    System.out.println("Process " + cpuProcess[i] + " added to 1 queue");
                    cpuQueue1.add(cpuProcess[i]);
                }
                else {
                    System.out.println("Process " + cpuProcess[i] + " added to 2 queue");
                    cpuQueue2.add(cpuProcess[i]);
                }
            }
        }
        while((cpu[0].isAlive() || cpu[1].isAlive())) {
            if(!cpu[0].isBusy() && (cpuQueue1.isEmplty() && cpuQueue2.isEmplty())) cpu[0].stopThread();
            if(!cpu[1].isBusy() && (cpuQueue1.isEmplty() && cpuQueue2.isEmplty())) cpu[1].stopThread();
        }
    }
}
