public class Driver {
 
    public static void main(String[] args) {
        Node n=new Node(15,null);
        n=new Node(50,n);
        n=new Node(34,n);
        n=new Node(27,n);
        n=new Node(26,n);
        n=new Node(56,n);
    
        //System.out.println(n.data);
        //System.out.println(n.next.data);
        //System.out.println(n.next.next.data);

       while(n!=null){
         if(n.next==null)
           System.out.println("There is no data stored ahead.");
         else
           System.out.println(n.data)
         n=n.next;
       }

    }
}