package scheduler;

import components.Job;
import loaders.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;

public class EDF extends SchedulerPriority {
    private static final Comparator<Job> JOBS_COMPARISON_KEY = Comparator.comparingDouble(Job::getADeadline);
    private static final BiPredicate<Job, Job> JOBS_COMPARISON_PREDICATE = (Job j1, Job j2) -> j1.getADeadline() < j2.getADeadline();

    /* ================ CONSTRUCTORS ================ */
    public EDF(Test test, int nbServers){
        super(test, nbServers);
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    protected void runScheduleStep() {
        arrivedJobs.sort(JOBS_COMPARISON_KEY);
        if(areAllServersIdle() && !arrivedJobs.isEmpty()){
            schedule.currentDate = arrivedJobs.getFirst().getArrivalDate();
            initServers();
        }

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double unitsOfWorkDone = nextEventDate - schedule.currentDate;
        schedule.currentDate += unitsOfWorkDone;

        //We decrement and deal with finished jobs:
        decrementAll(unitsOfWorkDone);

        //We deal with new arrivals:
        arrivedJobs.addAll(jobsBatch.getArrivedJobs(nextEventDate));
        arrivedJobs.sort(JOBS_COMPARISON_KEY);
        assignArrivals(JOBS_COMPARISON_KEY, JOBS_COMPARISON_PREDICATE);
    }
}
