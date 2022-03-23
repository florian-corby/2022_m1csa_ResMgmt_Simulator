package simulator;

import loaders.Test;

public class Main {
    public static void main(String[] args) {
        Test test = new Test("../res/inputs/ut3/", "test.txt");
        test.print();
        System.out.println();

        new MonoServer(test);
        System.out.println();
        new MultiServer(test);
        System.out.println();
    }
}
