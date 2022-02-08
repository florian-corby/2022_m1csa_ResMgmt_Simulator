package loaders;

import components.Server;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Servers {
    LinkedList<Server> servers = new LinkedList<>();

    public Servers(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();
            while(sc.hasNext()){
                String currentLine = sc.nextLine();
                currentLine = currentLine.replaceAll("[()]", "");
                String[] tokens = currentLine.split(" ");

                int[] serverFreq = new int[tokens.length-1];
                for (int i = 0; i < tokens.length-1; i++)
                    serverFreq[i] = Integer.parseInt(tokens[i+1]);

                Server server = new Server(Integer.parseInt(tokens[0]), serverFreq);
                servers.add(server);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public Server getServer(int idx){ return servers.get(idx); }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.println(">>> PRINTING INPUT SERVERS");
        for(Server server : servers) server.print();
        System.out.println("<<< END OF INPUT SERVERS PRINTING");
    }
}
