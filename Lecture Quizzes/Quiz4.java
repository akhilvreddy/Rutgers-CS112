public void insertAt(T item, int index) {
		
   Node<T> current = this.front; 	
   int counter = 0; 
		
   while(counter < index){
      counter++;
      previous = current; 
      current = current.next; 
   }
		
   if(counter==index){
      Node<T> insertNode=new Node<T>(item, current.next);
      current.setNext(insertNode);    
   }
}
