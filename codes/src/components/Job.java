package components;

public class Job {
    private int id;
    private int arrivalDate;
    private int unitsOfWork;
    private int deadline;
    private int period;

    /* ============== CONSTRUCTORS ============== */
    public Job(int id, int arrivalDate, int unitsOfWork,
               int deadline, int period){
        this.id = id;
        this.arrivalDate = arrivalDate;
        this.unitsOfWork = unitsOfWork;
        this.deadline = deadline;
        this.period = period;
    }

    public Job(Job job){
        id = job.getId();
        arrivalDate = job.getArrivalDate();
        unitsOfWork = job.getUnitsOfWork();
        deadline = job.getRDeadline();
        period = job.getPeriod();
    }

    /* ============== GETTERS ============== */
    public int getId() { return id; }
    public int getADeadline(){ return arrivalDate + deadline; }
    public int getArrivalDate() { return arrivalDate; }
    public int getUnitsOfWork() { return unitsOfWork; }
    public int getPeriod() { return period; }
    public int getRDeadline() { return deadline; }

    /* ============== SETTERS ============== */
    public void decrement(int consumedUnitsOfWork){
        unitsOfWork -= consumedUnitsOfWork;
        if(unitsOfWork < 0) unitsOfWork = 0;
    }
    public void setAperiodic(){ period = Integer.MAX_VALUE; }

    /* ============== PREDICATES ============== */
    public boolean isWorkDone(){ return unitsOfWork == 0; }

    /* ============== PRINTERS ============== */
    public void print(){
        System.out.println("============== JOB #" + id + " ============== ");
        System.out.println("Arrival Date: " + arrivalDate);
        System.out.println("Units of Work: " + unitsOfWork);
        System.out.println("Deadline: " + deadline);
        System.out.println("Period: " + period);
        System.out.println("=====================================");
    }

    @Override
    public String toString() {
        return "J" + id + "{" + arrivalDate + "a/" + unitsOfWork +
                "u/" + deadline + "rd/"+(arrivalDate+deadline)+"ad/"+ (period == Integer.MAX_VALUE ? 0 : period) + "p}";
    }
}
