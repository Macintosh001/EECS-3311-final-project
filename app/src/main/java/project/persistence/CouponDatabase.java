package project.persistence;


import project.objects.Coupon;

import java.util.List;

public interface CouponDatabase {


    public void addCoupon(Coupon coupon);
    public void removeCoupon(String code);
    public void replaceCoupon(Coupon coupon); //for update
    public Coupon getCoupon(String code);
    public List<Coupon> getCouponList();


}
