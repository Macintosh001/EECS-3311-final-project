package project.display.views.builders;

import project.displayold.Builder;

public class Director {

    private StockCheckingBuilder builder;

    public Director(StockCheckingBuilder builder){
        this.builder = builder;
    }

    public StockCheckingBuilder getBuilder() {
        return builder;
    }
}
