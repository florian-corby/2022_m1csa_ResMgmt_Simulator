package simulator;

import inputParsers.Dependencies;
import inputParsers.Jobs;
import inputParsers.Servers;

public class Main {

    public static void main(String[] args) {
        //Testing Jobs files reading:
	    Jobs jobs = new Jobs("../res/inputs/input1_ut3/jobs.txt");
        jobs.print();
        System.out.println();

        //Testing Servers file reading:
        Servers servers = new Servers("../res/inputs/input1_ut3/servers.txt");
        servers.print();
        System.out.println();

        //Testing Dependency file reading:
        Dependencies dependencies = new Dependencies("../res/inputs/input1_ut3/dependencies.txt");
        dependencies.print();
        System.out.println();
    }
}
