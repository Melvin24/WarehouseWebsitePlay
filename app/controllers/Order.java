package controllers;

/**
 * Created by mjo39 on 27/09/15.
 */
import java.util.Date;


public class Order {

    public int orderID;
    public String date;
    public int orderStatus;

    public Order(){

    }

    public Order(int inOrderID, String inDate, int inOrderStatus){
        this.orderID = inOrderID;
        this.date = inDate;
        this.orderStatus = inOrderStatus;
    }



    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

}
