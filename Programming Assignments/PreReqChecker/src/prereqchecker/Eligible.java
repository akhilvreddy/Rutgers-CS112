package prereqchecker;
import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    
    public static HashSet<String> bfs(HashMap<String, ArrayList<String>> hm, String[] courses) {
            
        HashSet<String> marked = new HashSet<>();
        LinkedList<String> q = new LinkedList<String>();

        for (String course : courses) {
            marked.add(course);
            q.add(course);
        }

        while (!q.isEmpty()) { // while the q is not empty
            String req = q.pop();
            if (hm.get(req) == null) { // we grab first off of q to check its prereqs
                marked.add(req);
            } 
            else {
                for (String s : hm.get(req)) { // for each prereq

                    if (!marked.contains(s)) { // if it is has not been checked
                        
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
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        StdOut.setFile(args[2]);
        StdIn.setFile(args[1]); // eligibility file
        int c = StdIn.readInt();
        StdIn.readLine();
        String[] takenCourses = new String[c];
        for (int i = 0; i < c; i ++) {
            takenCourses[i] = StdIn.readLine();
        }
        AdjList adjList = new AdjList(args[0]);
        HashMap<String, ArrayList<String>> hm = adjList.getAdjList();
       
        HashSet<String> allTaken = new HashSet<>();
    
        //System.out.println(bfs(hm, takenCourses));
        
        allTaken = bfs(hm, takenCourses);

        ArrayList<String> eligible = new ArrayList<>();
        
        for (String course:hm.keySet()) {
            if (!allTaken.contains(course)) {
                if (hm.get(course) == null) {
                    eligible.add(course);
                }
                else {
                    ArrayList<String> prereqs = hm.get(course);
                    if (allTaken.containsAll(prereqs)) {
                        eligible.add(course);
                    }
                }
            }
            
            
            
        }
        for (int i = 0; i < eligible.size(); i ++) {
            StdOut.println(eligible.get(i));
        }
    }
    
}


// how to run: 
// c:; cd 'c:\Users\reddy\Documents\GitHub\CS112\Programming Assignments\PreReqChecker'; & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.3.7-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\reddy\AppData\Roaming\Code\User\workspaceStorage\4f2d78c4078b20c9906f3b4847e0574f\redhat.java\jdt_ws\PreReqChecker_53217910\bin' 'prereqchecker.Eligible' 'adjlist.in' 'eligible.in' 'eligible.out'