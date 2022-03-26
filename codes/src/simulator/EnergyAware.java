package simulator;

import components.JobsBatch;
import loaders.Test;
import scheduler.*;

import java.io.IOException;

public class EnergyAware {
    public EnergyAware(Test test){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%% ENERGY AWARE %%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");

        // =================================== OUTPUT FILES GENERATION ===========================================
        JobsBatch loadedBatch = new JobsBatch(test.getJobsLoader().getLoadedJobs());

        System.out.println(">>> SCHEDULING USING FIFO");
        FIFOe fifoScheduler = new FIFOe(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
        fifoScheduler.getSchedule().write("../out/" + test.getFileName() + "_energyAware_fifo.txt");
        fifoScheduler.getSchedule().print();
        new Metrics(fifoScheduler.getSchedule()).print();

//        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
//        EDF edfScheduler = new EDF(new JobsBatch(loadedBatch), test.getServersLoader().getServers());
//        edfScheduler.getSchedule().write("../out/" + test.getFileName() + "_multiServer_edf.txt");
//        edfScheduler.getSchedule().print();
//        new Metrics(edfScheduler.getSchedule()).print();

        // =================================== PYTHON GRAPHS GENERATION ===========================================
        String plotterCmd = "python3 ../lib/ut3/savePlot.py ";
        try {
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_energyAware_fifo.txt");
//            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_energyAware_edf.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
