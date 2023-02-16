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
    private final String filterType;

    //objects of type Integer, Float, Date or String only. Objects should be of the same type.
    private final Object rangeStart;
    private final Object rangeEnd;

    /**
     * Static factory method that builds and returns a ProductFilter if params are acceptable
     * rangeStart and end must have the same class type;
     * @param filterType type of filter
     * @param rangeStart: start of range included in filter
     * @param rangeEnd: end of range included in filter
     * @return a productFilter ot null if params are not valid to create a filter
     */
    public static FilterProduct FilterProductFactory(String filterType, Object rangeStart, Object rangeEnd){
        if(rangeStart.getClass().getName().compareTo(rangeEnd.getClass().getName()) != 0){
           return null;
        }
        else if(rangeStart.getClass().getName().compareTo("String") != 0
                && rangeStart.getClass().getName().compareTo("Integer") != 0
                && rangeStart.getClass().getName().compareTo("Float") != 0
                && rangeStart.getClass().getName().compareTo("Date") != 0){
            return null;
        }
        else if(rangeEnd.getClass().getName().compareTo("String") != 0
                && rangeEnd.getClass().getName().compareTo("Integer") != 0
                && rangeEnd.getClass().getName().compareTo("Float") != 0
                && rangeEnd.getClass().getName().compareTo("Date") != 0){
            return null;
        }
        else if(filterType.toLowerCase().compareTo("quantity") != 0
                && filterType.toLowerCase().compareTo("name") != 0
                && filterType.toLowerCase().compareTo("barcode") != 0
                && filterType.toLowerCase().compareTo("expirydate") != 0
                && filterType.toLowerCase().compareTo("price") != 0){
            return null;
        }
        else{
            return new FilterProduct(filterType, rangeStart, rangeEnd);
        }

    }

    private FilterProduct(String filterType, Object rangeStart, Object rangeEnd){
        this.filterType = filterType;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public Object getRangeEnd() {
        return rangeEnd;
    }

    public String getFilterType() {
        return filterType;
    }

    public Object getRangeStart() {
        return rangeStart;
    }
}
