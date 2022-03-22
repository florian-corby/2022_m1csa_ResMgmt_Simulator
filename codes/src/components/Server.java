package components;

import java.util.Comparator;
import java.util.LinkedList;

public class Server {
    private int id;
    private int[] frequences;
    private LinkedList<Job> assignedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public Server(int id, int[] frequences){
        this.id = id;
        this.frequences = new int[frequences.length];
        System.arraycopy(frequences, 0, this.frequences, 0, frequences.length);
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getAssignedJobs() { return assignedJobs; }
    public int getId() { return id; }
    public int getFreq(int idx) { return frequences[idx]; }
    public Job getRunningJob(){ return isIdle() ? null : assignedJobs.getFirst(); }

    /* ================ PREDICATES ================ */
    public boolean isIdle(){ return assignedJobs.isEmpty(); }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println("============ SERVER #" + this.id + " ============");
        System.out.print("Supported frequencies: ");
        for(int freq : frequences) System.out.print(freq + " ");
        System.out.println("\n===================================");
    }

    /* ================ SETTERS ================ */
    public void add(Job j, Comparator<Job> c){
        assignedJobs.add(j);
        assignedJobs.sort(c);
    }
    public void removeRunningJob(){ assignedJobs.removeFirst(); }
}
