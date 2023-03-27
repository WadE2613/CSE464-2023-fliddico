import junit.framework.Assert;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

import java.io.IOException;

public class GraphTest {

    String filepath = "test.gv";
    Graph<String, DefaultEdge> graphTest = graph.parseGraph(filepath); // returns graph

    public GraphTest() throws IOException {
    }


    // -------------------------------------------------------------------
    @Test
    public void testNumNodes() throws IOException {

        int numNodes = graph.getNumNodes(graphTest);

        Assert.assertEquals("NumNodes test failed", 6, numNodes);
    }

    @Test
    public void testRemoveNode() throws IOException {

        graph.removeNode(graphTest, "f");
        int numNodes = graph.getNumNodes(graphTest);

        Assert.assertEquals("RemoveNode test failed", 5, numNodes);
    }

    @Test
    public void testAddNode() throws IOException {

        graph.addNode(graphTest, "g");
        int numNodes = graph.getNumNodes(graphTest);

        Assert.assertEquals("RemoveNode test failed", 7, numNodes);
    }

    @Test
    public void testNumEdges() throws IOException {

        int numEdges = graph.getNumEdges(graphTest);

        Assert.assertEquals("NumEdges test failed", 5, numEdges);
    }
    @Test
    public void testRemoveEdge() throws IOException {

        graph.removeEdge(graphTest, "b", "e");
        int numEdges = graph.getNumEdges(graphTest);

        Assert.assertEquals("NumEdges test failed", 4, numEdges);
    }


}

