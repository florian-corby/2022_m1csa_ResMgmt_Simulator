package scheduler;

import java.io.File;

public abstract class Scheduler {
    private Schedule schedule = new Schedule();

    /* ================ GETTERS ================ */
    public Schedule getSchedule() { return schedule; }

    /* ================ PRINTERS ================ */
    public void out(String fileName){
        //Clearing file if it exists:
        new File(fileName).delete();
        schedule.out(fileName);
    }
    public void print(){ schedule.print(); }
}
