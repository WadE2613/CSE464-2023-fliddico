import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS implements Strategy {

    @Override
    public void searchAlgorithm(graph.Node startNode, graph.Node destNode) {

        Stack<graph.Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        int destFound = 0;

        stack.push(startNode);

        do {
            graph.Node currentNode = stack.pop();

            if (!visited.contains(currentNode.label)) {

                graph.printPath(destNode, currentNode);

                if (currentNode == destNode) {
                    destFound = 1;
                }
                visited.add(currentNode.label);
            }

            for (graph.Node n : currentNode.children) {
                if (!visited.contains(n.label)) {
                    stack.push(n);
                }
            }
        }while (!stack.isEmpty() && destFound == 0);
        System.out.println("");

    }
}

