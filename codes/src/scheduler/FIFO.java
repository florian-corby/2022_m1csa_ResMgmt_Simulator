package scheduler;

import components.Server;
import components.Job;

import java.util.Iterator;
import java.util.LinkedList;

public class FIFO extends Scheduler{

    /* ================ CONSTRUCTORS ================ */
    public FIFO(LinkedList<Job> jobs, LinkedList<Server> servers) {
        super(jobs, servers, 0);
    }

    /* ================ SETTERS ================ */
    @Override
    public void assignNextJob() {
        if(getJobsBatch().isEmpty()) return;
        LinkedList<Server> servers = getServers();
        LinkedList<Job> jobsBatch = getJobsBatch();
        servers.getFirst().getAssignedJobs().add(jobsBatch.removeFirst());
    }

    @Override
    public void runScheduleStep(int quantum) {
        LinkedList<Server> servers = getServers();
        Job job = servers.getFirst().getAssignedJobs().removeFirst();
        Schedule schedule = getSchedule();

        double start = ScheduleEntry.computeStart(schedule, job);
        double end = start + job.getUnitsOfWork();

        ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
        schedule.add(newEntry);
    }
}
