package TA2.Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Rental {
    private int rentalID;
    private Customer customer;
    private List<Product> products;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private String shippingMethod;  // Renamed to match the second class
    private String paymentMethod;
    private double totalPrice;
    private String rentalStatus;  // "Active", "Returned", etc.
    private double lateFee;

    // Constructor
    public Rental(Customer customer, List<Product> products, LocalDate rentalDate, LocalDate returnDate, String shippingMethod, String paymentMethod, double totalPrice) {
        this.customer = customer;
        this.products = products;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.rentalStatus = "Active";  // Default rental status
        this.lateFee = 0.0;
    }

    
    // public Rental(int i, Object object, List<Product> cart, String startDate, String endDate, String shippingMethod2,
    //         String paymentMethod2, double totalPrice2) {
    //     //TODO Auto-generated constructor stub
    // }


    public Rental(int i, Object object, List<Product> cart, String startDate, String endDate, String shippingMethod2,
            String paymentMethod2, double totalPrice2) {
        //TODO Auto-generated constructor stub
    }


    // Method to calculate late fee
    public void calculateLateFee(LocalDate actualReturnDate) {
        long lateDays = ChronoUnit.DAYS.between(returnDate, actualReturnDate);
        if (lateDays > 0) {
            this.lateFee = lateDays * 10000;  // Rp 10.000 per day of delay
        } else {
            this.lateFee = 0.0;
        }
    }

    // Getter and Setter Methods
    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public double getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    // Method to display rental status
    public void viewRentalStatus() {
        System.out.println("Rental Status: " + rentalStatus);
        if (lateFee > 0) {
            System.out.println("Late Fee: Rp " + lateFee);
        }
    }

    public LocalDate getStartDate() {
        return rentalDate;
    }
}
