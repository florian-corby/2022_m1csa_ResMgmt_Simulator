package components;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class JobsBatch {
    LinkedList<Job> jobs = new LinkedList<>();

    /* ================ CONSTRUCTORS ================ */
    public JobsBatch(LinkedList<Job> argJobs){
        jobs = argJobs;
        sort();
    }

    public JobsBatch(JobsBatch batchToCopy){
        for(Job j : batchToCopy.getJobsList())
            jobs.add(new Job(j));
        sort();
    }

    /* ================ GETTERS ================ */
    public LinkedList<Job> getArrivedJobs(double date){
        LinkedList<Job> res = new LinkedList<>();

        Iterator<Job> jobIterator = jobs.iterator();
        while(jobIterator.hasNext()){
            Job nextJob = jobIterator.next();
            if(nextJob.getArrivalDate() <= date){
                res.add(nextJob);
                jobIterator.remove();
            }
            else break;
        }

        return res;
    }

    public LinkedList<Job> getJobsList(){ return jobs; }

    public double getNextArrivalDate(){
        return jobs.size() == 0 ? -1 : jobs.getFirst().getArrivalDate();
    }

    public LinkedList<Job> getSoonestJobs(){
        LinkedList<Job> res = new LinkedList<>();
        Job soonestJob = jobs.removeFirst();
        res.add(soonestJob);

        Iterator<Job> jobIterator = jobs.iterator();
        while(jobIterator.hasNext()){
            Job nextJob = jobIterator.next();
            if(nextJob.getArrivalDate() == soonestJob.getArrivalDate()){
                res.add(nextJob);
                jobIterator.remove();
            }
            else break;
        }

        return res;
    }

    /* ================ PREDICATES ================ */
    public boolean isEmpty(){ return jobs.isEmpty(); }

    /* ================ TOOLS ================ */
    public void sort(){ jobs.sort(Comparator.comparingDouble(Job::getArrivalDate)); }
}
