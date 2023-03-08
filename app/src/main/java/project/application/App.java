/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package project.application;

import project.display.Display;
import project.displayold.*;
import project.logic.CouponManagerLogic;
import project.logic.OrderLogic;
import project.logic.SaleLogic;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;
import project.persistence.*;

public class App {
    public static void main(String[] args) {

        init();
    }

    public static void init(){

        ProductDatabaseStub productDB =  new ProductDatabaseStub();
        CouponDatabaseStub couponStub = new CouponDatabaseStub();
        OrderableDatabaseStub orderDBstub = new OrderableDatabaseStub();
        OrderLogic oLogic = new OrderLogic(productDB,orderDBstub);
        CouponManagerLogic cpLogic = new CouponManagerLogic(couponStub);
        StockCheckingLogic scLogic = new StockCheckingLogic(productDB);
        StockManagingLogic smLogic = new StockManagingLogic(productDB);
        SaleLogic sLogic = new SaleLogic(productDB, couponStub);

        Display display = new Display(
                scLogic,
                smLogic,
                oLogic,
                cpLogic
        );
//        InitialDisplay in = new InitialDisplay(scLogic,sLogic,cpLogic,smLogic,oLogic);
    }
}
