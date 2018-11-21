/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star_search;

import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;
import javax.naming.spi.DirStateFactory;

/**
 *
 * @author Rayhan
 */
public class Search_Runner {
       State initialState;
       State finalState;
       long RunningTime;
       long StartingTime;
       int MaxNodeExpanded;
       int NodeExpanded;
       int NodeExplored;
       int tempx,posx;
       int tempy,posy;
       int []dx={1,-1,0,0};
       int []dy={0,0,1,-1};
       Hashtable<State, Integer> OpenList;
       Hashtable<State, Integer> CloseList;
       PriorityQueue<Node> priorityQueue;
       int squaresize;
       int heuristics_index=2;
       
       
       
    public Search_Runner(int [][] matrix,int n) {
        OpenList=new Hashtable<>(1000);
        CloseList=new Hashtable<>(1000);
        priorityQueue=new PriorityQueue(1000000, new MyComparator());
        initialState=new State(matrix, n);
        finalState=new State(n);
        finalState.printstate();
        this.squaresize=n;
        MaxNodeExpanded=1000000;
        
        
    }
    int Hamming_Distance(State s)
    {
        int result=0;
        for(int i=1;i<=s.size;i++)
        {
            for(int j=1;j<=s.size;j++)
            {
                if((s.matrix[i][j]-0)==0)
                {
                    continue;
                }
                int temp=s.matrix[i][j];
                //System.out.println("TMEP:"+temp);
                Coord coe=getpos(temp);
                if(coe.x!=i || coe.y!=j)
                {
                 result++;   
                }
            }
        }
        //System.out.println("Hamming e : "+result);
        return result;
    }
    int Manhattan_Distance(State s)
    {
        int result=0;
        for(int i=1;i<=s.size;i++)
        {
            for(int j=1;j<=s.size;j++)
            {
                if((s.matrix[i][j]-0)==0)
                {
                    continue;
                }
                int temp=s.matrix[i][j];
                //System.out.println("TMEPMM:"+temp);
                Coord coe=getpos(temp);
                //System.out.println(i+" "+j+" "+coe.x+" "+coe.y);
                result=result+(Math.abs(coe.x-i)+Math.abs(coe.y-j));
                //System.out.println(result);
            }
        }
        //System.out.println("Manns e : "+result);
        return result;
      
    }
    int Linear_Conflicts(State s)
    {
        int result=Manhattan_Distance(s);
        
        return result;
    }
    int HeuristicsValue(State s)
    {
        if(heuristics_index==1)
        {
            return Hamming_Distance(s);
        }
        if(heuristics_index==2)
        {
            return Manhattan_Distance(s);
        }
        return Linear_Conflicts(s);
        
    }
    public Coord findzero(State s)
    {
        Coord x=null;
        for(int i=1;i<=s.size;i++)
        {
            for(int j=1;j<=s.size;j++)
            {
                //System.out.println(i+" "+j+" "+s.matrix[i][j]);
                if((s.matrix[i][j]-0)==0)
                {
                    
                    //System.out.println("Find Zero at:"+i+" "+j);
                    x= new Coord(i, j);
                }
            }
        }
        return x;
    }
    
    public Coord getpos(int number) {
        if (number % this.squaresize != 0) {
            posx = 1 + number / this.squaresize;
            posy = number % this.squaresize;
        } else {
            posx = number / this.squaresize;
            posy = this.squaresize;
        }
        return new Coord(posx, posy);

    }
    
    Node Search()
    {
        int inv=initialState.CountInversionHelper();
        if( (this.squaresize % 2) == 0)
        {
            System.out.println("Board Size Even! Parity of INversion Count not Invariant!!");
            System.out.println("Inversions: "+ inv);
            Coord tm=findzero(initialState);
            System.out.println("Row of Blank: "+tm.x);
            
            if((tm.x+inv) % 2 == 0)
            {
                System.out.println("Solution Exists for Even Board!!");
            }
            else
            {
                System.out.println("Solution Doesnt Exist for Even Board!!");
                return null;
            }
            
        }
        else
        {
            System.out.println("Board Size Odd! Parity of Inversion count is enough!!!");
            System.out.println("Inversions: "+ inv);
            
            if ((inv) % 2 == 0) {
                System.out.println("Solution Exists for Odd Board!!");
            } else {
                System.out.println("Solution Doesnt Exist for Odd Board!!");
                return null;
            }
            
        }
        Node Result;
        int init_h_cost=HeuristicsValue(initialState);
        System.out.println("Initial Heuristics Value: "+ init_h_cost);
        Node node=new Node(initialState,0,init_h_cost);
        NodeExpanded=0;
        NodeExplored=0;
        node.parent=null;
        priorityQueue.clear();
        priorityQueue.add(node);
        OpenList.clear();
        CloseList.clear();
        OpenList.put(initialState, 0);
        while(!priorityQueue.isEmpty())
        {
            //System.out.println("Rayhan");
            NodeExpanded++;
            if(NodeExpanded>MaxNodeExpanded)
            {
                System.out.println("MaxLimit Exceeded with: "+NodeExpanded);
                return null;
            }
            Node current=priorityQueue.remove();
            //current.nodeState.printstate();
            
            Coord coo=findzero(current.nodeState);
            //System.out.println("Find Zero:"+coo.x+" "+coo.y);
            int x,y;
            for(int k=0;k<4;k++)
            {
                x=coo.x+dx[k];
                y=coo.y+dy[k];
                //System.out.println(x+","+y);
                if(x<1 || x>this.squaresize || y<1 || y>this.squaresize)
                {
                    continue;
                }
                //System.out.println(x+","+y);
                State childState=new State(current.nodeState,coo.x,coo.y,x,y);
                
                NodeExplored++;
                if(CloseList.containsKey(childState) || OpenList.containsKey(childState))
                {
                    continue;
                }
                int temp_h_cost=HeuristicsValue(childState);
                Node temp=new Node(childState, (current.g_cost)+1,temp_h_cost);
                //temp.nodeState.printstate();
                temp.parent=current;
                priorityQueue.add(temp);
                OpenList.put(childState, 0);
                if(temp.nodeState.equals(finalState))
                {
                    Result =temp;
                    System.out.print("Node Expanded for BFS:");
                    System.out.println(this.NodeExpanded);
                    System.out.println("Node Explored for BFS:" + NodeExplored + "\n");
                    //System.out.println("Milliseconds!!");
                    return Result;
                }
            }
            CloseList.put(current.nodeState, 0);
            OpenList.remove(current.nodeState, 0);
            
            
        }
        System.out.println("But No Solution Found!!");
        return null;
    }
    
    
    void printpath(Node end)
    {
        Stack<Node> running=new Stack<>();
        while(end!=null)
        {
            running.push(end);
            end=end.parent;
        }
        while(!running.isEmpty())
        {
            Node x=running.pop();
            x.nodeState.printstate();
            System.out.println("|||");
            System.out.println("|||");
            System.out.println("---");
            System.out.println("\\|/");
            
        }
    }
    
       
       
       
}
