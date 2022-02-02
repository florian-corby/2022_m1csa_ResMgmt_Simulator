package inputParsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestLoader {
    private String fileName;
    private String folderPath;

    private Jobs jobs;
    private Servers servers;
    private Dependencies dependencies;
    private int powerCap = 0;
    private int energyCap = 0;
    private int repeat = 0;

    /* ================ CONSTRUCTORS ================ */
    public TestLoader(String folderPath, String fileName){
        this.fileName = fileName;
        this.folderPath = folderPath;

        try {
            Scanner sc = new Scanner(new File(folderPath + fileName));
            while(sc.hasNext()){
                String currentLine = sc.nextLine();

                if(!currentLine.startsWith("#")) {
                    if (currentLine.startsWith("job_file")) {
                        jobs = new Jobs(folderPath + extractValue(currentLine));
                    } else if (currentLine.startsWith("server_file")) {
                        servers = new Servers(folderPath + extractValue(currentLine));
                    } else if (currentLine.startsWith("dependency_file")) {
                        dependencies = new Dependencies(folderPath + extractValue(currentLine));
                    } else if (currentLine.startsWith("power_cap")) {
                        powerCap = Integer.parseInt(extractValue(currentLine));
                    } else if (currentLine.startsWith("energy_cap")) {
                        energyCap = Integer.parseInt(extractValue(currentLine));
                    } else if (currentLine.startsWith("repeat")) {
                        repeat = Integer.parseInt(extractValue(currentLine));
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public Jobs getJobs() { return jobs; }
    public Servers getServers() { return servers; }
    public Dependencies getDependencies() { return dependencies; }
    public int getPowerCap() { return powerCap; }
    public int getEnergyCap() { return energyCap; }
    public int getRepeat() { return repeat; }

    /* =============== PREDICATES =============== */
    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println("######## TESTS PARAMETERS ########");
        System.out.println("Folder's path: " + folderPath);
        System.out.println("Test's file name: " + fileName + "\n");
        jobs.print();
        System.out.println();
        servers.print();
        System.out.println();
        dependencies.print();
        System.out.println();
        System.out.println("Power Cap: " + powerCap);
        System.out.println("Energy Cap: " + energyCap);
        System.out.println("Nb repeat of periodic tasks: " + repeat);
        System.out.println("##################################");
    }
    /* =============== SETTERS =============== */
    /* =============== TOOLS =============== */
    public String extractValue(String s){
        s = s.replaceAll("\"", "");
        String[] tokens = s.split(" ");
        return tokens[tokens.length - 1];
    }
}
