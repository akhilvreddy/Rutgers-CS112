package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        
        Warehouse w = new Warehouse();
        w.addProduct(933,"prod0",24,72,0);
        w.addProduct(934,"WaterBottle",2,7,0);
        w.addProduct(935,"WaterBottle",2,7,0);
        w.addProduct(936,"WaterBottle",2,7,0);
        w.addProduct(937,"WaterBottle",2,7,0);
        
        w.addProduct(938,"WaterBottle",2,7,0);

        StdOut.println(w);

    
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

    	// Use this file to test addProduct

    }
}
