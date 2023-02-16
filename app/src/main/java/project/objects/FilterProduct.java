package project.objects;

/**
 * Class represents a filter over a range of values for products.
 * For example, a filter could be created for all products with quantities on the range
 * rangeStart < quantity < rangeEnd
 * -> Once created a filter is not meant to have its fields altered, as the Filter
 * should only be created or destroyed but not changed.
 */
public class FilterProduct {

    //Values barcode, name, quantity, etc.
    private final String filterTpe;

    //objects of type Integer, Float, Date or String only. Objects should be of the same type.
    private final Object rangeStart;
    private final Object rangeEnd;

    public FilterProduct(String filterType, Object rangeStart, Object rangeEnd){
        this.filterTpe = filterType;
        //add some object type checking for stability
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public Object getRangeEnd() {
        return rangeEnd;
    }

    public String getFilterTpe() {
        return filterTpe;
    }

    public Object getRangeStart() {
        return rangeStart;
    }
}
