package loaders;

import components.Job;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class JobsLoader {
    LinkedList<Job> loadedJobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public JobsLoader(String fileName, int nbRepeat){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();
            while(sc.hasNextInt()) {
                Job readJob = new Job(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                loadedJobs.add(readJob);

                if(readJob.getPeriod() > 0) {
                    for(int i = 1; i < nbRepeat; i++) {
                        Job prevPeriodicJob = loadedJobs.getLast();
                        double periodicArrivalDate = prevPeriodicJob.getArrivalDate() + prevPeriodicJob.getPeriod();
                        Job periodicJob = new Job(prevPeriodicJob.getId(), periodicArrivalDate, prevPeriodicJob.getUnitsOfWork(),
                                                  prevPeriodicJob.getRDeadline(), prevPeriodicJob.getPeriod());
                        loadedJobs.add(periodicJob);
                    }
                }
                else
                    readJob.setAperiodic();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getLoadedJobs(){ return loadedJobs; }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println(">>> PRINTING INPUT TASKS");
        for (Job task : loadedJobs) { task.print(); }
        System.out.println("<<< END OF INPUT TASKS PRINTING");
    }
}
