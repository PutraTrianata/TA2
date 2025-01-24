package TA2.Model;

public class Admin extends User {
    private int adminID;
    private RentalManagementSystem rentalSystem; // Menambahkan rentalSystem untuk manajemen produk dan penyewaan

    // Constructor
    public Admin(String username, String password, String phoneNumber, String address, int adminID, RentalManagementSystem rentalSystem) {
        super(username, password, phoneNumber, address);
        this.adminID = adminID;
        this.rentalSystem = rentalSystem; // Inisialisasi rentalSystem
    }

    @Override
    public void login() {
        System.out.println("Admin logged in as: " + getUsername()); // Menggunakan getter untuk username
    }

    // Method untuk menambahkan produk ke dalam sistem
    public void addProduct(Product product) {
        rentalSystem.addProduct(product); // Menambahkan produk menggunakan rentalSystem
        System.out.println("Product " + product.getName() + " has been added to the catalog.");
    }

    // Method untuk menyetujui penyewaan
    public void approveRental(Rental rental) {
        rental.setRentalStatus("Diantar"); // Mengubah status penyewaan menjadi "Diantar"
        rentalSystem.addRental(rental); // Menambahkan penyewaan ke dalam sistem
        System.out.println("Rental with ID " + rental.getRentalID() + " has been approved.");
    }

    // Method untuk melihat rincian penyewaan
    public void viewRentalDetails(Rental rental) {
        // Menampilkan rincian penyewaan
        System.out.println("Rental ID: " + rental.getRentalID());
        System.out.println("Customer: " + rental.getCustomer().getUsername()); // Menggunakan getter untuk username customer
        System.out.println("Rental Date: " + rental.getRentalDate());
        System.out.println("Return Date: " + rental.getReturnDate());
        System.out.println("Delivery Method: " + rental.getShippingMethod());
        System.out.println("Payment Method: " + rental.getPaymentMethod());
        System.out.println("Total Price: " + rental.getTotalPrice());
        System.out.println("Rental Status: " + rental.getRentalStatus());
        System.out.println("Late Fee: " + rental.getLateFee());
        
        // Menampilkan daftar produk yang disewa
        System.out.println("Products Rented:");
        for (Product product : rental.getProducts()) {
            System.out.println("- " + product.getName() + " (" + product.getBrand() + ")");
        }
    }

    // Getter dan Setter untuk adminID
    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    // Getter dan Setter untuk rentalSystem
    public RentalManagementSystem getRentalSystem() {
        return rentalSystem;
    }

    public void setRentalSystem(RentalManagementSystem rentalSystem) {
        this.rentalSystem = rentalSystem;
    }
}
