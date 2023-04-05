package project.persistence;


import project.objects.Coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * A coupon database stub, an implementor of the coupon database interface, used for simulation of real database functionality
 * during testing. Stub is loaded with fixed data so that it will have predictable behavior
 * for testing. Stores temporary coupon objects during runtime.
 */
public class CouponDatabaseStub implements CouponDatabase{

    private ArrayList<Coupon> dbStub;

    /**
     * Constructor: load stub with fixed content for predictable starting behavior
     */
    public CouponDatabaseStub(){
        dbStub = this.getTestContent();
    }

    /**
     * Creates starting data for stub
     * @return an arrayList of coupon objects that represent fixed starting data for predictable behavior during testing
     */
    private ArrayList<Coupon> getTestContent(){
        ArrayList<Coupon> coups = new ArrayList<>();
        Coupon coup1 = new Coupon("coup1", 0.15F);
        Coupon coup2 = new Coupon("coup2", 0.25F);
        coups.add(coup1);
        coups.add(coup2);
        return coups;
    }

    /**
     *  add a coupon to stub
     * @param coupon to be added
     */
    @Override
    public void addCoupon(Coupon coupon) {
        dbStub.add(coupon);
    }

    /**
     * remove a coupon with the code from stub
     * @param code a coupon code of the coupon to be removed
     */
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

    /**
     Update a coupon in storage by deleting it from storage and adding
     a new coupon with the same coupon code as the deleted object but updated information.
     If no coupon with the same code as the input exists in storage, a new coupon will be added to storage.
     * @param coupon updated coupon object
     */
    @Override
    public void replaceCoupon(Coupon coupon) {
        this.removeCoupon(coupon.getCode());
        this.addCoupon(coupon);
    }

    /**
     *
     *  Retrieve a coupon from storage with the given code
     *      @param code code of the retrieved coupon
     *      @return coupon with the given code
     */
    @Override
    public Coupon getCoupon(String code) {
        for(Coupon coup: dbStub){
            if(coup.getCode().compareTo(code) == 0){
                return coup;
            }
        }
        return null;
    }

    /**
     * @return a list of coupons currently in storage
     */
    @Override
    public List<Coupon> getCouponList() {
        return dbStub;
    }


    @Override
    public void empty(){
        this.dbStub = new ArrayList<>();
    }
}
