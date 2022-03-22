package scheduler;

import components.Job;
import components.JobsBatch;
import components.Server;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BiPredicate;

public class RMS extends SchedulerPriority {
    private static final Comparator<Job> JOBS_COMPARISON_KEY = Comparator.comparingDouble(Job::getPeriod);
    private static final BiPredicate<Job, Job> JOBS_COMPARISON_PREDICATE = (Job j1, Job j2) -> j1.getPeriod() < j2.getPeriod();

    /* ================ CONSTRUCTORS ================ */
    public RMS(JobsBatch jobsBatch, LinkedList<Server> servers){ super(jobsBatch, servers); }

    /* ================ SETTERS ================ */
    @Override
    protected void runScheduleStep(int quantum) {
        arrivedJobs.sort(JOBS_COMPARISON_KEY);
        if(areAllServersIdle() && !arrivedJobs.isEmpty()) initServers(JOBS_COMPARISON_KEY);

        //We compute next event date:
        int nextEventDate = getNextEventDate();
        int unitsOfWorkDone = nextEventDate - (int) schedule.currentDate;

        //We decrement and deal with finished jobs:
        decrementAll(unitsOfWorkDone);

        //We deal with new arrivals:
        arrivedJobs.addAll(jobsBatch.getArrivedJobs(nextEventDate));
        arrivedJobs.sort(JOBS_COMPARISON_KEY);
        assignArrivals(JOBS_COMPARISON_KEY, JOBS_COMPARISON_PREDICATE);
    }
}
