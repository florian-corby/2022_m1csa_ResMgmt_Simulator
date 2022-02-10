package scheduler;

import components.Job;
import components.Server;

import java.util.Comparator;
import java.util.LinkedList;

public class RR extends Scheduler {

    /* ================ CONSTRUCTORS ================ */
    public RR(LinkedList<Job> jobs, Server server, int quantum){
        Schedule schedule = getSchedule();
        jobs.sort(Comparator.comparingInt(Job::getArrivalDate));

        //Schedule init:
        Job firstJob = jobs.removeFirst();
        double start = firstJob.getArrivalDate();
        double end;
        if(firstJob.getUnitsOfWork() <= quantum)
            end = start + firstJob.getUnitsOfWork();
        else{
            end = start + quantum;
            firstJob.decrementMakespan(quantum);
            jobs.add(firstJob);
        }
        ScheduleEntry firstEntry = new ScheduleEntry(firstJob.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(firstEntry);

        //We schedule all the rest:
        while(!jobs.isEmpty()){
            Job job = jobs.removeFirst();
            start = schedule.getLastEntry().getEnd();
            if(job.getUnitsOfWork() <= quantum)
                end = start + job.getUnitsOfWork();
            else{
                end = start + quantum;
                job.decrementMakespan(quantum);
                jobs.add(job);
            }
            ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
            schedule.add(newEntry);
        }
    }
}
