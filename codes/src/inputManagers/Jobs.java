package inputManagers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Jobs {
    LinkedList<int[]> jobs = new LinkedList<>();

    public Jobs(String fileName, int nbParams){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();

            while(sc.hasNextLine()){
                int[] jobsParams = new int[nbParams];

                for(int i = 0; i < nbParams; i++)
                    jobsParams[i] = sc.nextInt();

                jobs.add(jobsParams);
                sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public int[] getJob(int idx){ return jobs.get(idx); }

    /* ================ PRINTERS ================ */
    public void print(){
        Iterator<int[]> it = jobs.iterator();

        System.out.println("=========== JOBS =========== ");
        while(it.hasNext()){
            int[] job = it.next();
            System.out.print("- Job #");
            for (int param : job)
               System.out.print(param + " ");
            System.out.println();
        }
        System.out.println("============================");
    }
}
