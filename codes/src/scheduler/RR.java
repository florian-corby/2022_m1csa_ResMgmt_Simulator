package scheduler;

import components.Job;
import components.JobsBatch;
import components.ScheduleEntry;
import components.Server;
import java.util.LinkedList;

public class RR extends Scheduler {

    private final int QUANTUM;

    /* ================ CONSTRUCTORS ================ */
    public RR(JobsBatch jobsBatch, LinkedList<Server> servers, int quantum){
        super(jobsBatch, servers); QUANTUM = quantum;
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
