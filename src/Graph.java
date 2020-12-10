public class Graph {
    private int numVertices;
    private int numEdges;
    //adj matrix or list

    public Graph(int n){
    //#of vertices must be "n > 0"
    //#of initialize graph with n vertices

    numVertices = n;
    numEdges = 0;
    //initialize adj matrix/list
    }
    public int getNumEdges() {
        return numEdges;
    }
    public int getNumVertices() {
        return numVertices;
    }
    public void addEdge(Integer V, Integer W, int weight){
    //update adj list/matrix for v
    //update for W
        numEdges++;
    }
    //edge e is part of the graph
    public void addEdge(Edge e){
        Integer V = e.getV();
        Integer W = e.getW();
        int wght = e.getWeight();

        addEdge(V,W,wght);

    }
   // public void removeEdge(Edge e){}
    //public Edge findEdge(Integer v, Integer w){}

    //implement DFS or BFS
}
