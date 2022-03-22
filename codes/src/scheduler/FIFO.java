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
        //System.out.print(getSchedule().getLastEntry().getEnd() + ": ");
        //System.out.println(arrivedJobs);
        Iterator<Job> jobIterator = arrivedJobs.iterator();

        while(jobIterator.hasNext()){
            Job job = jobIterator.next();
            jobIterator.remove();

            double start = ScheduleEntry.computeStart(schedule, job);
            double end = start + job.getUnitsOfWork();

            ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
            schedule.add(newEntry);
        }
    }

}
