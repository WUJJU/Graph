

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;


public class Airline {
   
    public static String []cityN;
    public static void  print(Object i){
        System.out.println(i);
    }
    public static Graph insertG(Graph g,Scanner s){
        int v=s.nextInt();
        g=new Graph(v);
       cityN=new String [v];
        for(int i=0;i<v;i++){
           String cn= s.next();
           print(i+":"+cn);
           print("-----------");
           cityN[i]=cn;
      
        }
        s.nextLine();
        while(s.hasNextLine()){
  
            print("********************");
           
            String cc=s.nextLine();
            print(cc);
            String []substring=cc.split(" ");
            double[]a=new double[substring.length];
            for(int i=0;i<a.length;i++){
                a[i]=Double.parseDouble(substring[i]);
            }
            int p1=(int)a[0]-1;
            int p2=(int)a[1]-1;
            CTC c1=new CTC(cityN[p1],cityN[p2],(int)a[0],(int)a[1],a[2],a[3]);
            CTC c2=new CTC(cityN[p2],cityN[p1],(int)a[1],(int)a[0],a[2],a[3]);
            g.addEdge(p1, p2, c1, c2);
        }
        return g;
        
    }
    public static void pathBelow(Graph g,double bound, double total, CTC ctc, String s, UF uf){
        for(int j=0;j< g.adj[ctc.getCn2()-1].size();j++){
            CTC cc=g.adj[ctc.getCn2()-1].get(j);
            int p=cc.getCn1()-1;
            int q=cc.getCn2()-1;
            if(!uf.connected(p, q)){
                total += cc.getPrice();
                s += "---" + (int)cc.getPrice() + "---" + cc.getName2();
                
                if(total <= bound){
                        
                        uf.union(p, q);
                       print("TotalCost: " + total);
                      print("Path: " + s);
                       
                  pathBelow(g,bound, total, cc, s, uf);
                }
                
                total -= cc.getPrice();
                int index = s.indexOf(cc.getName1()) + cc.getName2().length();
                s = s.substring(0, index);
            }
                
            }
        }
    
        

    public static void main(String[] args) throws FileNotFoundException {
        
        print("Select files:input 'a1' for airline_data1.txt; input 'a2' for airline_data2.txt ");
        Graph graph=new Graph();
   
        Scanner sc=new Scanner(System.in);
        
        String s=sc.nextLine();
     
        if(s.equals("a1")){
            File file=new File("airline_data1.txt");
            Scanner sca1 = new Scanner(file);
            graph=insertG(graph,sca1);
            print("Complete the Graph");
        }else if(s.equals("a2")){
            File file2=new File("airline_data2.txt");
            Scanner sca2 = new Scanner(file2);
            graph=insertG(graph,sca2);
            print("Complete the Graph");
        }
        print("input 1 to show the graph");
        print("input 2 to show MST");
        print("input 3 to show Shortest Path");
        print("input 4 to show trips whose cost is below some amount");
        print("input 5 to add new route");
        print("input 6 to remove route");
       print("input 7 to enter vertex defining route");
//        print("input 8 to retreve all cars in double PQ");
//        print("input 9 to quite");
        int i=0;
        do{
            i=sc.nextInt();
            if(i==1){
               print("E is:"+ graph.E());
               String allg= graph.toString();
               print("print graph below:");
               print(allg);
            }else if(i==2){
              
                PrimMST mst = new PrimMST(graph);
                for (CTC e : mst.edges()) {
                   print(e.getName1()+" -----> "+e.getName2()+",distance: "+e.getDistance());
                }
               print("total weight of MST: "+ mst.weight());
            }else if(i==3){
                print("input source city name:");
                
                String source=sc.next();
                print("input destination city name:");
                String destination=sc.next();
                print("input D to show Shortest Path based on Distance;");
                print("input P to show Shortest Path based on Cost;");
                print("input H to show Shortest Path based on Hop;");
                String order=sc.next();
                //search city number in cityN array
                int ss=-1;
                int dd=-1;
                for(int t=0;t<cityN.length;t++){
                    if(source.toLowerCase().equals(cityN[t].toLowerCase())) ss=t;
                    if(destination.toLowerCase().equals(cityN[t].toLowerCase())) dd=t;
                }
                if(ss!=-1&&dd!=-1){
                
                    if(order.equals("D")||order.equals("P")){
                        print("source: "+cityN[ss]);
                        print("destination: "+cityN[dd]);
                        DijkstraSP sp = new DijkstraSP(graph, ss,order);
                        sp.printpath(graph, sp, ss,dd,order);
                    }
                 
                    else if(order.equals("H")){

                        print("source: "+cityN[ss]);
                        print("destination: "+cityN[dd]);
                        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, ss);
                        bfs.printpath(graph, bfs, ss, dd);
                   
                    }
                }else{
                    print("wrong city name");
                }
               
            }else if(i==4){
                print("input a dollar amount: ");
                double p=sc.nextDouble();
          
                for(int m=0;m<graph.V();m++){
                    for(int n=0;n<graph.adj[m].size();n++){
                        UF uf=new UF(graph.V());
                        CTC ctc=graph.adj[m].get(n);
                        String start = ctc.getName1()+ "---" + ctc.getPrice() + "---" + ctc.getName2();
                     
                        if(ctc.getPrice()<=p){
                           print("TotalCost:"+ctc.getPrice());
                           print("Path:"+start);
                           print("");
                           uf.union(ctc.getCn1()-1,ctc.getCn2()-1);
                           pathBelow(graph,p,ctc.getPrice(),ctc,start,uf);
                           
                        }
                    }
              
                }
                print("");
            }else if(i==5){
                print("input start city number ");
                int c1=sc.nextInt()-1;
                print("input end city number");
                int c2=sc.nextInt()-1;
                print("input price");
                double p=sc.nextDouble();
                print("input distance");
                double d=sc.nextDouble();
                for(int t=0;t<graph.adj[c1].size();t++){
                    if(graph.adj[c1].get(t).getCn2()==c2+1){
                        print("route already exist");
                        break;
                    }
                }
        
                CTC ctc1=new CTC(cityN[c1],cityN[c2],c1+1,c2+1,d,p);
                CTC ctc2=new CTC(cityN[c2],cityN[c1],c2+1,c1+1,d,p);
                graph.addEdge(c1, c2, ctc1, ctc2);
                
               
            }else if(i==6){
                
                print("input start city number ");
                int c1=sc.nextInt()-1;
                print("input end city number");
                
                int c2=sc.nextInt()-1;
                graph.removeEdge(c1, c2);
            }else if(i==7){
                print("input start city name");
                String c1=sc.next();
                print("input end city name");
                String c2=sc.next();
                print("input price");
                double p=sc.nextDouble();
                print("input distance");
                double d=sc.nextDouble();
                graph.addnewEdge(c1, c2, d, p);
                
            }else if(i==8){
                print("storing changes into orignl files....");
                String file=null;
                if(s.equals("a1")) file="airline_data1.txt";
                else if(s.equals("a2")) file="airline_data2.txt";
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(new File(file));
                   } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                   }

                writer.println(graph.V());
                for(int ii = 0; ii < graph.adj.length; ii++){
                        if(graph.adj[ii].size()>0)
                        writer.println(graph.adj[ii].get(0).getName1());
                }
                for(int jj=0;jj<graph.fileadj.length;jj++){
                    if(graph.fileadj[jj].size()>0){
                    for(int zz=0;zz<graph.fileadj[jj].size();zz++)
                       writer.println(graph.fileadj[jj].get(zz).getCn1()+" "+graph.fileadj[jj].get(zz).getCn2()+" "+
                               graph.fileadj[jj].get(zz).getDistance()+" "+graph.fileadj[jj].get(zz).getPrice());
                    }
                }
               // writer.print(graph.fileWrite());
                writer.close();
                System.out.println("Completed!input 9 to quite.");
            }
            
            
        }while(i!=9);
    }
}