package components;

import scheduler.Scheduler;

import java.util.Comparator;
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
    public LinkedList<Server> getIdleServers(){
        LinkedList<Server> idleServers = new LinkedList<>();
        for(Server s : servers) if(s.isIdle()) idleServers.add(s);
        idleServers.sort(Comparator.comparingDouble(Server::getMaxFreq).reversed());
        return idleServers;
    }
    public LinkedList<Server> getLateServers(double date){
        LinkedList<Server> res = new LinkedList<>();
        for(Server s : servers) if(s.isLate(date)) res.add(s);
        return res;
    }
    public Server getNextServerToFinish(){
        Server res = null;
        for(Server s : servers){
            if(res == null && !s.isIdle()) res = s;
            else if(!s.isIdle() && s.getDuration() < res.getDuration())
                res = s;
        }
        return res;
    }
    public double getPow(){ double res = 0; for(Server s : servers) res += s.getCurrPow(); return res; }
    public LinkedList<Server> getServers() { return servers; }

    /* ================ SETTERS ================ */
    public boolean assignToIdle(Job j){
        LinkedList<Server> idleServers = getIdleServers();
        if(!idleServers.isEmpty()) idleServers.getFirst().getAssignedJobs().add(j);
        return !idleServers.isEmpty();
    }

    public void decrementAll(double time){
        for(Server s: servers){
            if(s.isIdle()) continue;
            s.getRunningJob().decrement(time * s.getCurrFreq());
            if(s.getRunningJob().getUnitsOfWork() == 0) {
                double start = ScheduleEntry.computeStart(scheduler.getSchedule(), s, s.getRunningJob());
                double end = scheduler.getSchedule().currentDate;
                ScheduleEntry newEntry = new ScheduleEntry(s.getRunningJob(), s, start, end, s.getCurrFreq());
                scheduler.getSchedule().add(newEntry);
                s.removeRunningJob();
            }
        }
    }

    public void initServers(){
        Iterator<Job> jobIterator = scheduler.getArrivedJ().iterator();
        LinkedList<Server> idleServers = getIdleServers();
        for(Server s : idleServers){
            if(jobIterator.hasNext()){
                s.getAssignedJobs().add(jobIterator.next());
                jobIterator.remove();
            }
            s.setCurrFreq(0);
        }
    }

    public void resetFreqs(){ for(Server s : servers) s.setCurrFreq(0); }

    public void setFreqs(double date){
        LinkedList<Server> lateServers = getLateServers(date);
        boolean possibleIncFreq = !isOverMaxPow();

        while(possibleIncFreq && !lateServers.isEmpty()){
            for(Server s : lateServers){
                if(isOverMaxPow()) break;
                int currFreq = 1;

                while(s.isLate(date) && s.getCurrFreq() != s.getMaxFreq() && !isOverMaxPow()) {
                    s.setCurrFreq(currFreq);
                    currFreq++;
                }

                if(isOverMaxPow()){
                    s.setCurrFreq(currFreq-1);
                    possibleIncFreq = false;
                    break;
                }
                else if(s.getCurrFreq() == s.getMaxFreq()) lateServers.remove(s);
            }
        }
    }

    /* ================ PREDICATES ================ */
    public boolean isOverMaxPow(){ return getPow() > scheduler.SYS_POW_MAX; }
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
