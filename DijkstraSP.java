

import java.util.LinkedList;



/**
 *  The <tt>DijkstraSP</tt> class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <tt>distTo()</tt> and <tt>hasPathTo()</tt> methods take
 *  constant time and the <tt>pathTo()</tt> method takes time proportional to the
 *  number of edges in the shortest path returned.
 *  <p>
 *  For additional documentation,    
 *  see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of    
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private CTC [] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex <tt>s</tt> to every other
     * vertex in the edge-weighted digraph <tt>G</tt>.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public DijkstraSP(Graph G, int s,String model) {
    

        distTo = new double[G.V()];
        edgeTo = new CTC[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            LinkedList<CTC>list=G.adj[v];
            for(int i=0;i<list.size();i++){
                relax(list.get(i),model);
            }
        
        // check optimality conditions
       // assert check(G, s);
    }
    }
    // relax edge e and update pq if changed
    private void relax(CTC e,String model) {
        
        int v = e.getCn1()-1, w = e.getCn2()-1;
        if(model.equals("D")){
        if (distTo[w] > distTo[v] + e.getDistance()) {
            distTo[w] = distTo[v] + e.getDistance();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
        }else if(model.equals("P")){
            if (distTo[w] > distTo[v] + e.getPrice()) {
                distTo[w] = distTo[v] + e.getPrice();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param  v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     *         <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return <tt>true</tt> if there is a path from the source vertex
     *         <tt>s</tt> to vertex <tt>v</tt>; <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>
     *         as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<CTC> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<CTC> path = new Stack<CTC>();
        for (CTC e = edgeTo[v]; e != null; e = edgeTo[e.getCn1()-1]) {
            path.push(e);
        }
        return path;
    }


//    // check optimality conditions:
//    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
//    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
//    private boolean check(EdgeWeightedDigraph G, int s) {
//
//        // check that edge weights are nonnegative
//        for (DirectedEdge e : G.edges()) {
//            if (e.weight() < 0) {
//                System.err.println("negative edge weight detected");
//                return false;
//            }
//        }
//
//        // check that distTo[v] and edgeTo[v] are consistent
//        if (distTo[s] != 0.0 || edgeTo[s] != null) {
//            System.err.println("distTo[s] and edgeTo[s] inconsistent");
//            return false;
//        }
//        for (int v = 0; v < G.V(); v++) {
//            if (v == s) continue;
//            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
//                System.err.println("distTo[] and edgeTo[] inconsistent");
//                return false;
//            }
//        }
//
//        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
//        for (int v = 0; v < G.V(); v++) {
//            for (DirectedEdge e : G.adj(v)) {
//                int w = e.to();
//                if (distTo[v] + e.weight() < distTo[w]) {
//                    System.err.println("edge " + e + " not relaxed");
//                    return false;
//                }
//            }
//        }
//
//        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
//        for (int w = 0; w < G.V(); w++) {
//            if (edgeTo[w] == null) continue;
//            DirectedEdge e = edgeTo[w];
//            int v = e.from();
//            if (w != e.to()) return false;
//            if (distTo[v] + e.weight() != distTo[w]) {
//                System.err.println("edge " + e + " on shortest path not tight");
//                return false;
//            }
//        }
//        return true;
//    }
      public void printpath(Graph G,DijkstraSP sp,int s,int d,String model){
          if(model.equals("D")){
              if (sp.hasPathTo(d)) {
                  System.out.println("TotalDistance:"+ sp.distTo(d));
                  for (CTC e : sp.pathTo(d)) {
             System.out.println(e.getCn1()+"("+e.getName1()+")" +"-"+e.getCn2()+"("+e.getName2()+")" +e.getDistance()+"   ");
                //System.out.println(e+"       ");
                  }
               
                System.out.println();
              }
              else {
               System.out.println("No path between "+s+" and "+d);
              }
          }else if(model.equals("P")){
          
                  if (sp.hasPathTo(d)) {
                      System.out.println("TotalCost:"+ sp.distTo(d));
                      for (CTC e : sp.pathTo(d)) {
                 System.out.println(e.getCn1()+"("+e.getName1()+")" +"-"+e.getCn2()+"("+e.getName2()+")" +e.getPrice()+"   ");
                    //System.out.println(e+"       ");
                      }
                   
                    System.out.println();
                  }
                  else {
                   System.out.println("No path between "+s+" and "+d);
                  }
          }
          
      }

    /**
     * Unit tests the <tt>DijkstraSP</tt> data type.
     */
    

}