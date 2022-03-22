package scheduler;

import components.*;

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

    public int getNextEventDate(){
        int nextArrivalDate = jobsBatch.getNextArrivalDate();
        int nextJobToFinishDate = (int) (getNextJobToFinish().getUnitsOfWork() + schedule.currentDate);

        if(nextArrivalDate == -1) return nextJobToFinishDate;
        else return Math.min(nextArrivalDate, nextJobToFinishDate);
    }

    public Job getNextJobToFinish(){
        Job res = null;

        for(Server s : servers){
            if(res == null && !s.isIdle()) res = s.getRunningJob();
            else if(!s.isIdle() && s.getRunningJob().getUnitsOfWork() < res.getUnitsOfWork())
                res = s.getRunningJob();
        }

        return res;
    }

    /* ================ SETTERS ================ */
    public boolean assignToIdle(Job j){
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

    public void decrementAll(double unitsOfWorkDone){
        schedule.currentDate += unitsOfWorkDone;

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

    public void printServers(){
        System.out.println("Current time is " + schedule.currentDate + " : ");
        for(Server s : servers)
            System.out.println(s.getId() + " : " + s.getAssignedJobs());
        System.out.println();
    }
}
