package scheduler;

import components.Job;
import components.Schedule;
import components.ScheduleEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Metrics {
    private int simulationTime;
    private int nbServers;
    private HashMap<Job, Double> tardMap = new HashMap<>();
    private double[] totalConsumption;
    private double[][] serversConsumption;
    private Schedule schedule;

    /* ============== CONSTRUCTORS ============== */
    public Metrics(Schedule argSchedule){
        schedule = argSchedule;
        simulationTime = (int) getTotalMakespan() + 1;
        nbServers = getNbServers();
        totalConsumption = new double[simulationTime];
        serversConsumption = new double[nbServers][simulationTime];
        setTardiness();
        setConsumption();
    }

    /* ============== GETTERS ============== */
    public Set<Job> getLateJobs(){ return tardMap.keySet(); }
    public double getMaxConsumption(){ double res = 0; for(double v : totalConsumption) if(v > res) res = v; return res;}
    public double getMaxTardiness(){
        return tardMap.entrySet().size() == 0 ? 0 : Collections.max(tardMap.entrySet(), HashMap.Entry.comparingByValue()).getValue();
    }
    public int getNbDeadlineMisses(){ return tardMap.size(); }
    public int getNbServers(){
        int res = 0;
        for(ScheduleEntry entry : schedule.getAllEntries())
            if(entry.getServer().getId() > res)
                res = entry.getServer().getId();
        return res+1;
    }
    public double getTotalMakespan(){ return schedule.getLastEntry().getEnd(); }

    /* ============== SETTERS ============== */
    public void setConsumption(){
        for(int i = 0; i < getTotalMakespan() + 1; i++){
            for(ScheduleEntry entry : schedule.getAllEntries())
                if(i >= entry.getStart() && i < entry.getEnd()) {
                    double serverConsumption = 200 * Math.pow(entry.getFreq() / entry.getServer().getMaxFreq(), 2);
                    totalConsumption[i] += serverConsumption;
                    serversConsumption[entry.getServer().getId()][i] = serverConsumption;
                }
        }
    }

    public void setTardiness(){
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
        System.out.println("> Total Makespan: " + getTotalMakespan());
        System.out.println("> Nb Deadline Misses: " + getNbDeadlineMisses());
        System.out.println("> Max Tardiness: " + getMaxTardiness());
        System.out.println("> Late Jobs: ");
        for(Map.Entry<Job, Double> entry : tardMap.entrySet()){ System.out.println("[Job: " + entry.getKey()
                                                                + " | Tardiness: " + entry.getValue() + " ]"); }
        System.out.println("> Max Consumption: " + getMaxConsumption());
        //for(int i = 0; i < consumption.length; i++) System.out.println("[Time: " + i + " | Consumption: " + consumption[i] + " ]");
        System.out.println("\n##################################################\n");
    }

    public void writeConsumption(String fileName){
        new File(fileName).delete();
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, true));
            writer.println(nbServers);
            for(int i = 0; i < simulationTime; i++){
                for(double[] server : serversConsumption) writer.print(server[i] + " ");
                writer.println(totalConsumption[i]);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
