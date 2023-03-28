package project.objects;

public class RestockTask {
    private final String name;
    private final Integer minQuantity;
    private final Integer restockAmount;

    public RestockTask(String name, Integer minQuantity, Integer restockAmount) {
        this.name = name;
        this.minQuantity = minQuantity;
        this.restockAmount = restockAmount;
    }

    public String getName() {
        return name;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public Integer getRestockAmount() {
        return restockAmount;
    }
}
