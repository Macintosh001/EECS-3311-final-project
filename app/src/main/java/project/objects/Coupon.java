package project.objects;

public class Coupon {
    private final String code;
    private final Float percentOff;

    public Coupon(String code, Float percentOff) {
        this.code = code;
        this.percentOff = percentOff;
    }

    public String getCode() {
        return code;
    }

    public Float getPercentOff() {
        return percentOff;
    }
}
