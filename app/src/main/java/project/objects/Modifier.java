package project.objects;

import java.util.Date;

public class Modifier {
    private String name;
    private Float modifier;
    private Date dateFrom;
    private Date dateTo;

    public Modifier(String name, Float modifier, Date dateFrom, Date dateTo) {
        this.name = name;
        this.modifier = modifier;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getName() {
        return name;
    }

    public Float getModifier() {
        return modifier;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }
}
