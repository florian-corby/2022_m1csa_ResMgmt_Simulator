package simulator;

import loaders.Test;
import scheduler.EDF;
import scheduler.FIFO;
import scheduler.RMS;
import scheduler.RR;


public class Main {
    public static void main(String[] args) {
        Test test = new Test("../res/inputs/ut3/", "test.txt");
        test.print();
        System.out.println();

        System.out.println(">>> SCHEDULING USING FIFO");
        FIFO fifoScheduler = new FIFO(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0));
        fifoScheduler.write("../out/fifo.txt");
        fifoScheduler.print();
        System.out.println();

        System.out.println(">>> SCHEDULING USING ROUND ROBIN");
        RR rrScheduler = new RR(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0), 2);
        rrScheduler.write("../out/rr.txt");
        rrScheduler.print();
        System.out.println();

        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
        EDF edfScheduler = new EDF(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0), 2);
        edfScheduler.write("../out/edf.txt");
        edfScheduler.print();
        System.out.println();

        System.out.println(">>> SCHEDULING USING RATE MONOTONIC");
        RMS rmsScheduler = new RMS(test.getJobsLoader().copyJobs(), test.getServersLoader().getServer(0));
        rmsScheduler.write("../out/rms.txt");
        rmsScheduler.print();
        System.out.println();
    }
}
