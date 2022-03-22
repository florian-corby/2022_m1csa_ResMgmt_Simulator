package scheduler;

import components.Job;
import components.JobsBatch;
import components.Schedule;
import components.Server;

import java.io.File;
import java.util.LinkedList;

public abstract class Scheduler {
    protected Schedule schedule = new Schedule();
    protected LinkedList<Server> servers;
    protected JobsBatch jobsBatch;
    protected LinkedList<Job> arrivedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Scheduler(JobsBatch argJobsBatch, LinkedList<Server> argServers, int quantum){
        servers = argServers;
        jobsBatch = argJobsBatch;

        while( !(jobsBatch.isEmpty() && arrivedJobs.isEmpty() && areAllServersIdle()) ){
            if(areAllServersIdle() && arrivedJobs.isEmpty()) arrivedJobs.addAll(jobsBatch.getSoonestJobs());
            runScheduleStep(quantum);
        }
    }

    /* ================ GETTERS ================ */
    public Schedule getSchedule(){ return schedule; }

    /* ================ SETTERS ================ */
    public void decrementAll(double unitsOfWorkDone){
        for(Server s: servers){
            if(s.isIdle()) continue;
            s.getRunningJob().decrement((int) unitsOfWorkDone);
            if(s.getRunningJob().getUnitsOfWork() == 0) s.removeRunningJob();
        }
    }
    protected abstract void runScheduleStep(int quantum);

    /* ================ PREDICATES ================ */
    protected boolean areAllServersIdle(){
        boolean res = true;
        for(Server s : servers){
            if(!s.isIdle()){
                res = false;
                break;
            }
        }
        return res;
    }

    /* ================ PRINTERS ================ */
    public void write(String fileName){
        //Clearing file if it exists:
        new File(fileName).delete();
        schedule.write(fileName);
    }

    public void print(){ schedule.print(); }
}
