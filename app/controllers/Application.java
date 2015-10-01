
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.mvc.*;
import play.db.*;

import java.sql.*;
import java.util.*;
import views.html.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Application extends Controller {

    private static Connection connection;
    private static List<Order> orderList;
    private static List<Item> itemList;


    public Result index() {
        // TODO Auto-generated method stub
        //Application classObj = new Application();
        init();

        try {
            gatherOrderDetails();

            getItemDetails();
            //writetoJASON();
            renderPage();
            connection.close();
            //System.out.println(orderList.get(0).getOrderID());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

            //System.out.println(orderList.get(0).getOrderID());
        return ok(index.render(orderList, itemList));
    }

    public void renderPage(){
        for(int i=0; i<5; i++){
            index.render(orderList, itemList);
        }
    }

    public void init(){
        orderList = new ArrayList<Order>();
        itemList = new ArrayList<Item>();
        try{
            this.connection = DB.getConnection();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void gatherOrderDetails() throws SQLException{
        Statement st = (Statement) connection.createStatement();
        ResultSet rs = (ResultSet) st.executeQuery("SELECT orders.order_id, orders.date, order_status.name "+
                "FROM orders " +
                "INNER JOIN order_status " +
                "ON orders.order_id=order_status.order_status_id AND order_status.name < 4; ");

        while(rs.next()){
            Order setOrder = new Order(rs.getInt("order_id"), rs.getDate("date").toString(), rs.getInt("name"));
            setOrder.getOrderID();
            orderList.add(setOrder);
        }
        rs.close();
        st.close();
    }

    public void getItemDetails() throws SQLException{
        for(Order order: orderList){

            Statement st = (Statement) connection.createStatement();
            ResultSet rs = (ResultSet) st.executeQuery("SELECT stock_id, quantity "+
                    "FROM ordered_items WHERE order_id = '"+order.getOrderID() +"';");

            Item setItem = new Item();

            while(rs.next()){
                setItem.addToStockIDList(rs.getInt("stock_id"));
                setItem.addToQuantityIDList(rs.getInt("quantity"));
            }
            itemList.add(setItem);

            rs.close();
            st.close();
        }
    }


    public void writetoJASON() throws IOException {
        JSONObject masterObj = new JSONObject();
        for (int i = 0; i < orderList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("OrderID", orderList.get(i).getOrderID());

            obj.put("Date", orderList.get(i).getDate());

            obj.put("OrderStatus", orderList.get(i).getOrderStatus());

            for(int x = 0; x < itemList.get(i).getItemStockId().size(); x++){
                JSONArray list = new JSONArray();
                JSONObject obj2 = new JSONObject();
                obj2.put("StockID", itemList.get(i).getItemStockId().get(x));
                obj2.put("Quantity", itemList.get(i).getItemQuantity().get(x));
                list.add(obj2);
                obj.put("Item" + x, list);
            }
            masterObj.put("Job"+i, obj);
        }

        FileWriter file = new FileWriter("test.json");
        file.write(masterObj.toJSONString());
        file.flush();
        file.close();
    }



}







//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        JSONDB classObj = new JSONDB();
//        classObj.init();
//
//        try {
//            classObj.gatherOrderDetails();
//            classObj.getItemDetails();
//            classObj.writetoJASON();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }







