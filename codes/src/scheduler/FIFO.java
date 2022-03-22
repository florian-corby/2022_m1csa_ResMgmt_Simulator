package scheduler;

import components.JobsBatch;
import components.ScheduleEntry;
import components.Server;
import components.Job;
import java.util.Iterator;
import java.util.LinkedList;

public class FIFO extends Scheduler{

    /* ================ CONSTRUCTORS ================ */
    public FIFO(JobsBatch jobsBatch, LinkedList<Server> servers) {
        super(jobsBatch, servers, 0);
    }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(int quantum) {
        Iterator<Job> jobIterator = arrivedJobs.iterator();

        while(jobIterator.hasNext()){
            Job job = jobIterator.next();
            jobIterator.remove();

            double start = ScheduleEntry.computeStart(schedule, servers.getFirst(), job);
            double end = start + job.getUnitsOfWork();
            schedule.currentDate = end;

            ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
            schedule.add(newEntry);
        }
    }

}
