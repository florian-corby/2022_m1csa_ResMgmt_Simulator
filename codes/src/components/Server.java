package components;

public class Server {
    private int id;
    private int[] frequences;

    /* ================ CONSTRUCTORS ================ */
    public Server(int id, int[] frequences){
        this.id = id;
        this.frequences = new int[frequences.length];
        System.arraycopy(frequences, 0, this.frequences, 0, frequences.length);
    }

    /* ================ GETTERS ================ */
    public int getId() { return id; }
    public int[] getFreq() { return frequences; }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println("============ SERVER #" + this.id + " ============");
        System.out.print("Supported frequencies: ");
        for(int freq : frequences) System.out.print(freq + " ");
        System.out.println("\n===================================");
    }
}
