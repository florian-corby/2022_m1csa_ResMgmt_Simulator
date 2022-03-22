package components;

public class ScheduleEntry {
    private Job job;
    private Server server;
    private double start;
    private double end;
    private int freq;

    /* ================ CONSTRUCTORS ================ */
    public ScheduleEntry(Job job, Server server, double start, double end, int freq){
        this.job = job;
        this.server = server;
        this.start = start;
        this.end = end;
        this.freq = freq;
    }

    /* ================ GETTERS ================ */
    public Job getJob() { return job; }
    public Server getServer() { return server; }
    public double getStart() { return start; }
    public double getEnd() { return end; }
    public int getFreq() { return freq; }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.printf("  %-5d | %-8d | %-5.1f | %-5.1f | %-9d  \n", job.getId(), server.getId(), start, end, freq);
    }

    /* ================ UTILS ================ */
    public static double computeStart(Schedule schedule, Job job){
        if(schedule.getLastEntry().getEnd() < job.getArrivalDate())
            return job.getArrivalDate();
        else
            return schedule.getLastEntry().getEnd();
    }

    public static double computeEnd(Job job, double start, int quantum){
        return job.getUnitsOfWork() <= quantum ? start + job.getUnitsOfWork() : start + quantum;
    }
}
