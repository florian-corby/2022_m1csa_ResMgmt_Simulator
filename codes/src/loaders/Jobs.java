package loaders;

import components.Job;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Jobs {
    LinkedList<Job> jobs = new LinkedList<>();

    public Jobs(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();
            while(sc.hasNextInt())
                jobs.add(new Job(sc.nextInt(), sc.nextInt(), sc.nextInt(),  sc.nextInt(), sc.nextInt()));
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> copyJobs(){
        LinkedList<Job> res = new LinkedList<>();
        for(Job j : jobs) res.add(new Job(j));
        return res;
    }
    public Job copyJob(int idx){ return new Job(jobs.get(idx)); }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println(">>> PRINTING INPUT TASKS");
        for (Job task : jobs) { task.print(); }
        System.out.println("<<< END OF INPUT TASKS PRINTING");
    }
}
