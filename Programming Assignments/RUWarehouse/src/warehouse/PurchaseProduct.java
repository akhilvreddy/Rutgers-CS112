package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        
        Warehouse w = new Warehouse();
        w.addProduct(2, "fob", 20, 2, 2);
        w.purchaseProduct(2, 3, 10);
        StdOut.println(w);
        
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test purchaseProduct
    }
}
