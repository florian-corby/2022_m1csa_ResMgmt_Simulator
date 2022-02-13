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
            else if(!jobsBatch.isEmpty()) getArrivedJobs();
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

    public double computeStart(Schedule schedule, Job job){
        if(schedule.getLastEntry().getEnd() < job.getArrivalDate())
            return job.getArrivalDate();
        else
            return schedule.getLastEntry().getEnd();
    }

    public double computeEnd(Job job, double start, int quantum){
        double end;
        if(job.getUnitsOfWork() <= quantum) {
            end = start + job.getUnitsOfWork();
            job.decrementMakespan(job.getUnitsOfWork());
        }
        else{
            end = start + quantum;
            job.decrementMakespan(quantum);
        }
        return end;
    }

    /* ================ PRINTERS ================ */
    public void write(String fileName){
        //Clearing file if it exists:
        new File(fileName).delete();
        schedule.write(fileName);
    }

    public void print(){ schedule.print(); }
}
