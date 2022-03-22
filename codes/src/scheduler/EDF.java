package scheduler;

import components.Job;
import components.JobsBatch;
import components.ScheduleEntry;
import components.Server;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class EDF extends Scheduler {
    /* ================ CONSTRUCTORS ================ */
    public EDF(JobsBatch jobsBatch, LinkedList<Server> servers, int quantum){ super(jobsBatch, servers, quantum); }

    /* ================ SETTERS ================ */
    private void initServers(){
        Iterator<Job> jobIterator = arrivedJobs.iterator();
        int counter = 0;

        while(jobIterator.hasNext() && counter < servers.size()){
            servers.get(counter).add(jobIterator.next(), Comparator.comparingDouble(Job::getADeadline));
            jobIterator.remove();
            counter++;
        }
    }

    private Server getPreemptable(double deadline){
        Server res = null;
        for(Server s : servers){
            if(s.getRunningJob().getADeadline() > deadline){
                if(res == null || s.getRunningJob().getADeadline() > res.getRunningJob().getADeadline())
                    res = s;
            }
        }
        return res;
    }

    private void assignArrivals(){
        boolean isAssignable = true;
        boolean isAssigned = false;
        Iterator<Job> jobIterator = arrivedJobs.iterator();
        //System.out.println(arrivedJobs);

        while(jobIterator.hasNext()){
            if(!isAssignable) break;

            Job j = jobIterator.next();
            isAssigned = false;

            //Assign arrived job if one server is idle:
            for(Server s : servers){
                if(s.isIdle()){
                    s.add(j, Comparator.comparingDouble(Job::getADeadline));
                    jobIterator.remove();
                    isAssigned = true;
                    break;
                }
            }

            if(!isAssigned){
                Server servP = getPreemptable(j.getADeadline());
                if(servP == null) { isAssignable = false; }
                else{
                    double start = ScheduleEntry.computeStart(schedule, servP, servP.getRunningJob());
                    schedule.add(new ScheduleEntry(servP.getRunningJob(), servP, start, schedule.currentDate, servP.getFreq(0)));
                    servP.add(j, Comparator.comparingDouble(Job::getADeadline));
                    jobIterator.remove();
                }
            }
        }
    }

    @Override
    protected void runScheduleStep(int quantum) {
        arrivedJobs.sort(Comparator.comparingDouble(Job::getADeadline));
        if(areAllServersIdle() && !arrivedJobs.isEmpty()) initServers();

        // =============================================================
        System.out.println("~~~~~~~~~~~~~~~~~~ STEP ~~~~~~~~~~~~~~~~~~");
        System.out.println(">>> Beginning:");
        printServers();
        schedule.print();
        System.out.println("Arrived Jobs: " + arrivedJobs);

        int nextEventDate = getNextEventDate();
        int unitsOfWorkDone = nextEventDate - (int) schedule.currentDate;
        System.out.println("NextEventDate: " + nextEventDate);
        System.out.println("UW Done: " + unitsOfWorkDone);

        decrementAll(unitsOfWorkDone);
        System.out.println("\n>>> After decrement:");
        printServers();
        schedule.print();

        //We deal with new arrivals:
        arrivedJobs.addAll(jobsBatch.getArrivedJobs(nextEventDate));
        arrivedJobs.sort(Comparator.comparingDouble(Job::getADeadline));
        assignArrivals();

        System.out.println("\n>>> After arrivals:");
        printServers();
        schedule.print();
        System.out.println("Arrived Jobs: " + arrivedJobs);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        // =============================================================
    }
}
