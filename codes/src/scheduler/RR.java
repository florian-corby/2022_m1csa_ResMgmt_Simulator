package scheduler;

import components.Job;
import components.Server;

import java.util.LinkedList;

public class RR extends Scheduler {

    /* ================ CONSTRUCTORS ================ */
    public RR(LinkedList<Job> jobs, Server server, int quantum){ super(jobs, server, quantum); }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(LinkedList<Job> arrivedJobs, Server server, int quantum) {
        Schedule schedule = getSchedule();
        Job job = arrivedJobs.removeFirst();

        double start = ScheduleEntry.computeStart(schedule, job);
        double end = ScheduleEntry.computeEnd(job, start, quantum);
        job.decrementMakespan(quantum);

        ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(newEntry);

        getArrivedJobs();
        if(!job.isWorkDone()) arrivedJobs.add(job);
    }

}
