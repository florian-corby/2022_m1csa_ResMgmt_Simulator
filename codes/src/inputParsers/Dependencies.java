package inputParsers;

import dataStructures.DepGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dependencies {
    DepGraph dependencies = new DepGraph();

    /* ================ CONSTRUCTORS ================ */
    public Dependencies(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();

            while(sc.hasNext()){
                String currentLine = sc.nextLine();
                currentLine = currentLine.replaceAll("\\W", " ");
                currentLine = currentLine.replaceAll("[ ]+", " ");
                String[] items = currentLine.split(" ");

                dependencies.add(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public DepGraph getDepGraph(){ return dependencies; }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println(">>> PRINTING INPUT DEPENDENCIES");
        dependencies.print();
        System.out.println("<<< END OF INPUT DEPENDENCIES PRINTING");
    }
}
