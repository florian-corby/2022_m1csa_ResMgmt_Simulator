package scheduler;

import components.Job;
import loaders.Test;
import java.util.Comparator;
import java.util.function.BiPredicate;

public class RMS extends SchedulerPriority {
    private static final Comparator<Job> JOBS_COMPARISON_KEY = (j1, j2) -> {
        if (j1.getPeriod() == j2.getPeriod())
            return Double.compare(j1.getADeadline(), j2.getADeadline());
        else
            return Double.compare(j1.getPeriod(), j2.getPeriod());
    };
    private static final BiPredicate<Job, Job> JOBS_COMPARISON_PREDICATE =  (j1, j2) -> {
            if (j1.getPeriod() == j2.getPeriod())
                return j1.getADeadline() <= j2.getADeadline();
            else
                return j1.getPeriod() <= j2.getPeriod();};

    /* ================ CONSTRUCTORS ================ */
    public RMS(Test test, int nbServers){
        super(test, nbServers);
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    protected void runScheduleStep() {
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("~~~~~~~~~~~~~~~~ STEP ~~~~~~~~~~~~~~~~");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("Current date: " + schedule.currentDate + "\n");
//
//        System.out.println(">>> Beginning of step: ");
//        System.out.println("> Jobs Batch: " + jobsB.getJobsList());
//        System.out.println("> Arrived Jobs: " + arrivedJ);
//        System.out.println("> Servers: ");
//        serversM.printServers();
//        System.out.println("> Schedule: ");
//        System.out.println("  jobID | serverID | start | end   | frequency  ");
//        System.out.println(" ---------------------------------------------- ");
//        for(ScheduleEntry entry : schedule.getAllEntries()) entry.print();

        arrivedJ.sort(JOBS_COMPARISON_KEY);
        if(serversM.areAllServersIdle() && !arrivedJ.isEmpty()){
            schedule.currentDate = arrivedJ.getFirst().getArrivalDate();
            serversM.initServers();
        }

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double unitsOfWorkDone = nextEventDate - schedule.currentDate;
        schedule.currentDate += unitsOfWorkDone;

//        System.out.println("\n>>> Next event is in: " + unitsOfWorkDone);

        //We decrement and deal with finished jobs:
        serversM.decrementAll(unitsOfWorkDone);

//        System.out.println("\n>>> After decrement: ");
//        System.out.println("> Servers: ");
//        serversM.printServers();

        //We deal with new arrivals:
        arrivedJ.addAll(jobsB.getArrivedJobs(nextEventDate));
        arrivedJ.sort(JOBS_COMPARISON_KEY);
        assignArrivals(JOBS_COMPARISON_KEY, JOBS_COMPARISON_PREDICATE);

//        System.out.println("\n>>> End of Step: ");
//        System.out.println("> Jobs Batch: " + jobsB.getJobsList());
//        System.out.println("> Arrived Jobs: " + arrivedJ);
//        System.out.println("> Servers: ");
//        serversM.printServers();
//        System.out.println("> Schedule: ");
//        System.out.println("  jobID | serverID | start | end   | frequency  ");
//        System.out.println(" ---------------------------------------------- ");
//        for(ScheduleEntry entry : schedule.getAllEntries()) entry.print();
//
//        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}
