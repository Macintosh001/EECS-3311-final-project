package project.logic;

import project.logic.validation.PercentOffValidator;
import project.objects.Coupon;
import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class CouponManagerLogic {

    CouponDatabase db;

    public CouponManagerLogic(CouponDatabase db) {
        this.db = db;
    }
    public String[][] getCouponTable() {
        return TableEntryGenerator.genCouponTableEntries(db.getCouponList());
    }

    public List<ErrorMsg> addCoupon(String code, String percentOff) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Check to see if code is empty
        if (code.equals("")) {
            errorMsgs.add(new ErrorMsg("Code cannot be empty!"));
            return errorMsgs;
        }

        // Check to make sure code doesn't already exist
        boolean hasCode = false;
        for (Coupon c: db.getCouponList()) {
            if (c.getCode().equals(code)) {
                hasCode = true;
                break;
            }
        }
        if (hasCode) {
            errorMsgs.add(new ErrorMsg("The code already exists!"));
            return errorMsgs;
        }

        // Validate percentOff
        PercentOffValidator v = new PercentOffValidator();
        Result<Float, List<ErrorMsg>> percentOffResult = v.validate(percentOff);
        if (percentOffResult.getError() != null) {
            return percentOffResult.getError();
        }
        Float oPercentOff = percentOffResult.getResult();

        // Add new coupon to database
        db.addCoupon(new Coupon(code, oPercentOff));

        return errorMsgs;
    }

    public List<ErrorMsg> removeCoupon(String code) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Check to see if code is empty
        if (code.equals("")) {
            errorMsgs.add(new ErrorMsg("Code cannot be empty!"));
            return errorMsgs;
        }

        // Check to make sure code doesn't already exist
        boolean hasCode = false;
        for (Coupon c: db.getCouponList()) {
            if (c.getCode().equals(code)) {
                hasCode = true;
                break;
            }
        }
        if (!hasCode) {
            errorMsgs.add(new ErrorMsg("This code doesn't exists!"));
            return errorMsgs;
        }

        // Remove the coupon
        db.removeCoupon(code);

        return errorMsgs;
    }

    public List<ErrorMsg> updateCoupon(String code, String percentOff) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Check to see if code is empty
        if (code.equals("")) {
            errorMsgs.add(new ErrorMsg("Code cannot be empty!"));
            return errorMsgs;
        }

        // Check to make sure code doesn't already exist
        boolean hasCode = false;
        for (Coupon c: db.getCouponList()) {
            if (c.getCode().equals(code)) {
                hasCode = true;
                break;
            }
        }
        if (!hasCode) {
            errorMsgs.add(new ErrorMsg("This code doesn't exists!"));
            return errorMsgs;
        }

        // Validate percentOff
        PercentOffValidator v = new PercentOffValidator();
        Result<Float, List<ErrorMsg>> percentOffResult = v.validate(percentOff);
        if (percentOffResult.getError() != null) {
            return percentOffResult.getError();
        }
        Float oPercentOff = percentOffResult.getResult();

        // Update coupon in database
        db.replaceCoupon(new Coupon(code, oPercentOff));

        return errorMsgs;
    }
}
