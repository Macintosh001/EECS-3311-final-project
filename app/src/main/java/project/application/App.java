/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package project.application;

import project.display.Display;
import project.logic.*;
import project.objects.Coupon;
import project.objects.Orderable;
import project.objects.Product;
import project.persistence.*;

import java.sql.SQLException;
import java.util.Date;

public class App {
    public static void main(String[] args) {

        //new DBConnectionWindow();
        initWithStub();
    }

    public static void initWithSQL(String username, String password) throws SQLException {

        ProductDatabase productDB = null;
        OrderableDatabase orderDB = null;
        CouponDatabase couponDB = null;

        productDB = new ProductPersistence(username, password);
        orderDB = new OrderablePersistence(username, password);
        couponDB = new CouponPersistence(username, password);

        // We want to populate the DB if it's empty!
        if (productDB.getProductList().isEmpty()) {
            productDB.addProduct(new Product(0, "Chips", 100, 0.99f, new Date()));
        }
        if (orderDB.getOrderableList().isEmpty()) {
            orderDB.addOrderable(new Orderable("Chips", 0.99f, 300));
        }
        if (couponDB.getCouponList().isEmpty()) {
            couponDB.addCoupon(new Coupon("SAVE10", 0.1f));
        }

        run(productDB, orderDB, couponDB);
    }

    public static void initWithStub() {
        ProductDatabase productDB = new ProductDatabaseStub();
        OrderableDatabase orderDB = new OrderableDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();

        run(productDB, orderDB, couponDB);
    }

    private static void run(ProductDatabase productDB, OrderableDatabase orderDB, CouponDatabase couponDB) {
        OrderLogic oLogic = new OrderLogic(productDB, orderDB);
        CouponManagerLogic cpLogic = new CouponManagerLogic(couponDB);
        StockCheckingLogic scLogic = new StockCheckingLogic(productDB);
        StockManagingLogic smLogic = new StockManagingLogic(productDB);
        SaleLogic sLogic = new SaleLogic(productDB, couponDB);

        Display display = new Display(
                scLogic,
                smLogic,
                oLogic,
                cpLogic,
                sLogic
        );
    }

    public static void initWithStub(){

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
                cpLogic,
                sLogic
        );
    }

}
