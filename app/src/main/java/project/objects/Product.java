package project.objects;

import java.util.Date;

public class Product {
    private Integer barcode;
    private String name;
    private Integer quantity;
    private Float price;
    private Date expityDate;

    private static int nextBarcode = 0;

    public Product(Integer barcode, String name, Integer quantity, Float price, Date expityDate) {
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expityDate = expityDate;
    }
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

    // This method should only be called once when we load the database!!!
    public void setNextBarcode(Integer barcode) {
        Product.nextBarcode = barcode;
    }
}
