package project.persistence;

import project.objects.FilterProduct;
import project.objects.Product;
import project.objects.ProductList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An implementation of a database communication class, used for testing only.
 * That is, it uses an arrayList to simulate db functionality without actually communicating with a db.
 * Uses fixed starting content so that behavior/outputs are predictable during testing.
 * This is not persistent storage; when a program ends, data is lost.
 */
public class DatabaseStub implements Database {
    private ArrayList<Product> dbStub;

    public DatabaseStub(){
        this.dbStub = this.getTestContent();
    }

    /**
     * Create fixed starting data to initialize the stub.
     * Used to create predictable behavior/content for use in testing
     * @return: an arrayList of content for initialization of the stub
     */
    private ArrayList<Product> getTestContent(){
        ArrayList<Product> ret = new ArrayList<Product>();
        Date expOreos = new Date();
        Date expCheetos = new Date();
        Product oreos = new Product(0, "oreos", 75, 3.99F, expOreos);
        Product cheetos = new Product(1, "cheetos", 44, 2.99F, expCheetos);
        ret.add(oreos);
        ret.add(cheetos);
        return ret;
    }


    /**
     * Gets a product from the stub with a given barcode
     * @param barcode: the barcode of a product
     * @return a product with the barcode.
     */
    @Override
    public Product getProduct(Integer barcode) {
        for(Product p: dbStub){
            if(p.getBarcode().intValue() == barcode.intValue()){
                return p;
            }
        }
        return null;
    }

    /**
     *
     * @return a productList representing the complete set of objects of a stub
     */
    @Override
    public ProductList getProductList() {
        ProductList ret = new ProductList(dbStub);
        return ret;
    }


    /**
     * Add a product to stub
     * @param product : the product added
     */
    @Override
    public void addProduct(Product product) {
        dbStub.add(product);
    }

    /**
     * Search for and remove a product from the stub
     * @param barcode: the barcode of the removed product
     */
    @Override
    public void removeProduct(Integer barcode) {
        Product product = null;
        for(Product p: dbStub){
            if(p.getBarcode().intValue() == barcode.intValue()){
                product = p;
                break;
            }
        }
        if(product != null) {
            dbStub.remove(product);
        }
    }

    /**
     * Replace a product in the stub with an updated version of itself.
     * @param product: a product
     */
    @Override
    public void replaceProduct(Product product) {
        removeProduct(product.getBarcode());
        addProduct(product);
    }

    @Override
    public ProductList getFilteredProductList(List<FilterProduct> filters){
        ArrayList<Product> leftOver = (ArrayList<Product>) this.dbStub.clone();

        for(FilterProduct filt: filters){
            if(filt.getFilterType().toLowerCase().compareTo("quantity") == 0){
                leftOver = quantityFilter(leftOver, (Integer) filt.getRangeStart(), (Integer) filt.getRangeEnd());
            }
            else if(filt.getFilterType().toLowerCase().compareTo("price") == 0){
                leftOver = priceFilter(leftOver, (Float) filt.getRangeStart(), (Float) filt.getRangeEnd());
            }
            else if(filt.getFilterType().toLowerCase().compareTo("barcode") == 0){
                leftOver = barcodeFilter(leftOver, (Integer) filt.getRangeStart(), (Integer) filt.getRangeEnd());
            }
            else if(filt.getFilterType().toLowerCase().compareTo("expirydate") == 0){
                leftOver = dateFilter(leftOver, (java.util.Date) filt.getRangeStart(), (java.util.Date) filt.getRangeEnd());
            }
        }

        return new ProductList(leftOver);
    }

    private ArrayList<Product> quantityFilter(ArrayList<Product> input, Integer start, Integer end){
        ArrayList<Product> leftOver = (ArrayList<Product>) input.clone();
        if(start  == null && end != null){
            for(Product p: input){
                if(p.getQuantity() > end){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end == null){
            for(Product p: input){
                if(p.getQuantity() < start){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end != null){
            for(Product p: input){
                if(p.getQuantity() > end || p.getQuantity() < start){
                    leftOver.remove(p);
                }
            }
        }
        return leftOver;
    }

    private ArrayList<Product> barcodeFilter(ArrayList<Product> input, Integer start, Integer end){
        ArrayList<Product> leftOver = (ArrayList<Product>) input.clone();
        if(start  == null && end != null){
            for(Product p: input){
                if(p.getBarcode() > end){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end == null){
            for(Product p: input){
                if(p.getBarcode() < start){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end != null){
            for(Product p: input){
                if(p.getBarcode() > end || p.getBarcode() < start){
                    leftOver.remove(p);
                }
            }
        }

        return leftOver;
    }

    private ArrayList<Product> priceFilter(ArrayList<Product> input, Float start, Float end){
        ArrayList<Product> leftOver = (ArrayList<Product>) input.clone();
        if(start  == null && end != null){
            for(Product p: input){
                if(p.getPrice() > end){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end == null){
            for(Product p: input){
                if(p.getPrice() < start){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end != null){
            for(Product p: input){
                if(p.getPrice() > end || p.getPrice() < start){
                    leftOver.remove(p);
                }
            }
        }
        return leftOver;
    }

    private ArrayList<Product> dateFilter(ArrayList<Product> input, java.util.Date start, java.util.Date end){
        ArrayList<Product> leftOver = (ArrayList<Product>) input.clone();
        if(start  == null && end != null){
            for(Product p: input){
                if(p.getExpityDate().after(end)){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end == null){
            for(Product p: input){
                if(p.getExpityDate().before(start)){
                    leftOver.remove(p);
                }
            }
        }
        else if(start != null && end != null){
            for(Product p: input){
                if(p.getExpityDate().after(end) || p.getExpityDate().before(start)){
                    leftOver.remove(p);
                }
            }
        }
        return leftOver;
    }

}
