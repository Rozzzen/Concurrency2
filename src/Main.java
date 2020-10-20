public class Main {


    public static void main(String[] args) throws InterruptedException {

        CPU[] cpu = new CPU[Constant.NUMBER_OF_CPU];
        CPUQueue cpuQueue = new CPUQueue();
        CPUProcess[][] cpuProcess = new CPUProcess[Constant.NUMBER_OF_PROCESS_FLOWS][Constant.NUMBER_OF_PROCESS];
        CPUProcessFlow[] cpuProcessFlows = new CPUProcessFlow[Constant.NUMBER_OF_PROCESS_FLOWS];

        for (int i = 0; i < Constant.NUMBER_OF_CPU; i++) {
            cpu[i] = new CPU(cpuQueue);
            cpu[i].start();
        }
        for (int i = 0; i < Constant.NUMBER_OF_PROCESS_FLOWS; i++) {
            cpuProcessFlows[i] = new CPUProcessFlow(cpu, cpuQueue, cpuProcess);
            cpuProcessFlows[i].start();
        }
        for (int i = 0; i < Constant.NUMBER_OF_PROCESS_FLOWS; i++) cpuProcessFlows[i].join();

        while((cpu[0].isAlive() || cpu[1].isAlive())) {
            if(!cpu[0].isFree() && cpuQueue.isEmplty()) cpu[0].stopThread();
            if(!cpu[1].isFree() && cpuQueue.isEmplty()) cpu[1].stopThread();
        }
        System.out.println("----------------------------------------------------------------");
        System.out.println("Processes deleted: " + (double)Constant.DELETED_PROCESSES / Constant.NUMBER_OF_PROCESS * 100 + "%");
        System.out.println("Processes interrupted: " + (double)Constant.INTERRUPTED_PROCESSES / Constant.NUMBER_OF_PROCESS * 100 + "%");
        System.out.println("Max queue length: " + Constant.MAX_LENGTH);
    }
}
