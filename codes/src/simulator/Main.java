package simulator;

import inputParsers.Dependencies;
import inputParsers.Jobs;
import inputParsers.Servers;
import inputParsers.TestLoader;

public class Main {

    public static void main(String[] args) {
        TestLoader test = new TestLoader("../res/inputs/input1_ut3/", "test1.txt");
        test.print();
    }
}
