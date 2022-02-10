package scheduler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Schedule {
    private LinkedList<ScheduleEntry> schedule = new LinkedList<>();

    /* ================ GETTERS ================ */
    public ScheduleEntry getLastEntry(){ return schedule.getLast(); }

    /* ================ PRINTERS ================ */
    public void write(String fileName){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, true));
            for(ScheduleEntry entry : schedule){
                writer.println(entry.getJobID() + " " + entry.getServerID() + " " +
                               entry.getStart() + " " + entry.getEnd() + " " + entry.getFreq());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void print(){
        System.out.println("################ OUTPUT SCHEDULE ################\n");
        System.out.println("  jobID | serverID | start | end   | frequency  ");
        System.out.println(" ---------------------------------------------- ");
        for(ScheduleEntry entry : schedule) entry.print();
        System.out.println("\n##################################################");
    }

    /* ================ SETTERS ================ */
    public void add(ScheduleEntry entry){
        schedule.add(entry);
    }
}
