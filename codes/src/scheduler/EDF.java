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
        arrivedJobs.sort(Comparator.comparingDouble( (Job j) -> j.getDeadline() - schedule.getLastEntry().getEnd() ));

        Job job = arrivedJobs.removeFirst();
        double start = schedule.getLastEntry().getEnd();
        double end = computeEnd(job, start, quantum);
        if(!job.isWorkDone()) arrivedJobs.add(job);

        ScheduleEntry newEntry = new ScheduleEntry(job.getId(), server.getId(), start, end, server.getFreq(0));
        schedule.add(newEntry);
    }
}
