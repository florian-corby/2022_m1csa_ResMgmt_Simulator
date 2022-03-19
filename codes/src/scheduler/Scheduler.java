package scheduler;

import components.Job;
import components.Server;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Scheduler {
    private Schedule schedule = new Schedule();
    private LinkedList<Job> jobsBatch;
    private LinkedList<Job> arrivedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Scheduler(LinkedList<Job> argJobsBatch, Server server, int quantum){
        jobsBatch = argJobsBatch;
        jobsBatch.sort(Comparator.comparingInt(Job::getArrivalDate));

        while(!jobsBatch.isEmpty() || !arrivedJobs.isEmpty()){
            if(arrivedJobs.isEmpty()) getSoonestJobs();
            runScheduleStep(arrivedJobs, server, quantum);
        }
    }

    /* ================ GETTERS ================ */
    public Schedule getSchedule() { return schedule; }

    public void getArrivedJobs(){
        Iterator<Job> jobIterator = jobsBatch.iterator();
        while(jobIterator.hasNext()){
            Job nextJob = jobIterator.next();
            if(nextJob.getArrivalDate() <= schedule.getLastEntry().getEnd()){
                arrivedJobs.add(nextJob);
                jobIterator.remove();
            }
            else break;
        }
    }

    public int getNextArrivalDate(){
        jobsBatch.sort(Comparator.comparingInt(Job::getArrivalDate));
        return jobsBatch.size() == 0 ? -1 : jobsBatch.getFirst().getArrivalDate();
    }

    public void getSoonestJobs(){
        Job soonestJob = jobsBatch.removeFirst();
        arrivedJobs.add(soonestJob);

        Iterator<Job> jobIterator = jobsBatch.iterator();
        while(jobIterator.hasNext()){
            Job nextJob = jobIterator.next();
            if(nextJob.getArrivalDate() == soonestJob.getArrivalDate()){
                arrivedJobs.add(nextJob);
                jobIterator.remove();
            }
            else break;
        }
    }

    /* ================ SETTERS ================ */
    public abstract void runScheduleStep(LinkedList<Job> arrivedJobs, Server server, int quantum);


    /* ================ PRINTERS ================ */
    public void write(String fileName){
        //Clearing file if it exists:
        new File(fileName).delete();
        schedule.write(fileName);
    }

    public void print(){ schedule.print(); }
}
