package TA2.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import TA2.Model.Admin;
import TA2.Model.Product;
import TA2.Model.Rental;
import TA2.Model.RentalManagementSystem;

public class AdminController {

    private Admin admin;
    private RentalManagementSystem rentalSystem;
    private Collection<Rental> rentals;

    // Konstruktor untuk menginisialisasi admin dan sistem penyewaan
    public AdminController(Admin admin, RentalManagementSystem rentalSystem) {
        this.admin = admin;
        this.rentalSystem = rentalSystem;
    }

    // Menambahkan produk ke dalam sistem penyewaan
    public void addProduct(Product product) {
        rentalSystem.addProduct(product);  // Menambahkan produk ke rentalSystem
        System.out.println("Produk " + product.getName() + " telah ditambahkan.");
    }

    // Menyetujui penyewaan berdasarkan rental ID
    public boolean approveRental(int rentalID) {
        Rental rental = findRentalById(rentalID);
        if (rental != null) {
            rental.setRentalStatus("Disetujui");
            return true;
        }
        return false;  // Jika penyewaan tidak ditemukan
    }

    // Menolak penyewaan berdasarkan rental ID
    public boolean rejectRental(int rentalID) {
        Rental rental = findRentalById(rentalID);
        if (rental != null) {
            rental.setRentalStatus("Ditolak");
            return true;
        }
        return false;  // Jika penyewaan tidak ditemukan
    }

    // Mencari penyewaan berdasarkan rental ID
    private Rental findRentalById(int rentalID) {
        return rentals.stream()
                .filter(r -> r.getRentalID() == rentalID)
                .findFirst()
                .orElse(null);  // Mengembalikan null jika rental tidak ditemukan
    }

    // Mendapatkan daftar penyewaan yang menunggu persetujuan
    public List<Rental> getPendingRentals() {
        if (this.rentals == null) {
            this.rentals = new ArrayList<>(); // Inisialisasi jika rentals belum ada
        }
        return this.rentals.stream()
                .filter(rental -> rental.getRentalStatus().equals("Menunggu Persetujuan"))
                .collect(Collectors.toList());
    }

    // Menampilkan daftar penyewaan
    public void viewRentals() {
        rentalSystem.viewRentals();
    }

    // Menampilkan daftar produk
    public void viewProducts() {
        rentalSystem.displayProducts();
    }

    // Mengedit informasi produk
    public void editProduct(int productId, String name, String brand, String type, double price, String description, int stock) {
        Product product = rentalSystem.getProduct(productId);
        if (product != null) {
            product.setName(name);
            product.setBrand(brand);
            product.setType(type);
            product.setPrice(price);
            product.setDescription(description);
            product.setStock(stock);
            System.out.println("Produk dengan ID " + productId + " berhasil diperbarui.");
        } else {
            System.out.println("Produk dengan ID " + productId + " tidak ditemukan.");
        }
    }

    // Menghapus produk dari sistem
    public void deleteProduct(int productId) {
        Product product = rentalSystem.getProduct(productId);
        if (product != null) {
            rentalSystem.deleteProduct(productId);
            System.out.println("Produk dengan ID " + productId + " berhasil dihapus.");
        } else {
            System.out.println("Produk dengan ID " + productId + " tidak ditemukan.");
        }
    }

    // Mengambil daftar produk yang ada di sistem
    public List<Product> getProducts() {
        return rentalSystem.getProducts();
    }

    // Memperbarui status produk
    public void updateProductStatus(int productId, String status) {
        Product product = rentalSystem.getProduct(productId);
        if (product != null) {
            product.setStatus(status); // Update status produk
            rentalSystem.updateProduct(product);  // Menyimpan perubahan ke rentalSystem
            System.out.println("Status produk dengan ID " + productId + " berhasil diperbarui.");
        } else {
            System.out.println("Produk dengan ID " + productId + " tidak ditemukan.");
        }
    }

    // Mencari produk berdasarkan ID produk
    public Product getProductById(int productId) {
        for (Product product : rentalSystem.getProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;  // Jika produk tidak ditemukan
    }
}
