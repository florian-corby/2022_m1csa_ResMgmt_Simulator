package scheduler;

import components.Job;
import components.Server;
import loaders.Test;

import java.util.Iterator;

public abstract class SchedulerQuantum extends Scheduler {
    /* ================ CONSTRUCTORS ================ */
    public SchedulerQuantum(Test test, int nbServers) {
        super(test, nbServers);
    }

    /* ================ SETTERS ================ */
    protected void assignArrivals(){
        boolean isAssigned;
        Iterator<Job> jobIterator = arrivedJ.iterator();

        while(jobIterator.hasNext()){
            Job j = jobIterator.next();
            isAssigned = serversM.assignToIdle(j);

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
        for(Server s : serversM.getServers())
            if(s != null && (res == null || s.getTotalUW() < res.getTotalUW())) res = s;
        return res;
    }
}
