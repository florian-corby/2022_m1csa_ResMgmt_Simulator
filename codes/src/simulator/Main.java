package simulator;

import loaders.Test;
import scheduler.FIFO;

public class Main {
    public static void main(String[] args) {
        Test test = new Test("../res/inputs/input1_ut3/", "test1.txt");
        test.print();
        System.out.println();

        FIFO fifoScheduler = new FIFO(test.getJobsLoader().getJobs(), test.getServersLoader().getServer(0));
        fifoScheduler.out("../out/fifo.txt");
        fifoScheduler.print();
    }
}
