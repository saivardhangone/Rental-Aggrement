package org.rental.tools.service;

import org.rental.tools.dto.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class RentalAgreementService {

    final private static String PROPERTIES_FILE_PATH="application.properties";


    Map<String, Tool> toolsMap =new HashMap<>();
    Map<String, ToolRent> toolsPriceMap =new HashMap<>();

    public void loadToolsMap(List<String> toolsList) {
        for (String toolString : toolsList) {
            try {
                Tool tool = Tool.getToolObject(toolString);
                toolsMap.put(tool.getToolCode(), tool);
//                System.out.println("toolsMap : " + toolsMap);
            } catch (Exception e) {
                System.err.println("Exception occurred during Toll object creation for string: "+ toolString+"\n exception is : "+ e.getMessage());
            }
        }
    }

    public void loadToolsPriceMap(List<String> rentalList) {
        for (String rentalString : rentalList) {
            try {
                ToolRent toolRent = ToolRent.getPriceListObject(rentalString);
                toolsPriceMap.put(toolRent.getToolName(), toolRent);
//                System.out.println("toolsPriceMap is : "+ toolsPriceMap);
            } catch (Exception e) {
                System.err.println("Exception encountered during tool rent object and exception is : "+ e.getMessage());
            }
        }
    }
    private boolean isWeekend(LocalDate localDate){
        return DayOfWeek.SATURDAY.equals(localDate.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(localDate.getDayOfWeek());
    }
    private boolean isWeekday(LocalDate localDate){
        return !isWeekend(localDate);
    }
    private boolean isIndependenceDay(LocalDate localDate){
        return Month.JULY.equals(localDate.getMonth()) && localDate.getDayOfMonth() == 4;
    }
    private boolean isLaborDay(LocalDate localDate){
        return Month.SEPTEMBER.equals(localDate.getMonth()) && localDate.getDayOfMonth()<=7 && DayOfWeek.MONDAY.equals(localDate.getDayOfWeek());
    }
    private boolean isHoliday(LocalDate localDate){
        return isLaborDay(localDate) || isIndependenceDay(localDate);
    }
    private double calculateRentalAmount(int chargeableDays, double dailyCharge){
       return chargeableDays * dailyCharge;
    }

    private int calculateChargeableDays(int totalRentalDays, LocalDate checkoutDate, ToolRent toolRentDetails){
        int chargeableDays = 0;
        for(int i=0; i<totalRentalDays; i++){
            LocalDate dateForPayableCheck = checkoutDate.minusDays(i);
            DayOfWeek dayOfWeek = dateForPayableCheck.getDayOfWeek();
            boolean isIndependenceDay = isIndependenceDay(dateForPayableCheck);
            boolean isWeekend = isWeekend(dateForPayableCheck);
            boolean isLaborDay = isLaborDay(dateForPayableCheck);
//            System.out.println(dateForPayableCheck+"'s day is : "+ dayOfWeek);
//            System.out.println(dateForPayableCheck+" isIndependenceDay: "+ isIndependenceDay);
//            System.out.println(dateForPayableCheck+" isLaborDay: "+ isLaborDay);
//            System.out.println(dateForPayableCheck+" isWeekend: "+ isWeekend);

            if(isIndependenceDay){
                if(!toolRentDetails.getHolidaysChargeable()){
                    if(isWeekend(dateForPayableCheck)){
                        if(DayOfWeek.SATURDAY.equals(dateForPayableCheck.getDayOfWeek()) && i!=0){
                            if(chargeableDays > 0) {
                                chargeableDays--;
                            }
                        }else if(DayOfWeek.SUNDAY.equals(dateForPayableCheck.getDayOfWeek()) && i!=totalRentalDays-1){
                            if(chargeableDays > 0) {
                                chargeableDays--;
                            }
                        }
                    }
                    continue;
                }else{
                    chargeableDays++;
                }
            }
            if( isWeekend && !toolRentDetails.getWeekendsChargeable()){
                continue;
            }
            if(isLaborDay && !toolRentDetails.getHolidaysChargeable()){
                continue;
            }
            if(toolRentDetails.getWeekDaysChargeable()) {
                chargeableDays++;
            }
        }
        return chargeableDays;
    }
    public Result calculateRentalAmount(ToolRentalInformation toolRentalInformation) throws Exception {
        Result result=new Result();
        validateRequest(toolRentalInformation);
        if(toolsMap.containsKey(toolRentalInformation.getToolCode())){
            Tool toolDetails= toolsMap.get(toolRentalInformation.getToolCode());
            if(toolsPriceMap.containsKey(toolDetails.getToolType())){
                ToolRent toolRentDetails= toolsPriceMap.get(toolDetails.getToolType());
                Date checkOutDate = toolRentalInformation.getCheckOutDate();
                LocalDate checkoutDateLocalDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int chargeableDays = calculateChargeableDays(toolRentalInformation.getRentalDays(), checkoutDateLocalDate, toolRentDetails);
                double totalRentAmount = calculateRentalAmount(chargeableDays, toolRentDetails.getRent());
                double discountAmount = totalRentAmount * toolRentalInformation.getDiscount()/100;
                double finalAmount = totalRentAmount - discountAmount;
                result.setToolType( toolDetails.getToolType());
                result.setToolCode(toolDetails.getToolCode());
                result.setRentalAmount(finalAmount);
                result.setChargeableDays(chargeableDays);
                result.setDiscountAmount(discountAmount);
            }
        }
        return result;
    }


    public RentalAgreementService(){
        loadApplicationData();
    }
    private void loadApplicationData() {
        Properties properties = new Properties();
        try (InputStream inputStream = RentalAgreementService.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
            properties.load(inputStream);
            String toolsPropertyString = properties.getProperty("tools");
            String priceListPropertyString = properties.getProperty("priceList");
//            System.out.println("toolsPropertyString : "+ toolsPropertyString);
//            System.out.println("priceListPropertyString : "+ priceListPropertyString);
            if(toolsPropertyString!=null && priceListPropertyString!= null){
                List<String> toolsStringsList = Arrays.stream(toolsPropertyString.split(",")).collect(Collectors.toList());
                this.loadToolsMap(toolsStringsList);
                List<String> toolPriceListStringsList = Arrays.stream(priceListPropertyString.split(",")).collect(Collectors.toList());
                this.loadToolsPriceMap(toolPriceListStringsList);
            }
        } catch (IOException e) {
            System.err.println("Exception occurred during load properties and exception is : " + e);
        }
    }

    public void validateRequest(ToolRentalInformation toolRentalInformation) throws Exception {
        if(toolRentalInformation.getRentalDays() == 0){
            throw new Exception("Rental day count is not 1 or greater");
        }
        if(toolRentalInformation.getDiscount() < 0 || toolRentalInformation.getDiscount() > 100){
            throw new Exception("Discount percent is not in the range 0-100");
        }
    }
    public void printRentalAgreementDetails(ToolRentalInformation toolRentalInformation, Result result){
        Tool tool = toolsMap.get(toolRentalInformation.getToolCode());
        ToolRent toolRent = toolsPriceMap.get(tool.getToolType());

        System.out.println("Tool Code = "+tool.getToolCode());
        System.out.println("Tool Type = "+tool.getToolType());
        System.out.println("Tool Brand = "+tool.getBrand());
        System.out.println("Rental Days = "+toolRentalInformation.getRentalDays());
        System.out.println("Check Out Date = "+toolRentalInformation.getCheckOutDate());
        LocalDate checkoutDateLocalDate = toolRentalInformation.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("Due Date = "+checkoutDateLocalDate.minusDays(toolRentalInformation.getRentalDays()));
        System.out.println("Daily Rent Charge= " + toolRent.getRent());
        System.out.println("Charge Days= "+result.getChargeableDays());
        System.out.println("Pre-Discount Charge = 0");
        System.out.println("Discount Percent ="+toolRentalInformation.getDiscount()+"%");
        System.out.println("Discount Amount = "+result.getDiscountAmount());
        System.out.println("Final Charge =$"+result.getRentalAmount());



    }


    public void printToolTable() {
        StringBuffer sb = new StringBuffer("\n\n");
        sb.append(String.format("%-20s", "Tool Code"));
        sb.append(String.format("%-20s", "Tool Type"));
        sb.append(String.format("%-20s\n", "Brand"));
        for(Map.Entry<String, Tool> entry : toolsMap.entrySet()){
            Tool tool = entry.getValue();
            sb.append(String.format("%-25s", tool.getToolCode()));
            sb.append(String.format("%-20s", tool.getToolType()));
            sb.append(String.format("%-20s\n", tool.getBrand()));
        }
        System.out.println(sb);
    }

    public void printToolsRentPrices() {
        StringBuffer sb = new StringBuffer("\n\n");
        sb.append(String.format("%-20s", ""));
        sb.append(String.format("%-20s", "Daily charge"));
        sb.append(String.format("%-20s", "Weekday charge"));
        sb.append(String.format("%-20s", "Weekend charge"));
        sb.append(String.format("%-20s\n", "Holiday charge"));
        for(Map.Entry<String, ToolRent> entry : toolsPriceMap.entrySet()){
            ToolRent toolRent = entry.getValue();
            sb.append(String.format("%-20s", toolRent.getToolName()));
            sb.append(String.format("%-20s", toolRent.getRent()));
            sb.append(String.format("%-20s", toolRent.getWeekDaysChargeable()));
            sb.append(String.format("%-20s",toolRent.getWeekendsChargeable()));
            sb.append(String.format("%-20s\n", toolRent.getHolidaysChargeable()));
        }
        System.out.println(sb);
    }
}






