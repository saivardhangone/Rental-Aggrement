package org.rental.tools.dto;

import java.util.Date;
import java.util.Objects;


public class ToolRentalInformation {

    private String toolCode;
    private Date checkOutDate;
    private Integer rentalDays;
    private Integer discount;

    public ToolRentalInformation(){

    }
    public ToolRentalInformation(String toolCode, Date checkOutDate, Integer rentalDays, Integer discount) {
        if(toolCode != null && !toolCode.trim().isEmpty() &&
                Objects.nonNull(checkOutDate) && rentalDays != 0 &&
                discount >=0 && discount <= 100) {
            this.toolCode = toolCode;
            this.checkOutDate = checkOutDate;
            this.rentalDays = rentalDays;
            this.discount = discount;
        }else{
            throw new RuntimeException("Given inputs are not valid, please check again............");
        }
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
