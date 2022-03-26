package components;

import scheduler.Scheduler;
import java.util.Iterator;
import java.util.LinkedList;

public class ServersManager {
    private Scheduler scheduler;
    private LinkedList<Server> servers;

    /* ================ CONSTRUCTORS ================ */
    public ServersManager(Scheduler argScheduler, LinkedList<Server> argServers){
        scheduler = argScheduler;
        servers = argServers;
    }

    /* ================ GETTERS ================ */
    public double getPow(){ double res = 0; for(Server s : servers) res += s.getCurrPow(); return res; }
    public Job getNextJobToFinish(){
        Job res = null;
        for(Server s : servers){
            if(res == null && !s.isIdle()) res = s.getRunningJob();
            else if(!s.isIdle() && s.getRunningJob().getUnitsOfWork() < res.getUnitsOfWork())
                res = s.getRunningJob();
        }
        return res;
    }
    public LinkedList<Server> getServers() { return servers; }

    /* ================ SETTERS ================ */
    public boolean assignToIdle(Job j){
        boolean isAssigned = false;
        for(Server s : servers){
            if(s.isIdle()){
                s.getAssignedJobs().add(j);
                isAssigned = true;
                break;
            }
        }
        return isAssigned;
    }

    public void decrementAll(double unitsOfWorkDone){
        for(Server s: servers){
            if(s.isIdle()) continue;
            s.getRunningJob().decrement(unitsOfWorkDone);
            if(s.getRunningJob().getUnitsOfWork() == 0) {
                double start = ScheduleEntry.computeStart(scheduler.getSchedule(), s, s.getRunningJob());
                double end = scheduler.getSchedule().currentDate;
                ScheduleEntry newEntry = new ScheduleEntry(s.getRunningJob(), s, start, end, s.getFreq(0));
                scheduler.getSchedule().add(newEntry);
                s.removeRunningJob();
            }
        }
    }

    public void initServers(){
        Iterator<Job> jobIterator = scheduler.getArrivedJ().iterator();
        int counter = 0;

        while(jobIterator.hasNext() && counter < servers.size()){
            servers.get(counter).getAssignedJobs().add(jobIterator.next());
            jobIterator.remove();
            counter++;
        }
    }

    /* ================ PREDICATES ================ */
    public boolean areAllServersIdle(){
        boolean res = true;
        for(Server s : servers){
            if(!s.isIdle()){
                res = false;
                break;
            }
        }
        return res;
    }

    /* ================ PRINTERS ================ */
    public void printServers(){ for(Server s : servers) System.out.println(s.getId() + " : " + s.getAssignedJobs()); }
}
