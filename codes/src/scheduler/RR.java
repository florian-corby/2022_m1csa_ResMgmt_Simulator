package scheduler;

import components.Job;
import components.ScheduleEntry;
import loaders.Test;


public class RR extends Scheduler {

    private final int QUANTUM;

    /* ================ CONSTRUCTORS ================ */
    public RR(Test test, int nbServers, int quantum){
        super(test, nbServers); QUANTUM = quantum;
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep() {
        Job job = arrivedJ.removeFirst();

        //We compute next event date:
        double start = ScheduleEntry.computeStart(schedule, serversM.getServers().getFirst(), job);
        double end = ScheduleEntry.computeEnd(job, start, QUANTUM);
        schedule.currentDate = end;

        //We decrement and deal with the finished job:
        job.decrement(QUANTUM);
        ScheduleEntry newEntry = new ScheduleEntry(job, serversM.getServers().getFirst(), start, end,
                                 serversM.getServers().getFirst().getCurrFreq());
        schedule.add(newEntry);

        //We deal with new arrivals:
        arrivedJ.addAll(jobsB.getArrivedJobs(end));
        if(!job.isWorkDone()) arrivedJ.add(job);
    }
}
