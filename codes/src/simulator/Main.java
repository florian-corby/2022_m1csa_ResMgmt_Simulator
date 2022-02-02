package simulator;

import inputManagers.Jobs;
import inputManagers.Servers;

public class Main {

    public static void main(String[] args) {

        //Testing Jobs files reading:
	    Jobs jobs = new Jobs("../res/inputs/input1_ut3/jobs.txt", 5);
        jobs.print();

        //Testing Servers file reading:
        Servers servers = new Servers("../res/inputs/input1_ut3/servers.txt", 2);
        //servers.print();

        //Testing Dependency file reading:
    }
}
