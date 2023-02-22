package project.objects;

import java.util.ArrayList;

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

    //objects of type Integer, Float, Date or String only. Objects should be of the same type, or one null.
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
       if(!validParams(filterType, rangeStart, rangeEnd)){
            return null;
       } else{
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

    public static boolean validParams(String filterType, Object rangeStart, Object rangeEnd){
        ArrayList<String> possibleTypes = new ArrayList<>();
        possibleTypes.add("expirydate");
        possibleTypes.add("quantity");
        possibleTypes.add("price");
        possibleTypes.add("barcode");

        ArrayList<String> possibleObjectTypes = new ArrayList<>();
        possibleObjectTypes.add("java.lang.float");
        possibleObjectTypes.add("java.lang.integer");
        possibleObjectTypes.add("java.util.date");


        if(!possibleTypes.contains(filterType.toLowerCase())){
            return false;
        }
        else if(rangeStart == null && rangeEnd == null){
            return false;
        }
        else if(rangeStart == null && possibleObjectTypes.contains(rangeEnd.getClass().getName().toLowerCase())){
            return true;
        }
        else if(rangeEnd == null && possibleObjectTypes.contains(rangeStart.getClass().getName().toLowerCase())){
            return true;
        }
        else if(rangeEnd != null && !possibleObjectTypes.contains(rangeEnd.getClass().getName().toLowerCase())){
            return false;
        }
        else if(rangeStart != null && !possibleObjectTypes.contains(rangeStart.getClass().getName().toLowerCase())){
            return false;
        }
        else if(rangeEnd != null && rangeStart != null && rangeEnd.getClass().getName().toLowerCase().compareTo(rangeStart.getClass().getName().toLowerCase()) != 0){
            return false;
        }
        else{
            return true;
        }
    }

}
