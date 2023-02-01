package project.objects;

import java.util.Date;

public class Product {
    private Integer barcode;
    private String name;
    private Integer quantity;
    private Float price;
    private Date expityDate;

    private static int nextBarcode = 0;

    public Product(String name, Integer quantity, Float price, Date expityDate) {
        this.barcode = nextBarcode;
        nextBarcode++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expityDate = expityDate;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    public Date getExpityDate() {
        return expityDate;
    }
}
