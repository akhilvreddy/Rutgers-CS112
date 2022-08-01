public void addEdge(int v1, int v2){

   if ((x>=n) || (y>n))
	return; //in this case, vertex, doesn't exist 

   if (x==y)
	return; //this is the same vertex in that case

   else{
	vertices[v1][v2]=1; 
	vertices[v2][v1]=1; 
   }

}