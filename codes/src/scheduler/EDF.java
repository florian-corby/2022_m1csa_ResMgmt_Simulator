package scheduler;

import components.Job;
import components.Server;

import java.util.Comparator;
import java.util.LinkedList;

public class EDF extends Scheduler {
    /* ================ CONSTRUCTORS ================ */
    public EDF(LinkedList<Job> jobs, Server server, int quantum){ super(jobs, server, quantum); }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep(LinkedList<Job> arrivedJobs, Server server, int quantum) {
        Schedule schedule = getSchedule();
        arrivedJobs.sort(Comparator.comparingDouble( (Job j) -> j.getArrivalDate() + j.getDeadline() ));
        //System.out.print(getSchedule().getLastEntry().getEnd() + ": ");
        //System.out.println(arrivedJobs);
        Job job = arrivedJobs.removeFirst();

        double start = ScheduleEntry.computeStart(schedule, job);
        double end = ScheduleEntry.computeEnd(job, start, quantum);
        job.decrementMakespan(quantum);

        ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(newEntry);

        getArrivedJobs();
        if(!job.isWorkDone()) arrivedJobs.add(job);
    }
}
