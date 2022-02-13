package scheduler;

import components.Job;

import java.io.File;

public abstract class Scheduler {
    private Schedule schedule = new Schedule();

    /* ================ GETTERS ================ */
    public Schedule getSchedule() { return schedule; }

    /* ================ SETTERS ================ */
    public double computeStart(Schedule schedule, Job job){
        if(schedule.getLastEntry().getEnd() < job.getArrivalDate()) return job.getArrivalDate();
        else return schedule.getLastEntry().getEnd();
    }

    public double computeEnd(Job job, double start){
        return start + job.getUnitsOfWork();
    }

    public double computeEnd(Job job, double start, int quantum){
        double end;
        if(job.getUnitsOfWork() <= quantum) {
            end = computeEnd(job, start);
            job.decrementMakespan(job.getUnitsOfWork());
        }
        else{
            end = start + quantum;
            job.decrementMakespan(quantum);
        }
        return end;
    }

    /* ================ PRINTERS ================ */
    public void write(String fileName){
        //Clearing file if it exists:
        new File(fileName).delete();
        schedule.write(fileName);
    }
    public void print(){ schedule.print(); }
}
