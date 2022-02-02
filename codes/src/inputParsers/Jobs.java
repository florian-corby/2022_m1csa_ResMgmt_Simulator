package inputParsers;

import scheduler.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Jobs {
    LinkedList<Task> tasks = new LinkedList<>();

    public Jobs(String fileName, int nbParams){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();

            while(sc.hasNextInt())
                tasks.add(new Task(sc.nextInt(), sc.nextInt(), sc.nextInt(),  sc.nextInt(), sc.nextInt()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public Task getJob(int idx){ return tasks.get(idx); }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println(">>> PRINTING INPUT TASKS");
        for (Task task : tasks) { task.print(); }
        System.out.println("<<< END OF INPUT TASKS PRINTING");
    }
}
