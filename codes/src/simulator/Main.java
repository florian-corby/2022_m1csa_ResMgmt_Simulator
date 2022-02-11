package simulator;

import loaders.Test;
import scheduler.EDF;
import scheduler.FIFO;
import scheduler.RR;

public class Main {
    public static void main(String[] args) {
        Test test = new Test("../res/inputs/input1_ut3/", "test1.txt");
        test.print();
        System.out.println();

        FIFO fifoScheduler = new FIFO(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0));
        fifoScheduler.write("../out/fifo.txt");
        fifoScheduler.print();
        System.out.println();

        RR rrScheduler = new RR(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0), 2);
        rrScheduler.write("../out/rr.txt");
        rrScheduler.print();
        System.out.println();

        EDF edfScheduler = new EDF(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0), 2);
        edfScheduler.write("../out/edf.txt");
        edfScheduler.print();
        System.out.println();
    }
}
