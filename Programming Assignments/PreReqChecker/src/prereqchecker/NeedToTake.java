package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {

    public static HashSet<String> bfs(HashMap<String, ArrayList<String>> hm, String[] courses) {
            
        HashSet<String> marked = new HashSet<>();
        LinkedList<String> q = new LinkedList<String>();

        for (String course : courses) {
            marked.add(course);
            q.add(course);
        }

        while (!q.isEmpty()) { 
            String req = q.pop();
            if (hm.get(req) == null) { 
                marked.add(req);
            } 
            else {
                for (String s : hm.get(req)) { 

                    if (!marked.contains(s)) { 
                        
                        q.add(s);
                        marked.add(s);
    
                    }
                }
            }
        }
        return marked;
    }

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
        
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        String[] target = new String[1];
        target[0] = StdIn.readLine();
        int d = StdIn.readInt();
        StdIn.readLine();
        String[] takenCourses = new String[d];
        for (int i = 0; i < d; i ++) {
            takenCourses[i] = StdIn.readLine();
        }
        
        AdjList adjList = new AdjList(args[0]);
        HashMap<String, ArrayList<String>> hm = adjList.getAdjList();
        
        HashSet<String> needToTake = new HashSet<>();
       
        HashSet<String> allTaken = new HashSet<>();
        
        allTaken = bfs(hm, takenCourses);
        needToTake = bfs(hm, target);
        for (String s : allTaken) {
            if (needToTake.contains(s)) needToTake.remove(s);
        }
        needToTake.remove(target[0]);
        for (String course : needToTake) {
            StdOut.println(course);
        }   
    }
}

/*
 * PUT THIS IS THE COMMAND LINE: 
 * c:; cd 'c:\Users\reddy\Documents\GitHub\CS112\Programming Assignments\PreReqChecker'; & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.3.7-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\reddy\AppData\Roaming\Code\User\workspaceStorage\4f2d78c4078b20c9906f3b4847e0574f\redhat.java\jdt_ws\PreReqChecker_53217910\bin' 'prereqchecker.NeedToTake' 'adjlist.in' 'needtotake.in' 'needtotake.out' 
 */