package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {



        
        Warehouse w = new Warehouse();
        w.addProduct(2, "fob", 2, 2, 2);
        StdOut.println(w);


        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
    	// Use this file to test addProduct
    }
}
