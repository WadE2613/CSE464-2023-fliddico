import junit.framework.Assert;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GraphTest {

    String filepath = "shells.gv";
    Graph<String, DefaultEdge> graphTest = graph.parseGraph(filepath); // returns graph

    public GraphTest() throws IOException {
    }


    // -------------------------------------------------------------------
    @Test
    public void testNumNodes() throws IOException {

        int numNodes = graph.getNumNodes(graphTest);

        Assert.assertEquals("NumNodes test failed", 29, numNodes);
    }

    @Test
    public void testRemoveNode() throws IOException {

        graph.removeNode(graphTest, "rc");
        int numNodes = graph.getNumNodes(graphTest);

        Assert.assertEquals("RemoveNode test failed", 28, numNodes);
    }

    @Test
    public void testAddNode() throws IOException {

        graph.addNode(graphTest, "xx");
        int numNodes = graph.getNumNodes(graphTest);

        Assert.assertEquals("RemoveNode test failed", 30, numNodes);
    }

    @Test
    public void testNumEdges() throws IOException {

        int numEdges = graph.getNumEdges(graphTest);

        Assert.assertEquals("NumEdges test failed", 38, numEdges);
    }
    @Test
    public void testRemoveEdge() throws IOException {

        graph.removeEdge(graphTest, "v9sh", "rc");
        int numEdges = graph.getNumEdges(graphTest);

        Assert.assertEquals("NumEdges test failed", 37, numEdges);
    }


//    @Test
//    public void givenAGraph_whenTraversingBreadthFirst_thenExpectedResult() {
//        GraphSearch g = graph.createGraph();
//        assertEquals("[Bob, Alice, Rob, Mark, Maria]",
//                GraphSearch.breadthFirstTraversal(g, "Bob").toString());
//    }




}

