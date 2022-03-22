package scheduler;

import components.Job;
import components.JobsBatch;
import components.ScheduleEntry;
import components.Server;

import java.util.Comparator;
import java.util.LinkedList;

public class RMS extends Scheduler {

    /* ================ CONSTRUCTORS ================ */
    public RMS(JobsBatch jobsBatch, LinkedList<Server> servers) { super(jobsBatch, servers, 1); }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(int quantum) {
        arrivedJobs.sort(Comparator.comparingDouble(Job::getPeriod));
        Job job = arrivedJobs.getFirst();

        double start = ScheduleEntry.computeStart(schedule, job);
        double end;
        int nextArrDate = jobsBatch.getNextArrivalDate();
        if(nextArrDate != -1 && (start + job.getUnitsOfWork()) > nextArrDate){
            double unitsOfWorksDone = nextArrDate - start;
            end = start + unitsOfWorksDone;
            job.decrementMakespan((int) unitsOfWorksDone);
        }
        else{
            end = start + job.getUnitsOfWork();
            arrivedJobs.removeFirst();
        }

        ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
        schedule.add(newEntry);
        arrivedJobs.addAll(jobsBatch.getArrivedJobs(end));
    }
}
