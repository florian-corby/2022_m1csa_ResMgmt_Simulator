package scheduler;

import loaders.Test;

public class FIFO extends SchedulerQuantum{
    /* ================ CONSTRUCTORS ================ */
    public FIFO(Test test, int nbServers) {
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

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double unitsOfWorkDone = nextEventDate - schedule.currentDate;
        schedule.currentDate += unitsOfWorkDone;

        //We decrement and deal with finished jobs:
        serversM.decrementAll(unitsOfWorkDone);

        //We deal with new arrivals:
        arrivedJ.addAll(jobsB.getArrivedJobs(nextEventDate));
        assignArrivals();
    }
}
