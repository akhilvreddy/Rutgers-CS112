package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        
        Warehouse w = new Warehouse();

        w.betterAddProduct(149, "prod0", 52, 2, 1);
        //w.betterAddProduct(id, name, stock, day, demand);


        StdOut.println(w);
        
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        // Use this file to test betterAddProduct
    }
}
