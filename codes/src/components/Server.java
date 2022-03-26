package components;

import java.util.LinkedList;

public class Server {
    private int id;
    private double[] frequences;
    private LinkedList<Job> assignedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Server(int argId, double[] argFreqs){
        id = argId;
        frequences = new double[argFreqs.length];
        System.arraycopy(argFreqs, 0, frequences, 0, argFreqs.length);
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getAssignedJobs() { return assignedJobs; }
    public int getId() { return id; }
    public double getFreq(int idx) { return frequences[idx]; }
    public Job getRunningJob(){ return isIdle() ? null : assignedJobs.getFirst(); }
    public double getTotalUW(){
        double res = 0;
        for(Job j : assignedJobs) res += j.getUnitsOfWork();
        return res;
    }

    /* ================ PREDICATES ================ */
    public boolean isIdle(){ return assignedJobs.isEmpty(); }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println("============ SERVER #" + this.id + " ============");
        System.out.print("Supported frequencies: ");
        for(double freq : frequences) System.out.print(freq + " ");
        System.out.println("\n===================================");
    }

    /* ================ SETTERS ================ */
    public void removeRunningJob(){ assignedJobs.removeFirst(); }
}
