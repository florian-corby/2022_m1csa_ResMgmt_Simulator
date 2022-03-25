package simulator;

import components.JobsBatch;
import loaders.ServersLoader;
import loaders.Test;
import scheduler.*;

import java.io.IOException;


public class MonoServer {
    public MonoServer(Test test){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%% MONOSERVER %%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");

        // =================================== OUTPUT FILES GENERATION ===========================================
        JobsBatch loadedBatch = new JobsBatch(test.getJobsLoader().getLoadedJobs());
        ServersLoader serversLoader = test.getServersLoader();

        System.out.println(">>> SCHEDULING USING FIFO");
        FIFO fifoScheduler = new FIFO(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1));
        fifoScheduler.getSchedule().write("../out/" + test.getFileName() + "_monoServer_fifo.txt");
        fifoScheduler.getSchedule().print();
        new Metrics(fifoScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING ROUND ROBIN");
        RR rrScheduler = new RR(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1), 2);
        rrScheduler.getSchedule().write("../out/" + test.getFileName() + "_monoServer_rr.txt");
        rrScheduler.getSchedule().print();
        new Metrics(rrScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
        EDF edfScheduler = new EDF(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1));
        edfScheduler.getSchedule().write("../out/" + test.getFileName() + "_monoServer_edf.txt");
        edfScheduler.getSchedule().print();
        new Metrics(edfScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING RATE MONOTONIC");
        RMS rmsScheduler = new RMS(new JobsBatch(loadedBatch), serversLoader.getServers(0, 1));
        rmsScheduler.getSchedule().write("../out/" + test.getFileName() + "_monoServer_rms.txt");
        rmsScheduler.getSchedule().print();
        new Metrics(rmsScheduler.getSchedule()).print();

        // =================================== PYTHON GRAPHS GENERATION ===========================================
        String plotterCmd = "python3 ../lib/ut3/savePlot.py ";
        try {
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_monoServer_fifo.txt");
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_monoServer_rr.txt");
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_monoServer_edf.txt");
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_monoServer_rms.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
