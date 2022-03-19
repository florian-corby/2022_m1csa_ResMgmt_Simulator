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
//        System.out.print(getSchedule().getLastEntry().getEnd() + ": ");
//        System.out.println(arrivedJobs);
        Job job = arrivedJobs.getFirst();

        double start = ScheduleEntry.computeStart(schedule, job);
        double end;
        if(getNextArrivalDate() != -1 && (start + job.getUnitsOfWork()) > getNextArrivalDate()){
            double unitsOfWorksDone = getNextArrivalDate() - start;
            end = start + unitsOfWorksDone;
            job.decrementMakespan((int) unitsOfWorksDone);
        }
        else{
            end = start + job.getUnitsOfWork();
            arrivedJobs.removeFirst();
        }

        ScheduleEntry newEntry = new ScheduleEntry(job, server, start, end, server.getFreq(0));
        schedule.add(newEntry);
        getArrivedJobs();
    }
}
