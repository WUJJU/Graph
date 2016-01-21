

import java.util.LinkedList;


public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private CTC[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public PrimMST(Graph G) {
        edgeTo = new CTC[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
            if (!marked[v]) prim(G, v);      // minimum spanning forest

        // check optimality conditions
       // assert check(G);
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(Graph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);//scan the neighbor of vertex v
        }
    }

    // scan vertex v
    private void scan(Graph G, int v) {
        marked[v] = true;
        LinkedList<CTC>list;
        for(int i=0;i<G.adj[v].size();i++){
           list=G.adj[v];
          int w= list.get(i).getCn2()-1;
          if (marked[w]) continue; //v-w is obsolete edge
           if(list.get(i).getDistance()<distTo[w]){
               distTo[w]=list.get(i).getDistance();
               edgeTo[w]=list.get(i);
               if(pq.contains(w)) pq.decreaseKey(w, distTo[w]);
               else               pq.insert(w, distTo[w]);
           }
        }
     
        
        
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<CTC> edges() {
        Queue<CTC> mst = new Queue<CTC>();
        for (int v = 0; v < edgeTo.length; v++) {
            CTC e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        double weight = 0.0;
        
        for (CTC e : edges())
            weight += e.getDistance();
        return weight;
    }



}