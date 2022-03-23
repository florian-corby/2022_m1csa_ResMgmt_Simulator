package simulator;

import components.JobsBatch;
import loaders.ServersLoader;
import loaders.Test;
import scheduler.*;


public class MonoServer {
    public MonoServer(Test test){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%% MONOSERVER %%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");

        // ============================================================================================
        JobsBatch loadedBatch = new JobsBatch(test.getJobsLoader().getLoadedJobs());
        ServersLoader serversLoader = test.getServersLoader();

        System.out.println(">>> SCHEDULING USING FIFO");
        FIFO fifoScheduler = new FIFO(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1));
        fifoScheduler.getSchedule().write("../out/monoServer_fifo.txt");
        fifoScheduler.getSchedule().print();
        new Metrics(fifoScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING ROUND ROBIN");
        RR rrScheduler = new RR(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1), 2);
        rrScheduler.getSchedule().write("../out/monoServer_rr.txt");
        rrScheduler.getSchedule().print();
        new Metrics(rrScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
        EDF edfScheduler = new EDF(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1));
        edfScheduler.getSchedule().write("../out/monoServer_edf.txt");
        edfScheduler.getSchedule().print();
        new Metrics(edfScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING RATE MONOTONIC");
        RMS rmsScheduler = new RMS(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1));
        rmsScheduler.getSchedule().write("../out/monoServer_rms.txt");
        rmsScheduler.getSchedule().print();
        new Metrics(rmsScheduler.getSchedule()).print();
        // ============================================================================================

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
