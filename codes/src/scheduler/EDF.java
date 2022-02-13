package scheduler;

import components.Job;
import components.Server;

import java.util.Comparator;
import java.util.LinkedList;

public class EDF extends Scheduler {
    public EDF(LinkedList<Job> jobs, Server server, int quantum){
        Schedule schedule = getSchedule();
        jobs.sort(Comparator.comparingInt(Job::getDeadline));

        //Schedule init:
        Job firstJob = jobs.removeFirst();
        double start = 0;
        double end = computeEnd(firstJob, start, quantum);
        if(!firstJob.isWorkDone()) jobs.add(firstJob);

        ScheduleEntry firstEntry = new ScheduleEntry(firstJob.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(firstEntry);
        jobs.sort(Comparator.comparingDouble( (Job j) -> j.getDeadline() - schedule.getLastEntry().getEnd() ));

        //We schedule all the rest:
        while(!jobs.isEmpty()){
            Job job = jobs.removeFirst();
            start = schedule.getLastEntry().getEnd();
            end = computeEnd(job, start, quantum);
            if(!job.isWorkDone()) jobs.add(job);

            ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
            schedule.add(newEntry);
            jobs.sort(Comparator.comparingDouble( (Job j) -> j.getDeadline() - schedule.getLastEntry().getEnd() ));
        }
    }
}
