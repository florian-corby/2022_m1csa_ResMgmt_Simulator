package scheduler;

import components.Job;
import components.Schedule;
import components.ScheduleEntry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Metrics {
    private HashMap<Job, Double> tardMap = new HashMap<>();
    private Schedule schedule;

    /* ============== CONSTRUCTORS ============== */
    public Metrics(Schedule schedule){ setMetrics(schedule); }

    /* ============== GETTERS ============== */
    public Set<Job> getLateJobs(){ return tardMap.keySet(); }
    public double getMaxTardiness(){ return Collections.max(tardMap.entrySet(), HashMap.Entry.comparingByValue()).getValue(); }
    public int getNbDeadlineMisses(){ return tardMap.size(); }
    public double getTotalMakespan(){ return schedule.getLastEntry().getEnd(); }

    /* ============== SETTERS ============== */
    public void setMetrics(Schedule schedule){
        this.schedule = schedule;

        for (ScheduleEntry entry: schedule.getAllEntries()) {
            if(entry.getJob().getADeadline() < entry.getEnd()) {
                double tardiness = entry.getEnd() - entry.getJob().getADeadline();
                tardMap.put(entry.getJob(), tardiness);
            }
        }
    }

    /* ============== PRINTERS ============== */
    public void print(){
        System.out.println("################ SCHEDULE METRICS ################\n");
        System.out.println("Nb Deadline Misses: " + getNbDeadlineMisses());
        System.out.print("Late Jobs: ");
        for(Job j : getLateJobs()){ System.out.print(j.getId() + " ");}
        System.out.println("\nMax Tardiness: " + getMaxTardiness());
        System.out.println("Total Makespan: " + getTotalMakespan());
        System.out.println("\n##################################################\n");
    }
}
