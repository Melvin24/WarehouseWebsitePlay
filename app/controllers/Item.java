package controllers;

/**
 * Created by mjo39 on 27/09/15.
 */
import java.util.ArrayList;
import java.util.List;


public class Item {
    List<Integer> itemStockList;
    List<Integer> itemQuantityList;
    public Item(){
        this.itemStockList = new ArrayList<Integer>();
        this.itemQuantityList = new ArrayList<Integer>();
    }

    public Item(int inStockID, int inQuantity){

        addToStockIDList(inStockID);
        addToQuantityIDList(inQuantity);
    }

    public void addToQuantityIDList(int inQuantity) {
        this.itemQuantityList.add(inQuantity);
    }

    public void addToStockIDList(int stockId){
        this.itemStockList.add(stockId);
    }


    public List<Integer> getItemStockId(){
        return itemStockList;
    }

    public List<Integer> getItemQuantity(){
        return itemQuantityList;
    }
}
