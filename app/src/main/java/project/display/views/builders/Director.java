package project.display.views.builders;

public class Director {

    private int labelX_offset = 0;
    private int labelY_offset = 0;
    private int buttonX_offset = 0;
    private int buttonY_offset = 0;
    private int buttonCount = 0;
    private int labelCount = 0;
    private Builder builder;
    public Director(Builder builder){
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
