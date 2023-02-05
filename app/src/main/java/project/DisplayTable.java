package project;

import project.objects.Product;
import project.objects.ProductList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DisplayTable {

    /* Perhaps using the singleton pattern to ensure only one table is created?
    What if we want to search for a specific item, should we return a list with only that item?
    Maybe a better idea to display a simple screen with information about the item whereby you order more of the Product
     */

    private JTable table1;
    private JPanel panel1;
    private JFrame frame;

    private Button addButton; // will be mapped to an addProduct operation that adds a Product to the ProductList
    private Button removeButton; // will be mapped to a removeProduct operation that removes a product from the list
    private Button searchButton; // will be mapped to a findProduct operation that will find a product in the list and return it
    public DisplayTable(String[][] data){
        frame = new JFrame();
        frame.setTitle("Basic Table");

        // Column Names
        String[] columnNames = { "ID", "Item name", "Quantity", "Price", "Expiry Date" };

        // initializing the JTable
        table1 = new JTable(data, columnNames);
        table1.setBounds(30,40,200,300);
        // adding the JTable to the scroll pane
        JScrollPane sp = new JScrollPane(table1);
        frame.add(sp);
        //frame size
        frame.setSize(500,200);
        // make frame visible
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // driver method
    public static void main(String[] args) {
        Date expiry = new Date();
        Product p1;
        Product p2;
        Product p3;
        Product p4;

        p1 = new Product("dummy", 1, 2.99f, expiry);
        p2 = new Product("dummy", 1, 2.99f, expiry);
        p3 = new Product("dummy", 1, 2.99f, expiry);
        p4 = new Product("dummy", 1, 2.99f, expiry);

        List<Product> products = new ArrayList<Product>();
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p1);
        ProductList products1 = new ProductList(products);

        new DisplayTable(products1.getTableEntries());
    }
}
