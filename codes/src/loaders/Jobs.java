package loaders;

import components.Job;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Jobs {
    LinkedList<Job> jobs = new LinkedList<>();

    public Jobs(String fileName, int nbRepeat){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();
            while(sc.hasNextInt()) {
                Job readJob = new Job(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                jobs.add(readJob);

                if(readJob.getPeriod() > 0) {
                    for(int i = 1; i < nbRepeat; i++) {
                        Job prevPeriodicJob = jobs.getLast();
                        int periodicArrivalDate = prevPeriodicJob.getArrivalDate() + prevPeriodicJob.getPeriod();
                        Job periodicJob = new Job(prevPeriodicJob.getId(), periodicArrivalDate, prevPeriodicJob.getUnitsOfWork(),
                                                  prevPeriodicJob.getDeadline(), prevPeriodicJob.getPeriod());
                        jobs.add(periodicJob);
                    }
                }
            }
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
