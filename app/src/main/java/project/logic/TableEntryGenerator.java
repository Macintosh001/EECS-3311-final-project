package project.logic;

import project.objects.Coupon;
import project.objects.Modifier;
import project.objects.Orderable;
import project.objects.Product;

import java.util.List;

public class TableEntryGenerator {
    /**
     * creates a Stirng[][] that can be used by a JTable
     * @param list a list of product objects
     * @return
     */
    public static String[][] genProductTableEntries(List<Product> list) {
        String[][] tableEntries = new String[list.size()][5];

        int i = 0;
        for (Product product: list) {
            tableEntries[i][0] = product.getBarcode().toString();
            tableEntries[i][1] = product.getName();
            tableEntries[i][2] = product.getQuantity().toString();
            tableEntries[i][3] = String.format("%.2f", product.getPrice());
            tableEntries[i][4] = product.getExpityDate().toString();
            i++;
        }

        return tableEntries;
    }

    /**
     * creates a Stirng[][] that can be used by a JTable
     * @param list a list of coupon objects
     * @return
     */
    public static String[][] genCouponTableEntries(List<Coupon> list) {
        String[][] tableEntries = new String[list.size()][2];

        int i = 0;
        for (Coupon coupon: list) {
            tableEntries[i][0] = coupon.getCode();
            tableEntries[i][1] = String.format("%.0f", coupon.getPercentOff() * 100.0f);
            i++;
        }

        return tableEntries;
    }

    /**
     * creates a Stirng[][] that can be used by a JTable
     * @param list a list of orderable objects
     * @return
     */
    public static String[][] genOrderableTableEntries(List<Orderable> list) {
        String[][] tableEntries = new String[list.size()][3];

        int i = 0;
        for (Orderable orderable: list) {
            tableEntries[i][0] = orderable.getName();
            tableEntries[i][1] = String.format("%.2f", orderable.getPrice());
            tableEntries[i][2] = orderable.getShelfLife().toString() + " Days";
            i++;
        }

        return tableEntries;
    }

    public static String[][] genModifierTableEntries(List<Modifier> list) {
        String[][] tableEntries = new String[list.size()][4];

        int i = 0;
        for (Modifier modifier: list) {
            tableEntries[i][0] = modifier.getName();
            tableEntries[i][1] = String.format("%.0f", modifier.getModifier() * 100.0f);
            tableEntries[i][2] = modifier.getDateFrom().toString();
            tableEntries[i][3] = modifier.getDateTo().toString();
            i++;
        }

        return tableEntries;
    }
}