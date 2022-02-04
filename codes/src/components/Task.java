package components;

public class Task {
    private int id;
    private int arrivalDate;
    private int unitsOfWork;
    private int deadline;
    private int period;

    /* ============== CONSTRUCTORS ============== */
    public Task(int id, int arrivalDate, int unitsOfWork,
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

    /* ============== PRINTERS ============== */
    public void print(){
        System.out.println("============== TASK #"
                             + id + " ============== ");
        System.out.println("Arrival Date: " + arrivalDate);
        System.out.println("Units of Work: " + unitsOfWork);
        System.out.println("Deadline: " + deadline);
        System.out.println("Period: " + period);
        System.out.println("=====================================");
    }
}
