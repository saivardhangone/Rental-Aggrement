package org.rental.tools;

import org.rental.tools.dto.Result;
import org.rental.tools.dto.ToolRentalInformation;
import org.rental.tools.service.RentalAgreementService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ParseException {
        try {
            RentalAgreementService rentalAgreementService = new RentalAgreementService();
            Scanner sc = new Scanner(System.in);
            rentalAgreementService.printToolTable();
            rentalAgreementService.printToolsRentPrices();
            System.out.println("Enter the toolCode=");
            String toolCode = sc.next();
            System.out.println("Enter the rental Day Count=");
            int rentalDaysCount = sc.nextInt();
            System.out.println("Enter the Discount percentage=");
            int discountPercentage = sc.nextInt();
            System.out.println("Enter the check out date in format mm/dd/yy=");
            String date = sc.next();
            SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yy");
            Date checkOutDate = smf.parse(date);
            ToolRentalInformation toolRentalInformation = new ToolRentalInformation();
            toolRentalInformation.setToolCode(toolCode);
            toolRentalInformation.setCheckOutDate(checkOutDate);
            toolRentalInformation.setDiscount(discountPercentage);
            toolRentalInformation.setRentalDays(rentalDaysCount);
            Result result = rentalAgreementService.calculateRentalAmount(toolRentalInformation);
            rentalAgreementService.printRentalAgreementDetails(toolRentalInformation, result);
        }catch (Exception e){
            System.err.println("Application closing due to an error : "+ e.getMessage());
        }
    }
}