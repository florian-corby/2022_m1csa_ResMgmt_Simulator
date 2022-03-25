package loaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Test {
    private String fileName;
    private String folderPath;

    private JobsLoader jobsLoader;
    private ServersLoader serversLoader;
    private DepLoader depLoader;
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
            repeat = Integer.parseInt(properties.getProperty("repeat"));
            jobsLoader = new JobsLoader(folderPath + properties.getProperty("job_file").replaceAll("\"", ""), repeat);
            serversLoader = new ServersLoader(folderPath + properties.getProperty("server_file").replaceAll("\"", ""));
            depLoader = new DepLoader(folderPath + properties.getProperty("dependency_file").replaceAll("\"", ""));
            powerCap = Integer.parseInt(properties.getProperty("power_cap"));
            energyCap = Integer.parseInt(properties.getProperty("energy_cap"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public String getFileName() { return fileName.substring(0, fileName.length() - 4); }
    public JobsLoader getJobsLoader() { return jobsLoader; }
    public ServersLoader getServersLoader() { return serversLoader; }
    public DepLoader getDepLoader() { return depLoader; }
    public int getPowerCap() { return powerCap; }
    public int getEnergyCap() { return energyCap; }
    public int getRepeat() { return repeat; }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println("######## TESTS PARAMETERS ########");
        System.out.println("Folder's path: " + folderPath);
        System.out.println("Test's file name: " + fileName + "\n");
        jobsLoader.print();
        System.out.println();
        serversLoader.print();
        System.out.println();
        depLoader.print();
        System.out.println("\nPower Cap: " + powerCap);
        System.out.println("Energy Cap: " + energyCap);
        System.out.println("Nb repeat of periodic tasks: " + repeat);
        System.out.println("##################################\n");
    }
}
