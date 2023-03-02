package project.persistence;

import project.objects.Coupon;

import java.util.List;

public interface CouponDatabase {
    List<Coupon> getCouponList();

    void addCoupon(Coupon coupon);

    void removeCoupon(String code);

    void replaceCoupon(Coupon coupon);
}
