package components;

import java.util.ArrayList;
import java.util.LinkedList;

public class DepGraph {
    private ArrayList<LinkedList<Integer>> graph = new ArrayList<>();

    /* =============== GETTERS =============== */
    public LinkedList<Integer> getParents(int vertex){
        LinkedList<Integer> res = new LinkedList<>();
        for(int i = 0; i < graph.size(); i++){
            if(graph.get(i).contains(vertex))
                res.add(i);
        }
        return res;
    }

    public LinkedList<Integer> getSons(int vertex) { return graph.get(vertex); }

    /* =============== PREDICATES =============== */
    public boolean isEdge(int vertex1, int vertex2) {
        boolean found = false;
        for(int i = 0; i < graph.get(vertex1).size(); i++) {
            if(graph.get(vertex1).get(i) == vertex2) {
                found = true;
                break;
            }
        }
        return found;
    }

    /* =============== PRINTERS =============== */
    public void print() {
        System.out.println("========= DEPENDENCY GRAPH =========");
        for (int i = 0; i < graph.size(); i++) {
            if(!graph.get(i).isEmpty()) {
                System.out.print("- Job #" + i + ": ");
                for (int vertex : graph.get(i)) System.out.print(vertex + " ");
                System.out.println();
            }
        }
        System.out.println("====================================");
    }

    /* =============== SETTERS =============== */
    public void add(int idxVertex, int vertexToAdd){
        if(graph.isEmpty() || graph.size() <= idxVertex) {
            for(int i = graph.size(); i <= idxVertex; i++)
                graph.add(new LinkedList<>());
        }
        graph.get(idxVertex).add(vertexToAdd);
    }
}
