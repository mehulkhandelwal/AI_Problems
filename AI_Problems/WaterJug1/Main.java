import java.io.*;
import java.util.*;
class Node {
    int jugOne;     
    int jugTwo;     
    public Node(int x,int y){
        this.jugOne = x;
        this.jugTwo = y;
    }
}
public class Main {
    public static LinkedList<Node> visited= new LinkedList<Node>();
    public static Dictionary<String, String> backLink = new Hashtable<String,String>();
    public static Queue<Node> q = new LinkedList<Node>();
    public static Stack<String> sol = new Stack<String>();
    public static int jugOneMax;
    public static int jugTwoMax;
    public static int targetJug1;
    public static int targetJug2;

    public static void main(String args[])throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter capacity of jug one :   ");
        jugOneMax = sc.nextInt();
        System.out.print("Enter capacity of jug two :   ");
        jugTwoMax = sc.nextInt();
        System.out.print("Enter target for Jug1 :   ");
        targetJug1 = sc.nextInt();
        System.out.print("Enter target for Jug2 :   ");
        targetJug2 = sc.nextInt();

        Node initial = new Node(0,0);
        q.add(initial);
        try{
            generateEnque();}
        catch(Exception e){
            System.out.println("No Solution exists");
        }
    }


    public static void generateEnque(){
        Node p = q.poll();
        if(!isVisited(p)){
            visited.add(p);
            generateStates(p);
        }
        generateEnque();
    }

    public static void generateStates(Node p){
        System.out.print("Children of ("+p.jugOne+","+p.jugTwo+") are: ");
        if(p.jugOne < jugOneMax)
        {
            Node pNew = new Node(jugOneMax,p.jugTwo);
            q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugTwo < jugTwoMax)
        {
            Node pNew = new Node(p.jugOne,jugTwoMax);
            q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugOne>0)
        {
            Node pNew = new Node(0,p.jugTwo);
            q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }


        if(p.jugTwo>0)
        {
            Node pNew = new Node(p.jugOne,0);q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugOne<jugOneMax && p.jugTwo!=0)
        {
            if(p.jugOne+p.jugTwo > jugOneMax)
            {
                int One = jugOneMax;
                int Two = p.jugOne+p.jugTwo - jugOneMax;
                Node pNew = new Node(One,Two);
                q.add(pNew);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                {
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                    checkIfSolution(pNew);
                }
            }

            else if(p.jugOne+p.jugTwo < jugOneMax)
            {
                Node pNew = new Node(p.jugOne+p.jugTwo,0);
                q.add(pNew);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugTwo<jugTwoMax && p.jugOne!=0)
        {
            if(p.jugTwo+p.jugOne > jugTwoMax)
            {
                int Two = jugTwoMax;
                int One = p.jugTwo+p.jugOne - jugTwoMax;
                Node pNew = new Node(One,Two);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                {
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                    checkIfSolution(pNew);
                }
            }
            else if(p.jugOne+p.jugTwo < jugTwoMax)
            {
                Node pNew = new Node(0,p.jugOne+p.jugTwo);
                q.add(pNew);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                {
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                    checkIfSolution(pNew);
                }
            }
        }

        System.out.println();
    }

    public static boolean checkIfSolution(Node p){
        if(p.jugOne == targetJug1 && p.jugTwo==targetJug2){
            System.out.print("\n");
            System.out.print("\nSolution\n");
            System.out.print("_______________________________________\n");
            String parent = "("+p.jugOne+","+p.jugTwo+")";

            while(!parent.equals("(0,0)")){

                sol.push(parent);
                parent = backLink.get(parent);
            }

            sol.push("(0,0)");
            for(int i=sol.size()-1;i>=0;i--)
            {
                System.out.println(sol.get(i));
            }
            System.exit(0);}

        return false;
    }

    public static boolean isVisited(Node p){
        for(Node check : visited)
        {
            if(p.jugOne==check.jugOne && p.jugTwo==check.jugTwo)
                return true;
        }
        return false;
    }
}