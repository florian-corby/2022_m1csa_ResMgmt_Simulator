package scheduler;

import components.Server;
import components.Job;
import java.util.LinkedList;

public class FIFO extends Scheduler{

    /* ================ CONSTRUCTORS ================ */
    public FIFO(LinkedList<Job> jobs, Server server){
        Schedule schedule = getSchedule();

        //Schedule init:
        Job firstJob = jobs.get(0);
        double start = firstJob.getArrivalDate();
        double end = start + firstJob.getUnitsOfWork();
        ScheduleEntry firstEntry = new ScheduleEntry(firstJob.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(firstEntry);

        //We schedule all the rest:
        for(Job job : jobs.subList(1, jobs.size())){
            start = schedule.getLastEntry().getEnd();
            end = start + job.getUnitsOfWork();
            ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
            schedule.add(newEntry);
        }
    }
}
