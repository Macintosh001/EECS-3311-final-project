package project.display.views;

import project.display.Display;
import project.display.views.builders.Director;
import project.display.views.builders.StockCheckingBuilder;
import project.logic.StockCheckingLogic;

import javax.swing.*;
import java.awt.*;

public class StockCheck extends JPanel {

    public Director director;

    public StockCheck(Display display, StockCheckingLogic logic){

        super();
        StockCheckingBuilder stockBuilder = new StockCheckingBuilder();
        this.director = new Director(stockBuilder);

        setBounds(0,0,1000,700);
        setLayout(new GridLayout(1,2));



    }
}
