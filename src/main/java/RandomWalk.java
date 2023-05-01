import java.util.*;
import java.lang.*;
import java.io.*;
public class RandomWalk implements Strategy {

    @Override
    public void searchAlgorithm(graph.Node startNode, graph.Node destNode) {

        //System.out.println("doing random walk..");

        Stack<graph.Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        int destFound = 0;

        stack.push(startNode);

        do {
            graph.Node currentNode = stack.pop();


            if (!visited.contains(currentNode.label)) {
                //System.out.println(currentNode.label);
                graph.printPath(destNode, currentNode);


                visited.add(currentNode.label);
            }
            if (currentNode == destNode) {
                destFound = 1;
            } else {
                //for (graph.Node n : currentNode.children) {}
                int rand;
                graph.Node prevNode = currentNode;
                if (currentNode.children.size() >= 1) {

                    rand = randomNode(currentNode);
                    //System.out.println("child: " + currentNode.children.get(rand).label);

                    //if (!visited.contains(currentNode.children.get(rand).label)) {

                        stack.push(currentNode.children.get(rand));
                    //}


                } else {
                    stack.push(prevNode);

                }

            }


        } while (!stack.isEmpty() && destFound == 0);

        System.out.println("");


    }



    // Returns random node
    static int randomNode(graph.Node src) {
        Random rand = new Random();
        //int rootMax = src.children.size();

        return rand.nextInt(src.children.size());
    }

}
