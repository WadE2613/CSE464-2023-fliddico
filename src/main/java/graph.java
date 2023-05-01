import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;
import org.jgrapht.traverse.BreadthFirstIterator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.List;

import static com.mxgraph.analysis.mxTraversal.bfs;
import static org.jgrapht.alg.shortestpath.BFSShortestPath.*;


public class graph {
    enum Algorithm {
        BFS,
        DFS
    }
    static Graph<String, DefaultEdge> graph;


    public static void main(String[] args) throws IOException {
        System.out.println("Enter dot format file name: ");
        Scanner scanner = new Scanner(System.in);
        String filepath = scanner.nextLine();

        graph = parseGraph(filepath);

        // Output the number of nodes
        int numNodes = getNumNodes(graph);
        System.out.println("number of nodes: " + numNodes);

        // output the label of the nodes
        String[] nodeNames = getNodeLabel(graph, numNodes);
        printNodes(nodeNames, numNodes);

        // output the number of edges
        int numEdges = getNumEdges(graph);
        System.out.println("number of edges: " + numEdges);

        // output the nodes and the edge direction of edges
        String nodesString = graphToString(graph, numEdges);
        System.out.println("Nodes with edge direction: " + nodesString);

        int optNum;
        do {
            System.out.println("----------------------------------");
            System.out.println("Type option number: ");
            System.out.println("\t1) Output graph to File\n" +
                    "\t2)Add or remove single node\n" +
                    "\t3)Add or remove list of nodes\n" +
                    "\t4)Add or remove an edge\n" +
                    "\t5)Output graph to DOT file\n" +
                    "\t6)Output graph to to graphics\n" +
                    "\t7)Find path to node");
            optNum = Integer.parseInt(scanner.nextLine());

            switch (optNum) {
                case 1 -> {
                    System.out.println("Enter file name: ");
                    String filename = scanner.nextLine();
                    outputGraph(graph, filename);
                }
                case 2 -> { // Add or remove single node
                    System.out.println("1) Add single node\n" + "2) Remove single node");
                    int addRemNode = Integer.parseInt(scanner.nextLine());
                    if (addRemNode == 1) { // add node
                        System.out.println("Type node label");
                        String nodeLabel = (scanner.nextLine());
                        addNode(graph, nodeLabel);
                        numNodes = getNumNodes(graph);
                        nodeNames = getNodeLabel(graph, numNodes);
                        printNodes(nodeNames, numNodes);
                    } else if (addRemNode == 2) { // remove node
                        System.out.println("Type node label");
                        String nodeLabel = (scanner.nextLine());
                        removeNode(graph, nodeLabel);
                        System.out.println(graph.vertexSet());
                    }
                }
                case 3 -> { // Add or remove list of nodes
                    System.out.println("1) Add list of nodes\n" + "2) Remove list of nodes");
                    int addRemNodes = Integer.parseInt(scanner.nextLine());
                    System.out.println("Type node labels separated by comma");
                    String nodeLabels = (scanner.nextLine());
                    nodeLabels = nodeLabels.replace(" ", "");
                    String[] nodes = nodeLabels.split(",");
                    if (addRemNodes == 1) { // add node
                        addNodes(graph, nodes);
                    } else if (addRemNodes == 2) { // remove node
                        removeNodes(graph, nodes);
                    }
                    System.out.println(graph.vertexSet());
                }
                case 4 -> { // Add or remove an edge
                    System.out.println("1) Add edge\n" + "2) Remove edge");
                    int addRemEdge = Integer.parseInt(scanner.nextLine());
                    System.out.println("Source node: ");
                    String srcNode = (scanner.nextLine());
                    System.out.println("Destination node: ");
                    String destNode = (scanner.nextLine());
                    if (addRemEdge == 1) { // add edge
                        addEdge(graph, srcNode, destNode);
                    } else if (addRemEdge == 2) { // remove edge
                        int prev = graph.edgeSet().size();
                        removeEdge(graph, srcNode, destNode);
                        if (prev == graph.edgeSet().size())
                            System.out.println("Edge does not exist.");
                        else
                            System.out.println("Edge " + srcNode + "," + destNode + " was removed.");
                    }
                    System.out.println(graph.edgeSet());
                }
                case 5 -> { // Output graph to DOT file
                    System.out.println("Enter name of output file");
                    String outFile = scanner.nextLine();
                    outFile = outFile.replace(" ", "");
                    outputDOTGraph(graph, outFile);
                }
                case 6 -> { //Output graph to graphics
                    System.out.println("Enter name of output file:\nUse .png or.jpg");
                    String outGraph = scanner.nextLine();
                    outGraph = outGraph.replace(" ", "");
                    String[] gName = outGraph.split("\\.");
                    outputGraphics(graph, outGraph, gName[1].toUpperCase());
                }
                case 7 -> {
                    System.out.println("Enter source node:");
                    String src = scanner.nextLine();
                    System.out.println("Enter destination node:");
                    String dest = scanner.nextLine();

                    if (!graph.vertexSet().contains(src)) {
                        System.out.println("Source node does not exist!");
                        break;
                    }
                    if (!graph.vertexSet().contains(dest)) {
                        System.out.println("Destination node does not exist!");
                        break;
                    }

                    System.out.println("Choose algorithm: \n" +
                            "\t1) BFS\n" +
                            "\t2) DFS\n");

                    int algor;
                    Node[] targetNode = new Node[2];
                    Algorithm algoBFS = Algorithm.BFS;
                    Algorithm algoDFS = Algorithm.DFS;


                    algor = Integer.parseInt(scanner.nextLine());
                    if (algor == 1) {
                        // use BFS
                        targetNode  =  buildTree(src, dest, numNodes);
                        GraphSearch(targetNode[0], targetNode[1], algoBFS);

                    } else if (algor == 2) {
                        //use DFS
                        targetNode  =  buildTree(src, dest, numNodes);
                        GraphSearch(targetNode[0], targetNode[1], algoDFS);
                    } else
                        System.out.println("Please enter 1 or 2");

                }
                default -> {
                }

            }
        } while (optNum >= 1 && optNum <= 7);

    }

    // --------------------------------------------------------------------------------
    // Feature 1: Parse a DOT graph file to create a graph
    // --------------------------------------------------------------------------------
    // Accept a DOT graph file and create a directed graph object
    public static Graph<String, DefaultEdge> parseGraph(String filepath) throws IOException {
        int i;
        String input = "";
        try {
            FileReader reader = new FileReader(filepath);       // opens file for reading
            // reads entire file contents
            while ((i = reader.read()) != -1) {
                input = input + (char) i;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File read error");
        }
        // Print all the content of a file
        //System.out.print("input: " + input);

        // get graph
        // ** need get type of graph **
        Graph<String, DefaultEdge> result = new SimpleDirectedGraph<>(DefaultEdge.class);
        DOTImporter<String, DefaultEdge> dotImporter = new DOTImporter<>();

        dotImporter.setVertexFactory(label -> label);
        dotImporter.importGraph(result, new StringReader(input));

        return result;
    }

    // ---------------------------------------------------------------------------------
    static String graphToString(Graph<String, DefaultEdge> g, int numEdges) {
        String temp = "";
        String edgeString = "";
        int i = 0;
        for (DefaultEdge e : g.edgeSet()) {
            temp = g.getEdgeSource(e) + " -> " + g.getEdgeTarget(e);
            if (i < numEdges - 1) {
                edgeString = edgeString.concat(temp).concat(", ");
            } else {
                edgeString = edgeString.concat(temp);
            }
            i++;

        }
        return edgeString;
    }

    // --------------------------------------------------------------------------------
    public static void outputGraph(Graph<String, DefaultEdge> graph, String filepath) {
        try {
            File gFile = new File(filepath);
            if (gFile.createNewFile()) {
                System.out.println("File created: " + gFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("File Error.");
        }
        String graphString = graphToString(graph, graph.edgeSet().size());
        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write(graphString);
            writer.close();
            System.out.println(graphString);
        } catch (IOException e) {
            System.out.println("Write error.");
        }
    }

    // --------------------------------------------------------------------------------
    // *** Feature 2: Adding and removing nodes from the imported graph ***
    // --------------------------------------------------------------------------------
    public static void addNode(Graph<String, DefaultEdge> g, String label) {
        if (g.addVertex(label)) {
            g.addVertex(label);
        }
    }

    // --------------------------------------------------------------------------------
    public static void removeNode(Graph<String, DefaultEdge> g, String label) {
        if (g.removeVertex(label)) {
            g.removeVertex(label);
        }
    }

    // --------------------------------------------------------------------------------
    public static void addNodes(Graph<String, DefaultEdge> g, String[] nodesList) {
        int i;
        int lenList = nodesList.length;
        for (i = 0; i < lenList; i++) {
            if (g.addVertex(nodesList[i])) {
                g.addVertex(nodesList[i]);
            }
        }
    }

    // --------------------------------------------------------------------------------
    public static void removeNodes(Graph<String, DefaultEdge> g, String[] remNodes) {
        int i;
        int lenList = remNodes.length;
        for (i = 0; i < lenList; i++) {
            if (g.removeVertex(remNodes[i])) {
                g.removeVertex(remNodes[i]);
            }
        }
    }

    // --------------------------------------------------------------------------------
    //*** Feature 3: Adding and removing edges from the imported graph ***
    // --------------------------------------------------------------------------------
    // Add an edge and check of duplicate edges: addEdge(String srcLabel,String dstLabel)
    public static void addEdge(Graph<String, DefaultEdge> g, String srcLabel, String dstLabel) {
        try {
            g.addEdge(srcLabel, dstLabel);
            //DefaultEdge edgeVar = g.addEdge(srcLabel, dstLabel);
            //if(edgeVar == null) {
            //    System.out.println("Edge already exists.");
            // }
        } catch (Exception e) {
            System.out.println("Add Edge Error: One or both of the given nodes do not exist.");
        }
    }

    // --------------------------------------------------------------------------------
    // Remove an edge: removeEdge(String srcLabel, String dstLabel)
    public static void removeEdge(Graph<String, DefaultEdge> g, String srcLabel, String dstLabel) {
        g.removeEdge(srcLabel, dstLabel);
    }

    // --------------------------------------------------------------------------------
    //*** Feature 4: Output the imported graph into a DOT file or graphics ***
    // --------------------------------------------------------------------------------
    // output the imported graph into a DOT file
    public static void outputDOTGraph(Graph<String, DefaultEdge> g, String filepath) throws IOException {
        DOTExporter<String, DefaultEdge> dotexporter = new DOTExporter<>(v -> v.toString());
        FileWriter writer = new FileWriter(filepath);
        try {
            dotexporter.exportGraph(g, writer);
            System.out.println("DOT file has been created.");
        } catch (Exception e) {
            System.out.println("DOT file unsuccessful");
        }
    }

    // --------------------------------------------------------------------------------
    // output the imported graph into a graphics, at least for jpg and png
    public static void outputGraphics(Graph<String, DefaultEdge> g, String path, String format) throws IOException {

        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File(path);
        try {
            ImageIO.write(image, format, imgFile);
            System.out.println("Graphics file has been created.");
        } catch (Exception e) {
            System.out.println("Graphics file unsuccessful.");
        }

    }

    // --------------------------------------------------------------------------------
    public static void printNodes(String[] nodeNames, int numNodes) {
        System.out.print("labels of nodes: ");
        for (int a = 0; a < numNodes; a++) {
            if (a == (numNodes - 1)) {
                System.out.println(nodeNames[a]);
            } else {
                System.out.print(nodeNames[a] + ", ");
            }
        }
    }

    // --------------------------------------------------------------------------------
    //Output the number of nodes, the label of the nodes, the number of edges, the nodes and the edge direction of edges (e.g., a -> b)
    public static int getNumNodes(Graph<String, DefaultEdge> g) {
        return g.vertexSet().size();
    }

    public static String[] getNodeLabel(Graph<String, DefaultEdge> g, int numNodes) {
        String nodeNames[] = new String[numNodes];

        int k = 0;
        String temp = g.vertexSet().toString();

        temp = temp.replace("[", "");
        temp = temp.replace("]", "");

        while (k < numNodes) {
            nodeNames = temp.split(", ", numNodes);
            k++;
        }
        return nodeNames;
    }

    public static int getNumEdges(Graph<String, DefaultEdge> g) {
        return g.edgeSet().size();
    }

    // --------------------------------------------------------------------------------
    // *** Part 2: API: Path GraphSearch(Node src, Node dst, Algorithm algo)  ***
    // --------------------------------------------------------------------------------

    public static Node[] buildTree(String src, String dest, int numNodes) {
        
        Node[] n = new Node[numNodes];

        //System.out.println("src: " + src + " dest: " + dest);

        // add node
        Node[] targetNode = getNodes(src, dest, n);

        // add edge
        addEdges(n);

        return targetNode;
    }

    // --------------------------------------------------------------------------------
    private static void addEdges(Node[] n) {
        int j = 0;
        int k;

        for (Node N : n) {
            for (DefaultEdge e : graph.edgeSet()) {
                if (graph.getEdgeSource(e).equals(n[j].label)) {
                    //System.out.println("edge: " + e);
                    //System.out.println("n[" + j + "]: " + n[j].label + "  dest: " + graph.getEdgeTarget(e));
                    k = 0;
                    for (Node d : n) {
                        if (n[k].label.equals(graph.getEdgeTarget(e))){
                            //System.out.println("dest node found: n[" + k + "]: " + n[k].label);
                            n[j].addEdge(n[k]);
                        }
                        k++;
                    }
                }
            }
            j++;
        }
    }

    // --------------------------------------------------------------------------------
    private static Node[] getNodes(String src, String dest, Node[] n) {
        int i = 0;
        Node [] targetNode = new Node[2];
        for (String V : graph.vertexSet()) {
            n[i] = new Node(V);
            //System.out.println("node[" + i + "]: " + n[i].label);

            boolean SrcEqualsNode = src.equals(n[i].label);
            if (SrcEqualsNode) {
                targetNode[0] = n[i];
                System.out.println("src node found: " + targetNode[0].label);
            }

            boolean DestEqualsNode = dest.equals(n[i].label);
            if (DestEqualsNode) {
                targetNode[1] = n[i];
                System.out.println("dest node found: " + targetNode[1].label);
            }
            i++;
        }
        return targetNode;
    }

    // --------------------------------------------------------------------------------
    public static void GraphSearch(Node src, Node dest, Algorithm algo) {
        switch(algo) {
            case BFS:
                System.out.println("BFS:");
                BFS(src, dest);
                break;
            case DFS:
                System.out.println("DFS:");
                DFS(src, dest);
                break;
        }

    }
    // --------------------------------------------------------------------------------
    static class Node {
        String label;
        List<Node> children;

        public Node(String label) {
            this.label = label;
            children = new ArrayList<>();
        }
        public void addEdge(Node to) {
            children.add(to);
//            System.out.println("Neighbors: ");
//            for (int i = 0; i < children.size(); i++) {
//                System.out.println(children.get(i).label);
//            }

        }

    }
    // --------------------------------------------------------------------------------
    public static void BFS(Node startNode, Node destNode) {
        //System.out.println("startNode: " + startNode.label);
        //System.out.println("destNode: " + destNode.label);

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode.label);

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();

            printPath(destNode, currentNode);

            for (Node n : currentNode.children) {
                if (!visited.contains(n.label)) {
                    queue.add(n);
                    visited.add(n.label);
                }
            }
        }
        System.out.println("");
    }
    // --------------------------------------------------------------------------------
    private static void printPath(Node destNode, Node currentNode) {
        System.out.print(currentNode.label);
        if (currentNode.label == destNode.label) {
            //System.out.println("match dest");
        }
        else {
            System.out.print("->");
        }
    }


    // --------------------------------------------------------------------------------
    public static void DFS(Node startNode, Node destNode) {
        Stack<Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();

            if (!visited.contains(currentNode.label)) {

                printPath(destNode, currentNode);

                visited.add(currentNode.label);
            }

            for (Node n : currentNode.children) {
                if (!visited.contains(n.label)) {
                    stack.push(n);
                }
            }
        }
        System.out.println("");
    }


}