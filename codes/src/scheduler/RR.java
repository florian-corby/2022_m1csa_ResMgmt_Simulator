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
        double end = computeEnd(firstJob, start, quantum);
        if(!firstJob.isWorkDone()) jobs.add(firstJob);
        ScheduleEntry firstEntry = new ScheduleEntry(firstJob.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(firstEntry);

        //We schedule all the rest:
        while(!jobs.isEmpty()){
            Job job = jobs.removeFirst();
            start = computeStart(schedule, job);
            end = computeEnd(job, start, quantum);
            if(!job.isWorkDone()) jobs.add(job);
            ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
            schedule.add(newEntry);
        }
    }
}
