package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD 
        sectors[id%10].add(new Product(id, name, stock, day, demand));
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        // IMPLEMENT THIS METHOD
         
        //access the last element and then swim in that up (can't sink because it's at the end)
        sectors[id%10].swim(sectors[id%10].getSize());
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
       // IMPLEMENT THIS METHOD

       //first check if it has all 5 spots full in that sector: 
       if(sectors[id%10].getSize()<5){
            return; 
       }

       //we need to search for the least popular item and find the index
       int valueLeastPopular = Integer.MAX_VALUE;
       int indexLeastPopular = 0; 

       for (int i = 1; i < sectors[id%10].getSize()+1; i++){
         if(sectors[id%10].get(i).getPopularity() < valueLeastPopular){
            indexLeastPopular = i;
            valueLeastPopular = sectors[id%10].get(i).getPopularity();
         }
       }

       //swap least popular element with the last one
       sectors[id%10].swap(indexLeastPopular, sectors[id%10].getSize());

       //since the least popular item is in the end now, we should delete it
       sectors[id%10].deleteLast();

       //you want to sink the item that was at the end is now in the middle
       sectors[id%10].sink(indexLeastPopular);
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        // IMPLEMENT THIS METHOD

        for (int i = 1; i < sectors[id%10].getSize()+1; i++){
            if(sectors[id%10].get(i).getId()==id){
                sectors[id%10].get(i).updateStock(amount);
            }
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        // IMPLEMENT THIS METHOD

        int theIndex = 0; 

        for (int i = 1; i < sectors[id%10].getSize()+1; i++){
            if (sectors[id%10].get(i).getId()==id)
                theIndex = i; 
        }

        //if the id is not there, leave the method
        if(theIndex==0)
            return;

        //swap the item to be deleted with the final index 
        sectors[id%10].swap(theIndex, sectors[id%10].getSize());

        //delete the last element
        sectors[id%10].deleteLast();

        //have to fix the heap so you sink it
        sectors[id%10].sink(theIndex);
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        // IMPLEMENT THIS METHOD

        int indexOfItem = 0; 

        //find index 
        for (int i = 1; i < sectors[id%10].getSize()+1; i++){
            if(sectors[id%10].get(i).getId()==id)
                indexOfItem = i;
        }

        //check if exits 
        if(indexOfItem == 0)
            return; 

        //check if stock is enough
        if(amount > sectors[id%10].get(indexOfItem).getStock())
            return; 

        //change day last bought for product 
        sectors[id%10].get(indexOfItem).setLastPurchaseDay(day);

        //change stock amount 
        sectors[id%10].get(indexOfItem).updateStock(-1*amount);
        
        //change demand amount 
        sectors[id%10].get(indexOfItem).updateDemand(amount);

        //fixing heap - we have to sink the product because it is more popular now
        sectors[id%10].sink(indexOfItem);

        //maybe even sink it too i'm not sure
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD

        //create variables
        int ourID = id; 
        boolean productAdded = false; 
        int counter = 0;

        while(!productAdded){

            if(counter==10){
                addToEnd(id, name, stock, day, demand);
                productAdded = true;
                break;
            }
                
            if(sectors[ourID%10].getSize()==5){
                ourID++;
            }

            else{
                addToEnd(ourID, name, stock, day, demand); 
                productAdded = true;
                break;        
            }
            counter++; 
        }

    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
