package project.logic;

import project.logic.validation.DateValidator;
import project.logic.validation.ModifierValidator;
import project.objects.ErrorMsg;
import project.objects.Modifier;
import project.objects.Result;
import project.persistence.ModifierDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModifierManagerLogic {
    private ModifierDatabase db;

    public ModifierManagerLogic(ModifierDatabase db) {
        this.db = db;
    }

    /**
     * Generates a 2D array of strings that can populate a JTable with information about the current modifiers
     * (discounts, markups)
     * @return the array of table entry data
     */
    public String[][] getModifierTable() {
        return TableEntryGenerator.genModifierTableEntries(db.getModifierList());
    }

    /**
     * Add a new modifier (discount/markup) to the database after validating inputs
     * @param name
     * @param modifier
     * @param dateFrom
     * @param dateTo
     * @return list of errors
     */
    public List<ErrorMsg> addModifier(String name, String modifier, String dateFrom, String dateTo) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        Float oModifier = null;
        Date oDateFrom = null;
        Date oDateTo = null;

        // Validate name (must be unique and non-empty)
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }
        for (Modifier m: db.getModifierList()) {
            if (m.getName().equalsIgnoreCase(name)) {
                errorMsgs.add(new ErrorMsg("There is already a sale/markup for '" + name + "'!"));
                return errorMsgs;
            }
        }

        ModifierValidator modifierValidator = new ModifierValidator();

        if (modifier.equals("")) {
            errorMsgs.add(new ErrorMsg("Sale/markup cannot be empty!"));
            return errorMsgs;
        }
        Result<Float, List<ErrorMsg>> modifierResult = modifierValidator.validate(modifier);
        if (modifierResult.getError() != null) {
            return modifierResult.getError();
        }
        oModifier = modifierResult.getResult();

        // Validate date range
        DateValidator dateValidator = new DateValidator();

        if (dateFrom.equals("")) {
            errorMsgs.add(new ErrorMsg("The start date can't be empty!"));
            return errorMsgs;
        }
        Result<Date, List<ErrorMsg>> dateFromResult = dateValidator.validate(dateFrom);
        if (dateFromResult.getError() != null) {
            return dateFromResult.getError();
        }
        oDateFrom = dateFromResult.getResult();

        if (dateTo.equals("")) {
            errorMsgs.add(new ErrorMsg("The end date can't be empty!"));
            return errorMsgs;
        }
        Result<Date, List<ErrorMsg>> dateToResult = dateValidator.validate(dateTo);
        if (dateToResult.getError() != null) {
            return dateToResult.getError();
        }
        oDateTo = dateToResult.getResult();

        // If we made it this far, we can now add the modifier to the database
        db.addModifier(new Modifier(name, oModifier, oDateFrom, oDateTo));

        return errorMsgs;
    }

    /**
     * Remove modifier from the database after validating inputs
     * @param name
     * @return a list of errors
     */
    public List<ErrorMsg> removeModifier(String name) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Check if modifier is even there
        boolean canRemove = true;
        for (Modifier m: db.getModifierList()) {
            if (m.getName().equalsIgnoreCase(name)) {
                canRemove = false;
                break;
            }
        }

        if (!canRemove) {
            errorMsgs.add(new ErrorMsg("There is no '" + name + "' to remove!`"));
            return errorMsgs;
        }

        // At this point, we know it's safe to remove
        db.removeModifier(name);

        return null;
    }
}
