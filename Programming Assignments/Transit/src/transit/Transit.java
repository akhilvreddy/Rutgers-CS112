package transit;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer
	//public LinkedList mainList = new LinkedList<TNode>();

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

		LinkedList mainList = new LinkedList<TNode>();
		TNode busZero; 
		TNode walkZero; 

		walkZero = new TNode(0, null, null);
		TNode curr = walkZero;
		mainList.add(walkZero);
		for (int i = 0; i < locations.length; i++){
			TNode temp = new TNode(locations[i],null,null); 
			curr.setNext(temp);
			curr = curr.getNext();
		}


		busZero = new TNode(0,null,walkZero);
		TNode current = busZero;
		mainList.add(busZero);
		for (int i = 0; i < busStops.length; i++){
			TNode temp = new TNode(busStops[i],null,null); 
			current.setNext(temp);
			current = current.getNext();
		}


		trainZero = new TNode(0,null,busZero);
		TNode ptr = trainZero; 
		mainList.add(trainZero);
		for (int i = 0; i < trainStations.length; i++){
			TNode temp = new TNode(trainStations[i], null, null);
			ptr.setNext(temp);
			ptr = ptr.getNext();
		}

		linkLower(trainZero,busZero);
		linkLower(busZero,walkZero);

	}
	

	/**
	 * For two layers of the linkedlist that are next to each other, this method connects the overlapping 
	 * nodes so that there is proper linking
	 * 
	 * @param upperFront A pointer to the first node of the upper layer 
	 * @param lowerFront A pointer to the first node of the lower layer 
	 * 
	 * Returns nothing (void method) 
	 */
	public void linkLower(TNode upperFront, TNode lowerFront){

		TNode upPointer = upperFront;
		TNode downPointer = lowerFront; 

		while (upPointer != null && downPointer != null) {
			
			if(downPointer.getLocation() < upPointer.getLocation())
				downPointer = downPointer.getNext();

			else if(downPointer.getLocation()==upPointer.getLocation()){
				upPointer.setDown(downPointer);
				upPointer = upPointer.getNext();
				downPointer = downPointer.getNext();
			}	
		}	

	}

	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
	    TNode previous = null; 
		TNode current = trainZero; 

		while(current!=null){
			if(current.getLocation()==station){
				current.setDown(null);
				previous.setNext(current.getNext());
				break;
			}
				
			else{
				previous = current; 
				current = current.getNext();
			}
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    
		TNode busZero = trainZero.getDown();
	    TNode previous = new TNode(0); 
		TNode current = busZero; 
		boolean runner; 

		TNode temporaryNode = new TNode(busStop);

		TNode walkingZero = busZero.getDown();
		TNode pointer = walkingZero; 

		while(pointer.getNext()!=null){
			pointer = pointer.getNext();
		}
		int lastLocation = pointer.getLocation();

		if(busStop<=lastLocation && busStop>0){
			runner = true;
		}
		else{
			runner = false; 
		}

		TNode currPointer = walkingZero;
		TNode downNode = null;  
		while(currPointer!=null){
			if(currPointer.getLocation()==busStop)
				downNode = currPointer;
			currPointer = currPointer.getNext();
		}

		if (runner){
			while(current!=null){
				
				//have a condition for when busStop is equal to that already value -> break
				//have a condition for when it is after the last -> just make a new node after then link (easy)
				//have a condition for when it is in between two values -> create new node, previous.getNext is that then new node.setNext(currrent)
					//how to link them? loop through until you find the node and then newNode.setDown(node you just found)
				
				//Condition where busStop is already in the bus layer 	
				if(current.getLocation()==busStop)
					return;
				
				//Condition where busStop is between two nodes in the the layer
				if(previous.getLocation()<busStop && current.getLocation()>busStop){
					previous.setNext(temporaryNode);
					//System.out.println("The previous node location is "+previous.getLocation());
					temporaryNode.setNext(current);
					temporaryNode.setDown(downNode);
					
					//System.out.println("Previous: "+previous.getLocation()+" Current: "+current.getLocation());
				}

				//Condition where busStop is after the final node in the layer	
				if (current.getLocation()<busStop && current.getNext()==null){
					current.setNext(temporaryNode);
					temporaryNode.setDown(downNode);
				}	
				

				
				previous = current; 
				current = current.getNext();	
			}
		}

	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {

	    ArrayList<TNode> bestPathList = new ArrayList<TNode>();
		
		TNode searchNode = trainZero; 
		bestPathList.add(searchNode);
		boolean found = false; 
		

		while(found==false){
			
			//Condition: There are still values to visit on your layer
			if(searchNode.getNext()!=null && searchNode.getNext().getLocation()<=destination){
				searchNode = searchNode.getNext();
			}

			//Condition: You are done with layer and must go down
			else{
				if(searchNode.getDown()!=null)
					searchNode = searchNode.getDown();
			}

			bestPathList.add(searchNode);

			if(searchNode.getLocation()==destination && searchNode.getDown()==null)
				found = true; 

		}

	    return bestPathList;


	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {

		TNode originalTrainZero = trainZero;
		TNode originalBusZero = trainZero.getDown();
		TNode originalWalkZero = trainZero.getDown().getDown();

		TNode trainZeroCopy, busZeroCopy, walkZeroCopy; 
		walkZeroCopy = new TNode(originalWalkZero.getLocation(), null, null); 
		busZeroCopy = new TNode(originalBusZero.getLocation(), null, walkZeroCopy);
		trainZeroCopy = new TNode(originalTrainZero.getLocation(),null,busZeroCopy);
			
		TNode current1 = originalWalkZero.getNext();
		TNode current2 = walkZeroCopy;
		while(current1!=null){	 
			TNode tempNode = new TNode(current1.getLocation());
			current2.setNext(tempNode);
			current1 = current1.getNext();
			current2 = current2.getNext();
		}

		TNode current3 = originalBusZero.getNext();
		TNode current4 = busZeroCopy;
		while(current3!=null){	 
			TNode tempNode = new TNode(current3.getLocation());
			current4.setNext(tempNode);
			current3 = current3.getNext();
			current4 = current4.getNext();
		}

		TNode current5 = originalTrainZero.getNext();
		TNode current6 = trainZeroCopy;
		while(current5!=null){	 
			TNode tempNode = new TNode(current5.getLocation());
			current6.setNext(tempNode);
			current5 = current5.getNext();
			current6 = current6.getNext();
		}

		linkLower(trainZeroCopy,busZeroCopy);
		linkLower(busZeroCopy,walkZeroCopy);

	    return trainZeroCopy;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

		TNode scooterZero = new TNode(0, null, trainZero.getDown().getDown());
		trainZero.getDown().setDown(scooterZero);

		TNode busZero = trainZero.getDown();
		TNode currentPointer = busZero.getNext();
		while(currentPointer!=null){
			currentPointer.setDown(null);
			currentPointer = currentPointer.getNext();
		}

		TNode curr = scooterZero;
		for (int i = 0; i < scooterStops.length; i++){
			TNode temp = new TNode(scooterStops[i],null,null); 
			curr.setNext(temp);
			curr = curr.getNext();
		}

		TNode walkingZero = scooterZero.getDown();
		
		linkLower(busZero,scooterZero);
		linkLower(scooterZero,walkingZero);

	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
