package scheduler;

import components.Job;
import components.JobsBatch;
import components.Server;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class SchedulerQuantum extends Scheduler {
    /* ================ CONSTRUCTORS ================ */
    public SchedulerQuantum(JobsBatch argJobsBatch, LinkedList<Server> argServers) {
        super(argJobsBatch, argServers);
    }

    /* ================ SETTERS ================ */
    protected void assignArrivals(){
        boolean isAssigned;
        Iterator<Job> jobIterator = arrivedJobs.iterator();

        while(jobIterator.hasNext()){
            Job j = jobIterator.next();
            isAssigned = assignToIdle(j);

            if(isAssigned) jobIterator.remove();
            else{
                Server servP = getPreemptable();
                servP.getAssignedJobs().add(j);
                jobIterator.remove();
            }
        }
    }

    private Server getPreemptable(){
        Server res = null;
        for(Server s : servers)
            if(s != null && (res == null || s.getTotalUW() < res.getTotalUW())) res = s;
        return res;
    }
}
