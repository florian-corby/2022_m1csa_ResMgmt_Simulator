package loaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Test {
    private String fileName;
    private String folderPath;

    private Jobs jobs;
    private Servers servers;
    private Dependencies dependencies;
    private int powerCap = 0;
    private int energyCap = 0;
    private int repeat = 0;

    /* ================ CONSTRUCTORS ================ */
    public Test(String folderPath, String fileName){
        this.fileName = fileName;
        this.folderPath = folderPath;

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(folderPath + fileName));
            jobs = new Jobs(folderPath + properties.getProperty("job_file").replaceAll("\"", ""));
            servers = new Servers(folderPath + properties.getProperty("server_file").replaceAll("\"", ""));
            dependencies = new Dependencies(folderPath + properties.getProperty("dependency_file").replaceAll("\"", ""));
            powerCap = Integer.parseInt(properties.getProperty("power_cap"));
            energyCap = Integer.parseInt(properties.getProperty("energy_cap"));
            repeat = Integer.parseInt(properties.getProperty("repeat"));
        } catch (IOException e) {
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
        System.out.println("\nPower Cap: " + powerCap);
        System.out.println("Energy Cap: " + energyCap);
        System.out.println("Nb repeat of periodic tasks: " + repeat);
        System.out.println("##################################");
    }
}
