package scheduler;

import components.Server;
import components.Job;

import java.util.Comparator;
import java.util.LinkedList;

public class FIFO extends Scheduler{

    /* ================ CONSTRUCTORS ================ */
    public FIFO(LinkedList<Job> jobs, Server server){
        Schedule schedule = getSchedule();
        jobs.sort(Comparator.comparingInt(Job::getArrivalDate));

        //Schedule init:
        Job firstJob = jobs.get(0);
        double start = firstJob.getArrivalDate();
        double end = computeEnd(firstJob, start);
        ScheduleEntry firstEntry = new ScheduleEntry(firstJob.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(firstEntry);

        //We schedule all the rest:
        for(Job job : jobs.subList(1, jobs.size())){
            start = computeStart(schedule, job);
            end = computeEnd(job, start);
            ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
            schedule.add(newEntry);
        }
    }
}
