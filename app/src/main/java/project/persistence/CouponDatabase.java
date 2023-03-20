package project.persistence;


import project.objects.Coupon;

import java.util.List;

/**
 * CouponDatabase interface for logic classes to interact with. Hides from logic whether
 * it is working with a real database or a stub.
 * Implementers represent persistent or temporary storage with add/update/remove/query operations
 * for coupon objects/entities.
 */
public interface CouponDatabase {

    /**
     * add a coupon to storage
     * @param coupon to be added
     */
    public void addCoupon(Coupon coupon);

    /**
     * remove a coupon with the code from storage
     * @param code a coupon code of the coupon to be removed
     */
    public void removeCoupon(String code);

    /**
     * Update a coupon in storage by deleting it from storage and adding
     * a new coupon with the same code but updated information to storage.
     * If no coupon with the same code as the input exists in storage, a new coupon will be added to storage.
     * @param coupon updated coupon object
     */
    public void replaceCoupon(Coupon coupon); //for update

    /**
     * Retrieve a coupon from storage with the given code
     * @param code code of the retrieved coupon
     * @return coupon with the given code
     */
    public Coupon getCoupon(String code);

    /**
     * @return a list of coupons currently in storage
     */
    public List<Coupon> getCouponList();

    public void empty();
}
