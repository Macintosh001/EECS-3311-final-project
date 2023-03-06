/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package project.application;

import project.display.*;
import project.logic.CouponManagerLogic;
import project.logic.OrderLogic;
import project.logic.SaleLogic;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;
import project.objects.Coupon;
import project.persistence.*;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        ProductDatabaseStub productDB =  new ProductDatabaseStub();
        CouponDatabaseStub couponStub = new CouponDatabaseStub();
        OrderableDatabaseStub orderDBstub = new OrderableDatabaseStub();
        OrderLogic oLogic = new OrderLogic(productDB,orderDBstub);
        CouponManagerLogic cpLogic = new CouponManagerLogic(couponStub);
        StockCheckingLogic scLogic = new StockCheckingLogic(productDB);
        StockManagingLogic smLogic = new StockManagingLogic(productDB);

        // The "nextBarcode" for logic has to be hardcoded based on the test data
        // That's set up when the DatabaseStub is initialized.
        //OrderView view = new OrderView(oLogic);
       //InitialDisplay init = new InitialDisplay();
        //CouponManagerView coupon = new CouponManagerView(cpLogic);
        //SaleView sale = new SaleView(sLogic);
        //EmployeeView emp = new EmployeeView(logic);
        ManagerView mv = new ManagerView();

        //StockCheckingView stockCheckingView = new StockCheckingView(scLogic);
       //StockManagingView stockManagingView = new StockManagingView(smLogic);
    }
}
