package scheduler;

import components.Job;
import components.Server;

import java.util.Iterator;
import java.util.LinkedList;

public class RR extends Scheduler {

    /* ================ CONSTRUCTORS ================ */
    public RR(LinkedList<Job> jobs, LinkedList<Server> servers, int quantum){ super(jobs, servers, quantum); }

    /* ================ GETTERS ================ */
    private void getArrivedJobs(Server server){
        Iterator<Job> jobIterator = getJobsBatch().iterator();
        while(jobIterator.hasNext()){
            Job nextJob = jobIterator.next();
            if(nextJob.getArrivalDate() <= getSchedule().getLastEntry().getEnd()){
                server.getAssignedJobs().add(nextJob);
                jobIterator.remove();
            }
            else break;
        }
    }

    private void getSoonestJobs(Server server){
        if(getJobsBatch().isEmpty()) return;
        Job soonestJob = getJobsBatch().removeFirst();
        server.getAssignedJobs().add(soonestJob);

        Iterator<Job> jobIterator = getJobsBatch().iterator();
        while(jobIterator.hasNext()){
            Job nextJob = jobIterator.next();
            if(nextJob.getArrivalDate() == soonestJob.getArrivalDate()){
                server.getAssignedJobs().add(nextJob);
                jobIterator.remove();
            }
            else break;
        }
    }

    /* ================ SETTERS ================ */
    @Override
    public void assignNextJob() {
        if(getServers().getFirst().isIdle())
            getSoonestJobs(getServers().getFirst());
        else
            getArrivedJobs(getServers().getFirst());
    }

    @Override
    public void runScheduleStep(int quantum) {
        LinkedList<Server> servers = getServers();
        Schedule schedule = getSchedule();

        Job job = servers.getFirst().getAssignedJobs().getFirst();
        double start = ScheduleEntry.computeStart(schedule, job);
        double end = ScheduleEntry.computeEnd(job, start, quantum);
        job.decrementMakespan(quantum);

        ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
        schedule.add(newEntry);

        assignNextJob();
        getServers().getFirst().getAssignedJobs().removeFirst();
        if(!job.isWorkDone()) servers.getFirst().getAssignedJobs().add(job);
    }
}
