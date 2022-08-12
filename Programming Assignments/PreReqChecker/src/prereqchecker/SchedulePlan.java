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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
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

    public static ArrayList<ArrayList<String>> scheduleBuilder(HashMap<String, ArrayList<String>> hm, HashSet<String> allTaken, HashSet<String> needToTake) {
        
        //System.out.println("All courses taken are: " + allTaken);
        //System.out.println("All courses needed to be taken are: " + needToTake);
        LinkedList<String> q = new LinkedList<>();

        ArrayList<ArrayList<String>> schedule = new ArrayList<>();
        
        
        for (String key : needToTake) {
            q.add(key);
        }

        //System.out.println("The Queue is: "  + q);

        while(!q.isEmpty()) {
            
            ArrayList<String> semester = new ArrayList<>();
            
            for (int i = 0; i < q.size(); i ++) {
                
                if (hm.get(q.get(i)) == null) { //Its a course we havent taken that we are eligible to take
                    String toAdd = q.get(i);
                    //System.out.println(toAdd + " is eligible to take");
                    //allTaken.add(toAdd);
                    //System.out.println("After adding " + toAdd + " All courses taken are: " + allTaken);
                    semester.add(toAdd);
                    //System.out.println("The semester is: " + semester);
                    //System.out.println("After removing, the q is: " + q);
                }
                else { //if we have taken all the prereqs of the course, we can add it
                    ArrayList<String> prereqs = hm.get(q.get(i));
                    if (allTaken.containsAll(prereqs)) {
                        String toAdd = q.get(i);
                        //System.out.println(toAdd + " is eligible to take");
                        //allTaken.add(toAdd);
                        //System.out.println("After adding " + toAdd + " All courses taken are: " + allTaken);
                        semester.add(toAdd);
                        //System.out.println("The semester is: " + semester);
                        //System.out.println("After removing, the q is: " + q);

                    }
                }
            }
            
            for (int j = 0; j < semester.size(); j ++) {
                allTaken.add(semester.get(j));
                q.remove(semester.get(j));
                //System.out.println(semester.get(j) + " removed from q. Q is: " + q);
                //System.out.println(semester.get(j) + " added to allTaken: " + allTaken);
            }
            schedule.add(semester);

        }
        return schedule;
    }
    
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }
        
        StdIn.setFile(args[1]); // set files for input and output
        StdOut.setFile(args[2]);
        
        String[] target = new String[1];
        target[0] = StdIn.readLine(); // read target
        int e = StdIn.readInt(); //read number of taken courses
        StdIn.readLine(); // buffer
        
        // add taken courses to an array
        String[] courses = new String[e];
        for (int i = 0; i < e; i ++) {
            courses[i] = StdIn.readLine();
        }

        AdjList adjList = new AdjList(args[0]);
        HashMap<String, ArrayList<String>> hm = adjList.getAdjList(); // import adjacency list
        
        HashSet<String> allTaken = bfs(hm, courses); // get all courses taken
        //System.out.println(allTaken);
        HashSet<String> needToTake = bfs(hm, target);
        for (String s : allTaken) {
            if (needToTake.contains(s)) needToTake.remove(s);

        }

        needToTake.remove(target[0]);
        

        ArrayList<ArrayList<String>> schedule = scheduleBuilder(hm, allTaken, needToTake);

        //System.out.println();
        StdOut.println(schedule.size());
        for (ArrayList<String> semester : schedule) {
            String line = "";
            for (int i = 0; i < semester.size(); i ++) {
                line += semester.get(i) + " ";
            }
            StdOut.println(line);
        }

        
    }
}

//how to run: 
//c:; cd 'c:\Users\reddy\Documents\GitHub\CS112\Programming Assignments\PreReqChecker'; & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.3.7-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\reddy\AppData\Roaming\Code\User\workspaceStorage\4f2d78c4078b20c9906f3b4847e0574f\redhat.java\jdt_ws\PreReqChecker_53217910\bin' 'prereqchecker.SchedulePlan' 'adjlist.in' 'scheduleplan.in' 'scheduleplan.out'