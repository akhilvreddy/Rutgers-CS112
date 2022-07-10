%Preorder traversing
public void preOrder(TreeNode node){

   if(node == null)
	return; 

   System.out.printf("%s ", node.data); 
   preOrder(node.left);
   preOrder(node.right);

}

%Postorder traversing
public void postOrder(TreeNode node){

   if(node == null)
	return; 

   postOrder(node.left);
   postOrder(node.right);
   System.out.printf("%s ", node.data); 
}