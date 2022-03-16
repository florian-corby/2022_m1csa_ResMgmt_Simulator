package scheduler;

import components.Job;
import components.Server;

import java.util.Comparator;
import java.util.LinkedList;

public class RMS extends Scheduler {

    /* ================ CONSTRUCTORS ================ */
    public RMS(LinkedList<Job> argJobsBatch, Server server) { super(argJobsBatch, server, 1); }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(LinkedList<Job> arrivedJobs, Server server, int quantum) {
        Schedule schedule = getSchedule();
        arrivedJobs.sort(Comparator.comparingDouble(Job::getPeriod));
        //System.out.print(getSchedule().getLastEntry().getEnd() + ": ");
        //System.out.println(arrivedJobs);
        Job job = arrivedJobs.removeFirst();

        double start = ScheduleEntry.computeStart(schedule, job);
        double end = ScheduleEntry.computeEnd(job, start, 1);
        job.decrementMakespan(1);

        ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(newEntry);

        getArrivedJobs();
        if(!job.isWorkDone()) arrivedJobs.add(job);
    }
}
