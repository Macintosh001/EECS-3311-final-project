package project.objects;

import java.util.Date;

public class Product {
    private int barcode;
    private String name;
    private int quantity;
    private float price;
    private Date expityDate;

    public int getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public Date getExpityDate() {
        return expityDate;
    }
}
