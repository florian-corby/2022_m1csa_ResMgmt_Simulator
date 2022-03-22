package components;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Schedule {
    private LinkedList<ScheduleEntry> schedule = new LinkedList<>();
    public double currentDate = 0;

    /* ================ GETTERS ================ */
    public LinkedList<ScheduleEntry> getAllEntries(){ return schedule; }
    public LinkedList<ScheduleEntry> getAllEntries(int serverID){
        LinkedList<ScheduleEntry> res = new LinkedList<>();

        for(ScheduleEntry entry : schedule)
            if(entry.getServer().getId() == serverID) res.add(entry);

        return res;
    }
    public ScheduleEntry getLastEntry(){
        if(schedule.isEmpty()) return new ScheduleEntry(null, null, 0, 0, 0);
        else return schedule.getLast();
    }
    public ScheduleEntry getLastEntry(int serverID){
        LinkedList<ScheduleEntry> allServerEntries = getAllEntries(serverID);
        return allServerEntries.size() == 0 ? null : allServerEntries.getLast();
    }

    /* ================ PRINTERS ================ */
    public void write(String fileName){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, true));
            for(ScheduleEntry entry : schedule){
                writer.println(entry.getJob().getId() + " " + entry.getServer().getId() + " " +
                               entry.getStart() + " " + entry.getEnd() + " " + entry.getFreq());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void print(){
        System.out.println("################# OUTPUT SCHEDULE ################\n");
        System.out.println("  jobID | serverID | start | end   | frequency  ");
        System.out.println(" ---------------------------------------------- ");
        for(ScheduleEntry entry : schedule) entry.print();
        System.out.println("\n##################################################\n");
    }

    /* ================ SETTERS ================ */
    public void add(ScheduleEntry entry){ schedule.add(entry); }
}
