package project.logic;

import project.objects.Coupon;
import project.objects.ErrorMsg;
import project.objects.Modifier;
import project.objects.Product;
import project.persistence.CouponDatabase;
import project.persistence.ProductDatabase;
import project.persistence.ModifierDatabase;

import java.util.*;

public class SaleLogic {
    private ProductDatabase productDB;
    private final Map<Integer, Integer> cart;
    private CouponDatabase couponDB;
    private Coupon coupon;
    private ModifierDatabase modifierDB;


    public SaleLogic(ProductDatabase productDB, CouponDatabase couponDB, ModifierDatabase modifierDB) {
        this.productDB = productDB;
        this.couponDB = couponDB;
        this.modifierDB = modifierDB;
        this.cart = new HashMap<>();
        this.coupon = null;
    }
    

    private float applyModifiers(Product product) {
        float price = product.getPrice();
        String productName = product.getName();
        java.util.Date currentDate = new java.util.Date();
        for (Modifier modifier : modifierDB.getModifierList()) {
            if (modifier.getName().equals(productName) && currentDate.after(modifier.getDateFrom()) && currentDate.before(modifier.getDateTo())) {
                price = price + price * modifier.getModifier();
            }
        }
        return price;
    }
    
    
    //Scan the barcode, check if it is valid
    public List<ErrorMsg> scan(String barcodeString) {
        List<ErrorMsg> errors = new ArrayList<>();
        int barcode;
        try {
            barcode = Integer.parseInt(barcodeString);
        } catch (NumberFormatException e) {
            errors.add(new ErrorMsg("Invalid barcode format"));
            return errors;
        }
        //Check if there is a product match the barcode, and check the quantity
        Product product = productDB.getProduct(barcode);
        if (product == null) {
            errors.add(new ErrorMsg("Product not found in inventory"));
        } else if (product.getQuantity() < 1) {
            errors.add(new ErrorMsg("Product out of stock"));
        } else {
            if (cart.containsKey(barcode)) {
                cart.put(barcode, cart.get(barcode) + 1);
            } else {
                cart.put(barcode, 1);
            }
        }
        return errors;
    }

    //Create the cart table
    public String[][] getCartTable() {
        String[][] cartTable = new String[cart.size()][4];
        Set<Integer> barcodeSet = cart.keySet();
        int i = 0;
        for (Integer barcode: barcodeSet) {
            Product product = productDB.getProduct(barcode);
            cartTable[i][0] = barcode.toString();
            cartTable[i][1] = product.getName();
            cartTable[i][2] = cart.get(barcode).toString();
            float modifiedPrice = applyModifiers(product);
            cartTable[i][3] = String.format("%.2f", modifiedPrice);
            i ++;
        }
        return cartTable;
    }

    //remove all products in the cart
    public void clearShoppingCart() {
        this.coupon = null; // Reset the coupon usage after a successful purchase
        cart.clear();
    }

    //Apply the coupon, check if the same coupon already allpied, and check if the coupon is valid
    public List<ErrorMsg> applyCoupon(String code) {
        List<ErrorMsg> errors = new ArrayList<>();
        Coupon tmpCoupon = couponDB.getCoupon(code);
        if (this.coupon != null) {
            errors.add(new ErrorMsg("Coupon already applied"));
        } else if (tmpCoupon == null) {
            errors.add(new ErrorMsg("Invalid coupon code"));
        } else {
            this.coupon = tmpCoupon;
        }
        return errors;
    }

    //Count the total price after the coupon applied
    public String getTotal() {
        double subtotal = 0;
        String[][] cartTable = getCartTable();
        for (int i = 0; i < cartTable.length; i ++) {
        	float modifiedPrice = applyModifiers(productDB.getProduct(Integer.parseInt(cartTable[i][0])));
        	subtotal = modifiedPrice * Integer.parseInt(cartTable[i][2]) + subtotal; // subtotal += product.getPrice() * product.getQuantity();
        }
        if (this.coupon != null) {
            subtotal *= (1 - this.coupon.getPercentOff());
        }
        return String.format("%.2f", subtotal);
    }



    //Check if the stock has enough quantity of the product to sell
    public List<ErrorMsg> buy() {
        List<ErrorMsg> errors = new ArrayList<>();
        Set<Integer> barcodeSet = cart.keySet();
        for (Integer barcode: barcodeSet) {
            Product product = productDB.getProduct(barcode);
            if (product.getQuantity() < cart.get(barcode)){
                errors.add(new ErrorMsg("Product out of stock"));
            }
        }

        //Update the quantity of the product in the stock 
        if (errors.isEmpty()) {
            for (Integer barcode: barcodeSet) {
                Product product = productDB.getProduct(barcode);
                Product newProduct = new Product(product.getBarcode(), product.getName(), product.getQuantity() - cart.get(barcode), product.getPrice(), product.getExpityDate());
                productDB.replaceProduct(newProduct);
            }
            this.clearShoppingCart();
        }
        return errors;
    }
    
}
