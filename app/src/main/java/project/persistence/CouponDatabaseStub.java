package project.persistence;


import project.objects.Coupon;


import java.util.ArrayList;
import java.util.List;

public class CouponDatabaseStub implements CouponDatabase{

    private ArrayList<Coupon> dbStub;


    public CouponDatabaseStub(){
        dbStub = this.getTestContent();
    }
    private ArrayList<Coupon> getTestContent(){
        ArrayList<Coupon> coups = new ArrayList<>();
        Coupon coup1 = new Coupon("coup1", 0.15F);
        Coupon coup2 = new Coupon("coup2", 0.25F);
        coups.add(coup1);
        coups.add(coup2);
        return coups;
    }

    @Override
    public void addCoupon(Coupon coupon) {
        dbStub.add(coupon);
    }

    @Override
    public void removeCoupon(String code) {
        Coupon coup = null;
        for(Coupon c : dbStub){
            if(c.getCode().compareTo(code) == 0){
                coup = c;
                break;
            }
        }
        if(coup != null) {
            dbStub.remove(coup);
        }
    }

    @Override
    public void replaceCoupon(Coupon coupon) {
        this.removeCoupon(coupon.getCode());
        this.addCoupon(coupon);
    }

    @Override
    public Coupon getCoupon(String code) {
        for(Coupon coup: dbStub){
            if(coup.getCode().compareTo(code) == 0){
                return coup;
            }
        }
        return null;
    }

    @Override
    public List<Coupon> getCouponList() {
        return dbStub;
    }
}
