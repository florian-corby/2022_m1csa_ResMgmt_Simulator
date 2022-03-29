package scheduler;

import components.Job;
import components.ScheduleEntry;
import components.Server;
import loaders.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiPredicate;

public abstract class SchedulerPriority extends Scheduler {
    /* ================ CONSTRUCTORS ================ */
    public SchedulerPriority(Test test, int nbServers) {
        super(test, nbServers);
    }

    /* ================ SETTERS ================ */
    protected void assignArrivals(Comparator<Job> jobsComparKey, BiPredicate<Job, Job> jobsComparPredicate){
        boolean isAssignable = true;
        boolean isAssigned;
        Iterator<Job> jobIterator = arrivedJ.iterator();

        while(jobIterator.hasNext() && isAssignable){
            Job j = jobIterator.next();
            isAssigned = serversM.assignToIdle(j);

            if(isAssigned) jobIterator.remove();
            else{
                Server servP = getPreemptable(j, jobsComparPredicate);
                if(servP == null) isAssignable = false;
                else{
                    double start = ScheduleEntry.computeStart(schedule, servP, servP.getRunningJob());
                    schedule.add(new ScheduleEntry(servP.getRunningJob(), servP, start, schedule.currentDate, servP.getCurrFreq()));
                    servP.getAssignedJobs().add(j);
                    servP.getAssignedJobs().sort(jobsComparKey);
                    jobIterator.remove();
                }
            }
        }
    }

    private Server getPreemptable(Job j, BiPredicate<Job, Job> jobsComparPredicate){
        Server res = null;
        for(Server s : serversM.getServers()){
            if(!jobsComparPredicate.test(s.getRunningJob(), j)){
                if(res == null || !jobsComparPredicate.test(s.getRunningJob(), res.getRunningJob()))
                    res = s;
            }
        }
        return res;
    }
}
