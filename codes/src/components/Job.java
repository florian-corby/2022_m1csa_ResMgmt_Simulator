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

    /* ============== GETTERS ============== */
    public int getId() { return id; }
    public int getArrivalDate() { return arrivalDate; }
    public int getUnitsOfWork() { return unitsOfWork; }
    public int getDeadline() { return deadline; }
    public int getPeriod() { return period; }

    /* ============== SETTERS ============== */
    public void decrementMakespan(int consumedUnitsOfWork){
        unitsOfWork -= consumedUnitsOfWork;
        if(unitsOfWork < 0) unitsOfWork = 0;
    }

    /* ============== PREDICATES ============== */
    public boolean isWorkDone(){ return unitsOfWork == 0; }

    /* ============== PRINTERS ============== */
    public void print(){
        System.out.println("============== JOB #"
                             + id + " ============== ");
        System.out.println("Arrival Date: " + arrivalDate);
        System.out.println("Units of Work: " + unitsOfWork);
        System.out.println("Deadline: " + deadline);
        System.out.println("Period: " + period);
        System.out.println("=====================================");
    }
}
