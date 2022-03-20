package scheduler;

import components.Job;
import components.Server;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Scheduler {
    private Schedule schedule = new Schedule();
    private LinkedList<Job> jobsBatch;
    private LinkedList<Server> servers;

    /* ================ CONSTRUCTORS ================ */
    public Scheduler(LinkedList<Job> argJobsBatch, LinkedList<Server> argServers, int quantum){
        servers = argServers;
        jobsBatch = argJobsBatch;
        jobsBatch.sort(Comparator.comparingInt(Job::getArrivalDate));

        while(!(jobsBatch.isEmpty() && areServersIdle())) {
            if(areServersIdle()) assignNextJob();
            runScheduleStep(quantum);
        }
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getJobsBatch() { return jobsBatch; }
    public Schedule getSchedule() { return schedule; }
    public LinkedList<Server> getServers() { return servers; }

    public int getNextArrivalDate(){
        jobsBatch.sort(Comparator.comparingInt(Job::getArrivalDate));
        return jobsBatch.size() == 0 ? -1 : jobsBatch.getFirst().getArrivalDate();
    }

    /* ================ PREDICATES ================ */
    public boolean areServersIdle(){
        boolean res = true;
        for (Server s: servers) {
            if(!s.getAssignedJobs().isEmpty()) {
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

    /* ================ SETTERS ================ */
    public abstract void assignNextJob();
    public abstract void runScheduleStep(int quantum);
}
