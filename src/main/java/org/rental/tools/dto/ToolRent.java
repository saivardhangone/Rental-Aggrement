package org.rental.tools.dto;



public class ToolRent {
    private  String toolName;
    private Double rent;
    private Boolean isWeekDaysChargeable;
    private Boolean isWeekendsChargeable;
    private  Boolean isHolidaysChargeable;

    private ToolRent(String toolName, Double rent, Boolean isWeekDaysChargeable, Boolean isWeekendsChargeable, Boolean isHolidaysChargeable) {
        this.toolName = toolName;
        this.rent = rent;
        this.isWeekDaysChargeable = isWeekDaysChargeable;
        this.isWeekendsChargeable = isWeekendsChargeable;
        this.isHolidaysChargeable = isHolidaysChargeable;
    }
    public static ToolRent getPriceListObject(String priceString) {
        String[] pricesTokens = priceString.split("#");
        if(pricesTokens.length == 5 &&
                (pricesTokens[0] != null && !pricesTokens[0].trim().isEmpty()) &&
                (pricesTokens[1] != null && !pricesTokens[1].trim().isEmpty()) &&
                (pricesTokens[2] != null && !pricesTokens[2].trim().isEmpty()) &&
                (pricesTokens[3] != null && !pricesTokens[3].trim().isEmpty()) &&
                (pricesTokens[4] != null && !pricesTokens[4].trim().isEmpty())) {
            return new ToolRent(pricesTokens[0],Double.valueOf(pricesTokens[1]),Boolean.valueOf(pricesTokens[2]), Boolean.valueOf(pricesTokens[3]), Boolean.valueOf(pricesTokens[4]));
        } else{
            throw new RuntimeException("Required fields are missing to create Tool Pricing  object. price String is : "+ priceString);
        }
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Boolean getWeekDaysChargeable() {
        return isWeekDaysChargeable;
    }

    public void setWeekDaysChargeable(Boolean weekDaysChargeable) {
        isWeekDaysChargeable = weekDaysChargeable;
    }

    public Boolean getWeekendsChargeable() {
        return isWeekendsChargeable;
    }

    public void setWeekendsChargeable(Boolean weekendsChargeable) {
        isWeekendsChargeable = weekendsChargeable;
    }

    public Boolean getHolidaysChargeable() {
        return isHolidaysChargeable;
    }

    public void setHolidaysChargeable(Boolean holidaysChargeable) {
        isHolidaysChargeable = holidaysChargeable;
    }

    @Override
    public String toString() {
        return "ToolRent{" +
                "toolName='" + toolName + '\'' +
                ", rent=" + rent +
                ", isWeekDaysChargeable=" + isWeekDaysChargeable +
                ", isWeekendsChargeable=" + isWeekendsChargeable +
                ", isHolidaysChargeable=" + isHolidaysChargeable +
                '}';
    }
}
