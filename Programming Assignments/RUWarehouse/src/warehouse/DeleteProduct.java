package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
       
        Warehouse w = new Warehouse();
        w.addProduct(2, "fob", 2, 2, 2);
        w.deleteProduct(2);
        StdOut.println(w);
       
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test deleteProduct
    }
}
