

import java.util.LinkedList;

public class Graph {
         
    private int V;//vertex
    private int E;//edge
    public LinkedList<CTC>[]adj;//adjacent list
    public LinkedList<CTC>[]fileadj;//for output
  
    public Graph(){
        
    }
    public Graph(int V){
        this.V=V;
        this.E=0;
        adj=(LinkedList<CTC>[])new LinkedList[V];
        fileadj=(LinkedList<CTC>[])new LinkedList[V];
        
        for(int i=0;i<V;i++){
            adj[i]=new LinkedList<CTC>();
            fileadj[i]=new LinkedList<CTC>();
        }
    }
   
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }
    public String toString(){
        StringBuilder s=new StringBuilder();
        for(int i=0;i<V;i++){
            if(adj[i].size()>0){
                
            
            s.append(adj[i].get(0).getCn1()+" "+adj[i].get(0).getName1()+":");
            for(int j=0;j<adj[i].size();j++){
            s.append(adj[i].get(j).getCn2()+" "+adj[i].get(j).getName2()+"(distance:"+adj[i].get(j).getDistance()+" price:"+adj[i].get(j).getPrice()+");");
            }
            s.append("\n");
            }else{
                s.append("");
            }
        }
        return s.toString();
    }
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w,CTC cv,CTC cw) {
        validateVertex(v);
        validateVertex(w);
        E++;
        fileadj[v].add(cv);
        adj[v].add(cv);
        adj[w].add(cw);
    }
    public void removeEdge(int v,int w){
        validateVertex(v);
        validateVertex(w);
        E--;
        for(int i=0;i<fileadj[v].size();i++){
            if( fileadj[v].get(i).getCn2()==w+1){
                fileadj[v].remove(i);
            }
         }
        for(int i=0;i<adj[v].size();i++){
           if( adj[v].get(i).getCn2()==w+1){
               adj[v].remove(i);
           }
        }
        
        for(int i=0;i<adj[w].size();i++){
            if( adj[w].get(i).getCn2()==v+1){
                adj[w].remove(i);
            }
         }
    }
    public void addnewEdge(String c1,String c2,double d,double p){
        int v=V;
        int w=V+1;
        System.out.println("v is "+v);
        System.out.println("w is"+w);
        
        if(V==adj.length){
            LinkedList<CTC>[]adj2=(LinkedList<CTC>[])new LinkedList[w+1];
            LinkedList<CTC>[]adj3=(LinkedList<CTC>[])new LinkedList[w+1];
            
            for(int i=0;i<V+2;i++){
                adj2[i]=new LinkedList<CTC>();
                adj3[i]=new LinkedList<CTC>();
            }
            for(int i=0;i<adj.length;i++){
                adj2[i]=adj[i];
               
            }
            for(int i=0;i<fileadj.length;i++){
                adj3[i]=fileadj[i];
            }
            V=V+2;
           System.out.println("V is:"+V ); 

           fileadj=(LinkedList<CTC>[])new LinkedList[V];
            adj=(LinkedList<CTC>[])new LinkedList[V];
            for(int i=0;i<adj.length;i++){
                adj[i]=adj2[i];
                fileadj[i]=adj3[i];
            }
            CTC cv=new CTC(c1,c2,v+1,w+1,d,p);
            CTC cw=new CTC(c2,c1,w+1,v+1,d,p);
            
            addEdge(v,w,cv,cw);
        }
        
        
    }
    public static void main(String[] args) {
    
        
    }
}
