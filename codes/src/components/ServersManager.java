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
    public Server getNextServerToFinish(){
        Server res = null;
        for(Server s : servers){
            if(res == null && !s.isIdle()) res = s;
            else if(!s.isIdle() && s.getDuration() < res.getDuration())
                res = s;
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

    public void decrementAll(double time){
        for(Server s: servers){
            if(s.isIdle()) continue;
            s.getRunningJob().decrement(time * s.getCurrFreq());
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
        for(Server s : servers){
            if(jobIterator.hasNext()){
                s.getAssignedJobs().add(jobIterator.next());
                jobIterator.remove();
            }
            s.setCurrFreq(0);
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
