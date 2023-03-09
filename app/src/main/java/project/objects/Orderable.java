package project.objects;

public class Orderable {
    private String name;
    private Float price;
    private Integer shelfLife;

    public Orderable(String name, Float price, Integer shelfLife) {
        this.name = name;
        this.price = price;
        this.shelfLife = shelfLife;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }
}
