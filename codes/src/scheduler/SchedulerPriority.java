package scheduler;

import components.Job;
import components.JobsBatch;
import components.ScheduleEntry;
import components.Server;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiPredicate;

public abstract class SchedulerPriority extends Scheduler {
    /* ================ CONSTRUCTORS ================ */
    public SchedulerPriority(JobsBatch argJobsBatch, LinkedList<Server> argServers) {
        super(argJobsBatch, argServers, 0);
    }

    /* ================ SETTERS ================ */
    protected void assignArrivals(Comparator<Job> jobsComparKey, BiPredicate<Job, Job> jobsComparPredicate){
        boolean isAssignable = true;
        boolean isAssigned;
        Iterator<Job> jobIterator = arrivedJobs.iterator();

        while(jobIterator.hasNext()){
            if(!isAssignable) break;

            Job j = jobIterator.next();
            isAssigned = assignToIdle(j);

            if(isAssigned) jobIterator.remove();
            else{
                Server servP = getPreemptable(j, jobsComparPredicate);
                if(servP == null) { isAssignable = false; }
                else{
                    double start = ScheduleEntry.computeStart(schedule, servP, servP.getRunningJob());
                    schedule.add(new ScheduleEntry(servP.getRunningJob(), servP, start, schedule.currentDate, servP.getFreq(0)));
                    servP.getAssignedJobs().add(j);
                    servP.getAssignedJobs().sort(jobsComparKey);
                    jobIterator.remove();
                }
            }
        }
    }

    private Server getPreemptable(Job j, BiPredicate<Job, Job> jobsComparPredicate){
        Server res = null;
        for(Server s : servers){
            if(!jobsComparPredicate.test(s.getRunningJob(), j)){
                if(res == null || !jobsComparPredicate.test(s.getRunningJob(), res.getRunningJob()))
                    res = s;
            }
        }
        return res;
    }

    protected void initServers(Comparator<Job> c){
        schedule.currentDate = arrivedJobs.getFirst().getArrivalDate();
        Iterator<Job> jobIterator = arrivedJobs.iterator();
        int counter = 0;

        while(jobIterator.hasNext() && counter < servers.size()){
            servers.get(counter).getAssignedJobs().add(jobIterator.next());
            servers.get(counter).getAssignedJobs().sort(c);
            jobIterator.remove();
            counter++;
        }
    }
}
