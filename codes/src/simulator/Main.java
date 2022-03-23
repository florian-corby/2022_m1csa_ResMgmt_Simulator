package simulator;

import components.JobsBatch;
import loaders.Test;
import scheduler.*;


public class Main {
    public static void main(String[] args) {
        Test test = new Test("../res/inputs/ut3/", "test.txt");
        JobsBatch loadedBatch = new JobsBatch(test.getJobsLoader().getLoadedJobs());
        test.print();

        System.out.println(">>> SCHEDULING USING FIFO");
        FIFO fifoScheduler = new FIFO(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        fifoScheduler.getSchedule().write("../out/fifo.txt");
        fifoScheduler.getSchedule().print();
        new Metrics(fifoScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING ROUND ROBIN");
        RR rrScheduler = new RR(new JobsBatch(loadedBatch), test.getServersLoader().getServers(), 2);
        rrScheduler.getSchedule().write("../out/rr.txt");
        rrScheduler.getSchedule().print();
        new Metrics(rrScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
        EDF edfScheduler = new EDF(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        edfScheduler.getSchedule().write("../out/edf.txt");
        edfScheduler.getSchedule().print();
        new Metrics(edfScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING RATE MONOTONIC");
        RMS rmsScheduler = new RMS(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        rmsScheduler.getSchedule().write("../out/rms.txt");
        rmsScheduler.getSchedule().print();
        new Metrics(rmsScheduler.getSchedule()).print();
    }
}
