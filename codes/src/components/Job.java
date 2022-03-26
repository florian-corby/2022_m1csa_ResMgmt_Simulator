package components;

public class Job {
    private int id;
    private double arrivalDate;
    private double unitsOfWork;
    private double deadline;
    private double period;

    /* ============== CONSTRUCTORS ============== */
    public Job(int id, double arrivalDate, double unitsOfWork,
               double deadline, double period){
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
    public double getADeadline(){ return arrivalDate + deadline; }
    public double getArrivalDate() { return arrivalDate; }
    public double getPeriod() { return period; }
    public double getRDeadline() { return deadline; }
    public double getUnitsOfWork() { return unitsOfWork; }

    /* ============== SETTERS ============== */
    public void decrement(double consumedUnitsOfWork){
        unitsOfWork -= consumedUnitsOfWork;
        if(unitsOfWork < 0) unitsOfWork = 0;
    }
    public void setAperiodic(){ period = Double.MAX_VALUE; }

    /* ============== PREDICATES ============== */
    public boolean isWorkDone(){ return unitsOfWork == 0; }

    /* ============== PRINTERS ============== */
    public void print(){
        System.out.println("============== JOB #" + id + " ============== ");
        System.out.println("Arrival Date: " + arrivalDate);
        System.out.println("Units of Work: " + unitsOfWork);
        System.out.println("Deadline: " + deadline);
        System.out.println("Period: " + (period == Integer.MAX_VALUE ? 0 : period));
        System.out.println("=====================================");
    }

    @Override
    public String toString() {
        return "J" + id + "{" + arrivalDate + "a/" + unitsOfWork +
                "u/" + deadline + "rd/"+(arrivalDate+deadline)+"ad/"+ (period == Double.MAX_VALUE ? 0 : period) + "p}";
    }
}
