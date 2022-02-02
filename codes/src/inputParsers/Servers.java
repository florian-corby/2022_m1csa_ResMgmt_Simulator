package inputParsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Servers {
    LinkedList<int[]> servers = new LinkedList<>();

    public Servers(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));

            //We ignore 1st line of the file (cf. convention):
            sc.nextLine();

            while(sc.hasNext()){
                String currentLine = sc.nextLine();
                currentLine = currentLine.replaceAll("\\(", "");
                currentLine = currentLine.replaceAll("\\)", "");

                String[] items = currentLine.split(" ");

                int[] serverParams = new int[items.length];

                for (int i = 0; i < items.length; i++) {
                    serverParams[i] = Integer.parseInt(items[i]);
                }

                servers.add(serverParams);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ================ GETTERS ================ */
    public int[] getServer(int idx){ return servers.get(idx); }

    /* ================ PRINTERS ================ */
    public void print(){
        Iterator<int[]> it = servers.iterator();

        System.out.println(">>> PRINTING INPUT SERVERS");
        while(it.hasNext()){
            int[] server = it.next();
            System.out.print("- Server #");
            for (int param : server)
                System.out.print(param + " ");
            System.out.println();
        }
        System.out.println("<<< END OF INPUT SERVERS PRINTING");
    }
}
