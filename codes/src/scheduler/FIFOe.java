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

        //We compute next event date at minimum frequencies and
        //manage late jobs by increasing frequencies if it's possible:
        double nextEventDate = getNextEventDate();
        serversM.setFreqs(nextEventDate);

        //We agree on next event:
        nextEventDate = getNextEventDate();
        double duration = nextEventDate - schedule.currentDate;
        schedule.currentDate += duration;

        //We decrement and deal with finished jobs:
        serversM.decrementAll(duration);

        //We deal with new arrivals:
        arrivedJ.addAll(jobsB.getArrivedJobs(nextEventDate));
        assignArrivals();
    }
}
