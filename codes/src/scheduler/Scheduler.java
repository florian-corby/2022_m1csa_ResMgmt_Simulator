package scheduler;

import components.*;
import loaders.Test;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Scheduler {
    protected Schedule schedule = new Schedule();
    protected LinkedList<Server> servers;
    protected double sysPowMax;
    protected JobsBatch jobsBatch;
    protected LinkedList<Job> arrivedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Scheduler(Test test, int nbServers){
        servers = test.getServersLoader().getServers(0, nbServers);
        sysPowMax = test.getPowerCap();
        jobsBatch = new JobsBatch(test.getJobsLoader().getLoadedJobs());
    }

    /* ================ GETTERS ================ */
    public Schedule getSchedule(){ return schedule; }
    protected double getNextEventDate(){
        double nextArrivalDate = jobsBatch.getNextArrivalDate();
        double nextJobToFinishDate = getNextJobToFinish().getUnitsOfWork() + schedule.currentDate;

        if(nextArrivalDate == -1) return nextJobToFinishDate;
        else return Math.min(nextArrivalDate, nextJobToFinishDate);
    }
    protected Job getNextJobToFinish(){
        Job res = null;
        for(Server s : servers){
            if(res == null && !s.isIdle()) res = s.getRunningJob();
            else if(!s.isIdle() && s.getRunningJob().getUnitsOfWork() < res.getUnitsOfWork())
                res = s.getRunningJob();
        }
        return res;
    }

    /* ================ SETTERS ================ */
    protected boolean assignToIdle(Job j){
        boolean isAssigned = false;
        for(Server s : servers){
            if(s.isIdle()){
                s.getAssignedJobs().add(j);
                isAssigned = true;
                break;
            }
        }
        return isAssigned;
    }
    protected void decrementAll(double unitsOfWorkDone){
        for(Server s: servers){
            if(s.isIdle()) continue;
            s.getRunningJob().decrement((int) unitsOfWorkDone);
            if(s.getRunningJob().getUnitsOfWork() == 0) {
                double start = ScheduleEntry.computeStart(schedule, s, s.getRunningJob());
                double end = schedule.currentDate;
                ScheduleEntry newEntry = new ScheduleEntry(s.getRunningJob(), s, start, end, s.getFreq(0));
                schedule.add(newEntry);
                s.removeRunningJob();
            }
        }
    }
    protected void initServers(){
        Iterator<Job> jobIterator = arrivedJobs.iterator();
        int counter = 0;

        while(jobIterator.hasNext() && counter < servers.size()){
            servers.get(counter).getAssignedJobs().add(jobIterator.next());
            jobIterator.remove();
            counter++;
        }
    }
    protected void run(){
        while( !(jobsBatch.isEmpty() && arrivedJobs.isEmpty() && areAllServersIdle()) ){
            if(areAllServersIdle() && arrivedJobs.isEmpty()) arrivedJobs.addAll(jobsBatch.getSoonestJobs());
            runScheduleStep();
        }
    }
    protected abstract void runScheduleStep();

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
    protected void printServers(){
        System.out.println("Current time is " + schedule.currentDate + " : ");
        for(Server s : servers)
            System.out.println(s.getId() + " : " + s.getAssignedJobs());
        System.out.println();
    }
}
