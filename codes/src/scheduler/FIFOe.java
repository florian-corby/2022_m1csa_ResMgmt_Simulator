package scheduler;

import loaders.Test;


public class FIFOe extends SchedulerQuantum {
    /* ================ CONSTRUCTORS ================ */
    public FIFOe(Test test, int nbServers) {
        super(test, nbServers);
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep() {
        if(serversM.areAllServersIdle() && !arrivedJ.isEmpty()){
            schedule.currentDate = arrivedJ.getFirst().getArrivalDate();
            serversM.initServers();
        }

        //We start with frequencies at minimum:
        serversM.resetFreqs();
        //We increase frequencies if jobs are going to be late from the current date:
        serversM.setFreqs(schedule.currentDate);

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double duration = nextEventDate - schedule.currentDate;
        schedule.currentDate += duration;

        //We decrement and deal with finished jobs:
        serversM.decrementAll(duration);

        //We deal with new arrivals:
        arrivedJ.addAll(jobsB.getArrivedJobs(nextEventDate));
        assignArrivals();
    }
}
