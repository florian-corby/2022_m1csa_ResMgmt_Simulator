package simulator;

import loaders.Test;
import scheduler.*;
import java.io.IOException;

public class EnergyAware {
    public EnergyAware(Test test){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%% ENERGY AWARE %%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
        int nbServers = test.getServersLoader().getServers().size();

        // =================================== OUTPUT FILES GENERATION ===========================================
        //Running FIFOe scheduling simulation:
        System.out.println(">>> SCHEDULING USING FIFO");
        FIFOe fifoScheduler = new FIFOe(test, nbServers);
        fifoScheduler.getSchedule().write("../out/" + test.getFileName() + "_energyAware_fifo.txt");
        fifoScheduler.getSchedule().print();

        //Getting and exporting FIFOe metrics:
        Metrics metrics = new Metrics(fifoScheduler.getSchedule());
        metrics.print();
        metrics.writeConsumption("../out/" + test.getFileName() + "_energyAware_fifo_consumption.txt");

//        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
//        EDF edfScheduler = new EDF(test, nbServers);
//        edfScheduler.getSchedule().write("../out/" + test.getFileName() + "_multiServer_edf.txt");
//        edfScheduler.getSchedule().print();
//        new Metrics(edfScheduler.getSchedule()).print();

        // =================================== PYTHON GRAPHS GENERATION ===========================================
        String plotterCmd = "python3 ../lib/etu/savePlot.py ";
        String consPlotCmd = "python3 ../lib/etu/consPlot.py ";
        try {
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_energyAware_fifo.txt");
            Runtime.getRuntime().exec(consPlotCmd + "../out/" + test.getFileName() + "_energyAware_fifo_consumption.txt");
//            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_energyAware_edf.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
