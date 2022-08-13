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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {

    private static String bfs(HashMap<String, ArrayList<String>> hm, String target, String course) {
        String ans = "";

        //base case will see if there are no pre-reqs
        if (hm.get(course) == null) {
            ans = "YES";
            return ans;
        }

        LinkedList<String> q = new LinkedList<String>();
        q.add(course);

        HashMap<String, Boolean> marked = new HashMap<>();
        marked.put(course, true);

        while (!q.isEmpty()) { 
            
            String req = q.pop(); 
            
            if (hm.get(req) == null) {
                ans = "YES";
                return ans;
            }

            for (String s : hm.get(req)) { 

                if (marked.get(s) == null) { 
                    if (s.equals(target)) { 
                        ans = "NO";
                        return ans;
                    }
                    else {
                        q.add(s);
                        marked.put(s, true);
                    }

                }
            }
        }
        return "YES";
    }


    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	
        AdjList adjList = new AdjList(args[0]);
        
        HashMap<String, ArrayList<String>> hm = adjList.getAdjList();

        StdIn.setFile(args[1]);
        String course1 = StdIn.readLine();
        String course2 = StdIn.readLine();

        StdOut.setFile(args[2]);

        String ans = bfs(hm, course1, course2);
        StdOut.println(ans);
    
    }
}

/*
 * PUT THIS IS THE COMMAND LINE: 
 * c:; cd 'c:\Users\reddy\Documents\GitHub\CS112\Programming Assignments\PreReqChecker'; & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.3.7-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\reddy\AppData\Roaming\Code\User\workspaceStorage\4f2d78c4078b20c9906f3b4847e0574f\redhat.java\jdt_ws\PreReqChecker_53217910\bin' 'prereqchecker.ValidPrereq' 'adjlist.in' 'validprereq.in' 'validprereq.out'   
 */