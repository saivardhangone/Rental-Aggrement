package org.rental.tools.dto;


public class Result {

    private double rentalAmount;
    private String toolCode;
    private String toolType;
    private double discountAmount;
    private int chargeableDays;

    @Override
    public String toString() {
        return "Result{" +
                "rentalAmount=" + rentalAmount +
                ", toolCode='" + toolCode + '\'' +
                ", toolType='" + toolType + '\'' +
                ", discountAmount=" + discountAmount +
                ", chargeableDays=" + chargeableDays +
                '}';
    }

    public double getRentalAmount() {
        return rentalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getChargeableDays() {
        return chargeableDays;
    }

    public void setChargeableDays(int chargeableDays) {
        this.chargeableDays = chargeableDays;
    }

    public void setRentalAmount(double rentalAmount) {
        this.rentalAmount = rentalAmount;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
}
