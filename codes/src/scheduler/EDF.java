//package scheduler;
//
//import components.Job;
//import components.Server;
//import java.util.Comparator;
//import java.util.LinkedList;
//
//public class EDF extends Scheduler {
//    /* ================ CONSTRUCTORS ================ */
//    public EDF(LinkedList<Job> jobs, LinkedList<Server> servers, int quantum){ super(jobs, servers, quantum); }
//
//    /* ================ SETTERS ================ */
//    @Override
//    public void assignNextJob() {
//
//    }
//
//    @Override
//    public void runScheduleStep(int quantum) {
//        LinkedList<Server> servers = getServers();
//        Schedule schedule = getSchedule();
//
//        servers.getFirst().getAssignedJobs().sort(Comparator.comparingDouble(Job::getADeadline));
//        Job job = servers.getFirst().getAssignedJobs().getFirst();
//        double start = ScheduleEntry.computeStart(schedule, job);
//        double end;
//
//        if(getNextArrivalDate() != -1 && (start + job.getUnitsOfWork()) > getNextArrivalDate()){
//            double unitsOfWorksDone = getNextArrivalDate() - start;
//            end = start + unitsOfWorksDone;
//            job.decrementMakespan((int) unitsOfWorksDone);
//        }
//        else{
//            end = start + job.getUnitsOfWork();
//            servers.getFirst().getAssignedJobs().removeFirst();
//        }
//
//        ScheduleEntry newEntry = new ScheduleEntry(job, servers.getFirst(), start, end, servers.getFirst().getFreq(0));
//        schedule.add(newEntry);
//        //getArrivedJobs(servers.getFirst());
//    }
//}
