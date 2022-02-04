package simulator;

import loaders.Test;

public class Main {
    public static void main(String[] args) {
        Test test = new Test("../res/inputs/input1_ut3/", "test1.txt");
        test.print();
    }
}
