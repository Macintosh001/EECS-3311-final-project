package project.persistence;

import project.objects.Coupon;
import project.objects.Orderable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouponPersistence implements CouponDatabase{
    private DatabaseManager db;

    public CouponPersistence(){
        this.db = new DatabaseManager();
        if(!db.databaseExists()) {
            db.createDB();
        }
        db.forceCurrentVersion();
    }

    @Override
    public void addCoupon(Coupon coupon) {
        String values = this.getSQLCouponString(coupon);
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("insert into coupon values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeCoupon(String code) {
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from coupon where code ="+ "'"+code+"'");
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void replaceCoupon(Coupon coupon) {
        this.removeCoupon(coupon.getCode());
        this.addCoupon(coupon);
    }

    @Override
    public Coupon getCoupon(String code) {
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select * from coupon where code = " + "'" + code+ "'");
            Coupon coup = extractCouponFromResultSet(res);
            db.terminate();
            return coup;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Coupon> getCouponList() {
        ArrayList<Coupon> coups = new ArrayList<>();
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select code from coupon");
            coups = this.extractCouponListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coups;
    }

    public String getSQLCouponString(Coupon coupon){
        return "(" + "'" + coupon.getCode() + "'" + ", "
                + "'" + coupon.getPercentOff().toString() + "'" + ")";
    }

    private Coupon extractCouponFromResultSet(ResultSet res){
        String code;
        Float percentOff;
        try{
            if(res != null){
                res.next();

                code = res.getString("code");
                percentOff = res.getFloat("percent_off");
                return new Coupon(code, percentOff);
            }
            else {
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Coupon> extractCouponListFromResultSet(ResultSet res){
        ArrayList<Coupon> coups = new ArrayList<>();
        try{
            if(res != null){
                Coupon c;
                while(res.next()){
                    c = this.getCoupon(res.getString("code"));
                    coups.add(c);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return coups;
    }
}
