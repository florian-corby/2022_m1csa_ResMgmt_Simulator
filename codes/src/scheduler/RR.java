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
        Job job = arrivedJobs.removeFirst();

        double start = computeStart(getSchedule(), job);
        double end = computeEnd(job, start, quantum);
        if(!job.isWorkDone()) arrivedJobs.add(job);

        ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
        getSchedule().add(newEntry);
    }

}
