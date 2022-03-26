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
        Job job = arrivedJobs.removeFirst();

        double start = ScheduleEntry.computeStart(schedule, servers.getFirst(), job);
        double end = ScheduleEntry.computeEnd(job, start, QUANTUM);
        schedule.currentDate = end;
        job.decrement(QUANTUM);

        ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
        schedule.add(newEntry);

        arrivedJobs.addAll(jobsBatch.getArrivedJobs(end));
        if(!job.isWorkDone()) arrivedJobs.add(job);
    }
}
