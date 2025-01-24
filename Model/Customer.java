package TA2.Model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private int customerID;
    private List<Product> cart = new ArrayList<>();
    private List<String> addresses;

    // Constructor
    public Customer(String username, String password, String phoneNumber, List<String> addresses, int customerID) {
        super(username, password, phoneNumber, addresses.get(0)); // Menggunakan alamat pertama sebagai default
        this.customerID = customerID;
        this.addresses = addresses;
    }

    // Getter untuk daftar alamat
    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void login() {
        // Menggunakan getter untuk username agar lebih konsisten
        System.out.println("Customer logged in as: " + getUsername());
    }

    // Menambahkan produk ke keranjang belanja
    public void addToCart(Product product) {
        cart.add(product);
        System.out.println(product.getName() + " has been added to your cart.");
    }

    // Menyelesaikan checkout dan menyewa produk
    public void checkout(Rental rental) {
        // Logika untuk memproses checkout, seperti menentukan tanggal, metode pengiriman, dll.
        System.out.println("Checking out the following items:");

        for (Product product : cart) {
            System.out.println("- " + product.getName() + " (" + product.getBrand() + ")");
        }

        // Misalnya, total harga dihitung berdasarkan produk dalam keranjang
        double totalPrice = 0.0;
        for (Product product : cart) {
            totalPrice += product.getPrice();
        }

        rental.setTotalPrice(totalPrice); // Menyimpan harga total ke objek rental
        rental.setRentalStatus("Aktif");  // Menandai status rental sebagai aktif
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Rental has been processed. Status: " + rental.getRentalStatus());
    }

    // Melihat status penyewaan
    public void viewRentalStatus(Rental rental) {
        // Menampilkan status penyewaan
        System.out.println("Rental ID: " + rental.getRentalID());
        System.out.println("Status: " + rental.getRentalStatus());
        System.out.println("Late Fee: " + rental.getLateFee());
    }

    // Getter untuk customerID
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    // Getter untuk cart
    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }
}
