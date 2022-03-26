package components;

import java.util.Arrays;
import java.util.LinkedList;

public class Server {
    private int id;
    private final double[] FREQS;
    private double currFreq;
    private double currPow;
    private LinkedList<Job> assignedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Server(int argId, double[] argFreqs){
        id = argId;
        FREQS = new double[argFreqs.length];
        argFreqs = Arrays.stream(argFreqs).sorted().toArray();
        System.arraycopy(argFreqs, 0, FREQS, 0, argFreqs.length);
        setCurrFreq(0);
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getAssignedJobs(){ return assignedJobs; }
    public double getCurrFreq(){ return currFreq; }
    public double getCurrPow(){ return currPow; }
    public double getDuration(){ return isIdle() ? -1 : getRunningJob().getUnitsOfWork() / currFreq; }
    public int getId(){ return id; }
    public double getFreq(int idx){ return FREQS[idx]; }
    public double getMaxFreq(){ return FREQS[FREQS.length-1]; }
    public int getNbFreqs(){ return FREQS.length; }
    public Job getRunningJob(){ return isIdle() ? null : assignedJobs.getFirst(); }
    public double getTotalUW(){
        double res = 0;
        for(Job j : assignedJobs) res += j.getUnitsOfWork();
        return res;
    }

    /* ================ PREDICATES ================ */
    public boolean isIdle(){ return assignedJobs.isEmpty(); }
    public boolean isLate(double date){ return !isIdle() && getRunningJob().getADeadline() <= date + getDuration(); }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println("============ SERVER #" + this.id + " ============");
        System.out.print("Supported frequencies: ");
        for(double freq : FREQS) System.out.print(freq + " ");
        System.out.println("\n===================================");
    }

    /* ================ SETTERS ================ */
    public void removeRunningJob(){ assignedJobs.removeFirst(); }
    public void setCurrFreq(int idFreq){
        currFreq = FREQS[idFreq];
        double currSlowDown = currFreq / getMaxFreq();
        currPow = 200 * Math.pow(currSlowDown, 2);
    }
}
