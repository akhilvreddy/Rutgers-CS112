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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {

    //our main adjacency list 
    private HashMap<String, ArrayList<String>> adjList = new HashMap<>(); 

    public AdjList(String inputFile){
        StdIn.setFile(inputFile);

        int a = StdIn.readInt(); 
        StdIn.readLine(); 

        for (int i = 0; i < a; i++){
            String s = StdIn.readLine(); 
            adjList.put(s, null);
        }

        int b = StdIn.readInt(); 
        StdIn.readLine(); 

        for (int i = 0; i < b; i++){
            String[] s = StdIn.readLine().split(" ");

            if (adjList.get(s[0]) != null)
                adjList.get(s[0]).add(s[1]);

            else{
                ArrayList<String> tempList = new ArrayList<>();
                tempList.add(s[1]);
                adjList.put(s[0], tempList);
            }    
        }
    }


    private void printAdjacencyList(String outputFile){

        StdOut.setFile(outputFile);

        for (String key : adjList.keySet()){
            if(adjList.get(key) == null){
                StdOut.println(key);
            }
            else {
                String requirement = "";
                for (int i = 0; i < adjList.get(key).size(); i++){
                    requirement += adjList.get(key) + " ";
                }
                StdOut.println(key + " " + requirement);
            }
        }

    }

    public HashMap<String, ArrayList<String>> getAdjList(){
        return adjList; 
    }

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        AdjList adjList2 = new AdjList(args[0]);
        adjList2.printAdjacencyList(args[1]);

    }
}
