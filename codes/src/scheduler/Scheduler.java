package scheduler;

import components.*;
import loaders.Test;

import java.util.LinkedList;

public abstract class Scheduler {
    protected Schedule schedule = new Schedule();
    protected ServersManager serversM;
    public final double SYS_POW_MAX;
    protected JobsBatch jobsB;
    protected LinkedList<Job> arrivedJ = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Scheduler(Test test, int nbServers){
        serversM = new ServersManager(this, test.getServersLoader().getServers(0, nbServers));
        SYS_POW_MAX = test.getPowerCap();
        jobsB = new JobsBatch(test.getJobsLoader().getLoadedJobs());
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getArrivedJ() { return arrivedJ; }
    protected double getNextEventDate(){
        double nextArrivalDate = jobsB.getNextArrivalDate();
        double nextJobToFinishDate = schedule.currentDate + serversM.getNextServerToFinish().getDuration();

        if(nextArrivalDate == -1) return nextJobToFinishDate;
        else return Math.min(nextArrivalDate, nextJobToFinishDate);
    }
    public Schedule getSchedule(){ return schedule; }

    /* ================ SETTERS ================ */
    protected void run(){
        while( !(jobsB.isEmpty() && arrivedJ.isEmpty() && serversM.areAllServersIdle()) ){
            if(serversM.areAllServersIdle() && arrivedJ.isEmpty()) arrivedJ.addAll(jobsB.getSoonestJobs());
            runScheduleStep();
        }
    }
    protected abstract void runScheduleStep();
}
