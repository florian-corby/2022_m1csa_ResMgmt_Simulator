package scheduler;

import components.JobsBatch;
import components.Server;

import java.util.LinkedList;

public class FIFOe extends SchedulerQuantum {
    /* ================ CONSTRUCTORS ================ */
    public FIFOe(JobsBatch jobsBatch, LinkedList<Server> servers) {
        super(jobsBatch, servers);
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep() {
        if(areAllServersIdle() && !arrivedJobs.isEmpty()){
            schedule.currentDate = arrivedJobs.getFirst().getArrivalDate();
            initServers();
        }

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double unitsOfWorkDone = nextEventDate - schedule.currentDate;
        schedule.currentDate += unitsOfWorkDone;

        //We decrement and deal with finished jobs:
        decrementAll(unitsOfWorkDone);

        //We deal with new arrivals:
        arrivedJobs.addAll(jobsBatch.getArrivedJobs(nextEventDate));
        assignArrivals();
    }
}
