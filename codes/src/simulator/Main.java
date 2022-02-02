package simulator;

import inputParsers.Jobs;
import inputParsers.Servers;

public class Main {

    public static void main(String[] args) {

        //Testing Jobs files reading:
	    Jobs jobs = new Jobs("../res/inputs/input1_ut3/jobs.txt", 5);
        jobs.print();
        System.out.println();

        //Testing Servers file reading:
        Servers servers = new Servers("../res/inputs/input1_ut3/servers.txt");
        servers.print();
        System.out.println();

        //Testing Dependency file reading:
    }
}
