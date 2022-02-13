package scheduler;

import components.Server;
import components.Job;

import java.util.Iterator;
import java.util.LinkedList;

public class FIFO extends Scheduler{

    /* ================ CONSTRUCTORS ================ */
    public FIFO(LinkedList<Job> jobs, Server server) {
        super(jobs, server, 0);
    }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(LinkedList<Job> arrivedJobs, Server server, int quantum) {
        Iterator<Job> jobIterator = arrivedJobs.iterator();

        while(jobIterator.hasNext()){
            Job job = jobIterator.next();
            jobIterator.remove();

            double start = computeStart(getSchedule(), job);
            double end = start + job.getUnitsOfWork();

            ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
            getSchedule().add(newEntry);
        }
    }

}
