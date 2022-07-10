public void rightRotate(RBT x){

	RBTNode y = x.left; 
	x.left = y.right; 

	if(y.right!=null)
		y.right.p = x;

	y.p=x.p;

	if(x.p==null)
		root=y;

	else if(x==x.p.right)
		x.p.right = y; 

	else
		x.p.left = y; 

	y.right = x; 
	x.p = y; 

}