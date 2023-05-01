import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS implements Strategy {

    @Override
    public void searchAlgorithm(graph.Node startNode, graph.Node destNode) {
        //System.out.println("startNode: " + startNode.label);
        //System.out.println("destNode: " + destNode.label);

        Queue<graph.Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int destFound = 0;

        queue.add(startNode);
        visited.add(startNode.label);

         do {
            graph.Node currentNode = queue.remove();

            graph.printPath(destNode, currentNode);

            if (currentNode == destNode) {
                destFound = 1;
            }
            for (graph.Node n : currentNode.children) {
                if (!visited.contains(n.label)) {
                    queue.add(n);
                    visited.add(n.label);
                }
            }
        }while (!queue.isEmpty() && destFound == 0);

        System.out.println("");

    }
}
