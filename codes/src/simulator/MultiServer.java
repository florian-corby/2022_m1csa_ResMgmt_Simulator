package simulator;

import components.JobsBatch;
import loaders.Test;
import scheduler.*;


public class MultiServer {
    public MultiServer(Test test){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%% MULTISERVER %%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");

        // ============================================================================================
        JobsBatch loadedBatch = new JobsBatch(test.getJobsLoader().getLoadedJobs());

        System.out.println(">>> SCHEDULING USING FIFO");
        FIFO fifoScheduler = new FIFO(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        fifoScheduler.getSchedule().write("../out/multiServer_fifo.txt");
        fifoScheduler.getSchedule().print();
        new Metrics(fifoScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING ROUND ROBIN");
        RR rrScheduler = new RR(new JobsBatch(loadedBatch), test.getServersLoader().getServers(), 2);
        rrScheduler.getSchedule().write("../out/multiServer_rr.txt");
        rrScheduler.getSchedule().print();
        new Metrics(rrScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
        EDF edfScheduler = new EDF(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        edfScheduler.getSchedule().write("../out/multiServer_edf.txt");
        edfScheduler.getSchedule().print();
        new Metrics(edfScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING RATE MONOTONIC");
        RMS rmsScheduler = new RMS(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        rmsScheduler.getSchedule().write("../out/multiServer_rms.txt");
        rmsScheduler.getSchedule().print();
        new Metrics(rmsScheduler.getSchedule()).print();
        // ============================================================================================

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
