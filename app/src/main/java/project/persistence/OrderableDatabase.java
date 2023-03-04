package project.persistence;

import project.objects.Coupon;
import project.objects.Orderable;

import java.util.List;

public interface OrderableDatabase {
    List<Orderable> getOrderableList();

    void addOrderable(Orderable coupon);

    void removeOrderable(String code);

    void replaceOrderable(Orderable coupon);
}
