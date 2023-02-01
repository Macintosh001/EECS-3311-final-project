package project.objects;

import java.util.Date;

public class Product {
    private final Integer barcode;
    private final String name;
    private final Integer quantity;
    private final Float price;
    private final Date expityDate;

    public Product(Integer barcode, String name, Integer quantity, Float price, Date expityDate) {
        this.barcode = barcode;
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
