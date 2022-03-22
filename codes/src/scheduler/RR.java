package scheduler;

import components.Job;
import components.JobsBatch;
import components.ScheduleEntry;
import components.Server;
import java.util.LinkedList;

public class RR extends Scheduler {

    /* ================ CONSTRUCTORS ================ */
    public RR(JobsBatch jobsBatch, LinkedList<Server> servers, int quantum){ super(jobsBatch, servers, quantum); }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(int quantum) {
        Job job = arrivedJobs.removeFirst();

        double start = ScheduleEntry.computeStart(schedule, servers.getFirst(), job);
        double end = ScheduleEntry.computeEnd(job, start, quantum);
        schedule.currentDate = end;
        job.decrement(quantum);

        ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
        schedule.add(newEntry);

        arrivedJobs.addAll(jobsBatch.getArrivedJobs(end));
        if(!job.isWorkDone()) arrivedJobs.add(job);
    }
}
