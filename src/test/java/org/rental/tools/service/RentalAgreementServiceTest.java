package org.rental.tools.service;

import org.junit.jupiter.api.Test;
import org.rental.tools.dto.Result;
import org.rental.tools.dto.ToolRentalInformation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class RentalAgreementServiceTest {

    @Test
    void calculateRentalAmount() throws Exception {

            ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
            toolRentalInformation.setToolCode("JAKR");
            toolRentalInformation.setRentalDays(4);
            String date = "07/02/20";
            SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
            Date checkOutDate = smf.parse(date);
            toolRentalInformation.setCheckOutDate(checkOutDate);
            toolRentalInformation.setDiscount(10);
            RentalAgreementService rentalAgreementService = new RentalAgreementService();
            Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
            DecimalFormat df = new DecimalFormat("####0.00");
            assertEquals("10.76", df.format(result.getRentalAmount()));
    }
    @Test
    public void whenDiscountNotInRageExceptionThrown_thenAssertionSucceeds() throws ParseException {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("JAKR");
        toolRentalInformation.setRentalDays(4);
        String date = "07/02/20";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(101);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Exception exception = assertThrows(Exception.class, () -> {
            rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        });

        String expectedMessage = "Discount percent is not in the range 0-100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void whenRentalDayLessThanOneDayExceptionThrown_thenAssertionSucceeds() throws ParseException {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("JAKR");
        toolRentalInformation.setRentalDays(0);
        String date = "07/02/20";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(50);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Exception exception = assertThrows(Exception.class, () -> {
            rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        });

        String expectedMessage = "Rental day count is not 1 or greater";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void whenRentalDayAtWeekEndDays_thenAssertionSucceeds() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("LADW");
        toolRentalInformation.setRentalDays(2);
        String date = "06/02/24";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(10);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("3.58",(df.format(result.getRentalAmount())));
    }
    @Test
    public void whenRentalDayIsLabour_thenAssertionSucceeds() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("CHNS");
        toolRentalInformation.setRentalDays(2);
        String date = "07/05/20";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(10);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("1.34",(df.format(result.getRentalAmount())));
    }
    @Test
    public void scenario1() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("JAKR");
        toolRentalInformation.setRentalDays(5);
        String date = "09/03/15";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(101);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Exception exception = assertThrows(Exception.class, () -> {
            rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        });

        String expectedMessage = "Discount percent is not in the range 0-100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void scenario2() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("LADW");
        toolRentalInformation.setRentalDays(3);
        String date = "07/02/20";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(10);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("5.37",(df.format(result.getRentalAmount())));
    }
    @Test
    public void scenario3() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("CHNS");
        toolRentalInformation.setRentalDays(5);
        String date = "07/02/15";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(25);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("4.47",(df.format(result.getRentalAmount())));
    }
    @Test
    public void scenario4() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("JAKD");
        toolRentalInformation.setRentalDays(6);
        String date = "09/03/15";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(0);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("11.96",(df.format(result.getRentalAmount())));

    }
    @Test
    public void scenario5() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("JAKR");
        toolRentalInformation.setRentalDays(9);
        String date = "07/02/15";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(0);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("20.93",(df.format(result.getRentalAmount())));

    }
    @Test
    public void scenario6() throws Exception {
        ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
        toolRentalInformation.setToolCode("JAKR");
        toolRentalInformation.setRentalDays(4);
        String date = "07/02/20";
        SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
        Date checkOutDate = smf.parse(date);
        toolRentalInformation.setCheckOutDate(checkOutDate);
        toolRentalInformation.setDiscount(50);
        RentalAgreementService rentalAgreementService = new RentalAgreementService();
        Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
        DecimalFormat df = new DecimalFormat("####0.00");
        assertEquals("5.98",(df.format(result.getRentalAmount())));

    }
}