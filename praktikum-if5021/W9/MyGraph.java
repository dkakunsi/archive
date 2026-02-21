import java.lang.reflect.Array;

public class MyGraph {
    public static void main(String[] args) {
        DirectedGraph<Integer> dg = new DirectedGraph<>(Integer.class);
        Integer in5 = new Integer(5);
        Integer in7 = new Integer(7);
        Integer in3 = new Integer(3);
        Integer in11 = new Integer(11);
        Integer in8 = new Integer(8);
        Integer in2 = new Integer(2);
        Integer in9 = new Integer(9);
        Integer in10 = new Integer(10);
        
        dg.addNode(in5);
        dg.addNode(in7);
        dg.addNode(in3);
        dg.addNode(in11);
        dg.addNode(in8);
        dg.addNode(in2);
        dg.addNode(in9);
        dg.addNode(in10);
        
        dg.addEdge(in5, in11);
        dg.addEdge(in7, in11);
        dg.addEdge(in7, in8);
        dg.addEdge(in3, in8);
        dg.addEdge(in3, in10);
        dg.addEdge(in11, in2);
        dg.addEdge(in11, in9);
        dg.addEdge(in11, in10);
        dg.addEdge(in8, in9);
        
        dg.printAllNodes();
        dg.printAllEdges();

        Integer checker = new Integer(11);
        System.out.println(dg.calculateOutdegree(checker));
        System.out.println(dg.calculateIndegree(checker));
    }
}

class Edge<T> {
    T node1;
    T node2;

    public Edge(T node1, T node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public T getNode1() {
        return node1;
    }

    public void setNode1(T t) {
        node1 = t;
    }

    public T getNode2() {
        return node2;
    }

    public void setNode2(T t) {
        node2 = t;
    }
}

class DirectedGraph<T> {
    private final int NMAX_NODES = 100;
    private final int NMAX_EDGES = 100;
    
    private T[] nodes;
    private Edge<T>[] edges;

    private int nNodes = 0;
    private int nEdges = 0;

    public DirectedGraph(Class clazz) {
        nodes = (T[]) Array.newInstance(clazz, NMAX_NODES);
        edges = new Edge[NMAX_EDGES];
    }

    public void addNode(T node) {
        if (findNode(node) == null) {
            nodes[nNodes] = node;
            nNodes++;
        }
    }

    public void addEdge(T node1, T node2) {
        edges[nEdges] = new Edge<T>(node1, node2);
        nEdges++;
    }

    public void printAllNodes() {
        int i;
        for (i = 0; i < nNodes; i++) {
            System.out.println(nodes[i]);
        }
    }

    public void printAllEdges() {
        int i;
        for (i = 0; i < nEdges; i++) {
            System.out.println(String.format("%s -> %s", edges[i].getNode1(), edges[i].getNode2()));
        }
    }

    public int calculateOutdegree(T node) {
        int i;
        int outDegree = 0;

        for (i = 0; i < nEdges; i++) {
            if (edges[i].getNode1().equals(node)) {
                outDegree++;
            }
        }
        
        return outDegree;
    }

    public int calculateIndegree(T node) {
        int i;
        int inDegree = 0;

        for (i = 0; i < nEdges; i++) {
            if (edges[i].getNode2().equals(node)) {
                inDegree++;
            }
        }
        
        return inDegree;
    }

    public T findNode(T node) {
        boolean found = false;
        int i = 0;
        
        while(!found && i < nNodes) {
            if(node.equals(nodes[i])) {
                return nodes[i];
            }
            
            i++;
        }
        
        return null;
    }

}