public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(graph.Node startNode, graph.Node destNode){
       strategy.searchAlgorithm(startNode, destNode);
    }
}
