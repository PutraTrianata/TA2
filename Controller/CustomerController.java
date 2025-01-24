package TA2.Controller;

import TA2.Model.Customer;
import TA2.Model.Product;
import TA2.Model.Rental;
import TA2.Model.RentalManagementSystem;

import java.time.LocalDate;
import java.util.List;

public class CustomerController {
    private Customer customer;
    private RentalManagementSystem rentalSystem;

    // Konstruktor untuk menginisialisasi customer dan sistem penyewaan
    public CustomerController(Customer customer, RentalManagementSystem rentalSystem) {
        this.customer = customer;
        this.rentalSystem = rentalSystem;
    }

    // Menambahkan produk ke dalam keranjang pelanggan
    public boolean addToCart(int productId) {
        Product product = rentalSystem.getProduct(productId);
        if (product != null && product.getStock() > 0) {
            customer.addToCart(product);  // Menambahkan produk ke keranjang
            return true;
        }
        return false;  // Jika produk tidak tersedia atau stok habis
    }

    // Melakukan checkout, menghitung total harga, memperbarui stok, dan membuat transaksi penyewaan
    public void checkout(List<Product> cart, String startDate, int duration, String shippingMethod, String paymentMethod) {
        double totalPrice = calculateTotalPrice(cart);  // Menghitung total harga produk di keranjang

        // Memperbarui stok untuk setiap produk yang ada di keranjang
        updateProductStock(cart);

        // Menghitung tanggal pengembalian berdasarkan tanggal mulai sewa dan durasi
        LocalDate rentalDate = LocalDate.parse(startDate);
        LocalDate returnDate = rentalDate.plusDays(duration);

        // Mencetak rincian produk yang disewa dan total harga yang harus dibayar
        printCheckoutDetails(cart, totalPrice);

        // Membuat transaksi penyewaan dan menyimpannya dalam sistem penyewaan
        Rental rental = new Rental(customer, cart, rentalDate, returnDate, shippingMethod, paymentMethod, totalPrice);
        rentalSystem.addRental(rental);

        System.out.println("Rental telah diproses. Status: Active");
    }

    // Menghitung total harga produk di keranjang
    private double calculateTotalPrice(List<Product> cart) {
        double totalPrice = 0;
        for (Product product : cart) {
            totalPrice += product.getPrice();  // Menjumlahkan harga setiap produk
        }
        return totalPrice;  // Mengembalikan total harga
    }

    // Memperbarui stok produk di sistem setelah checkout
    private void updateProductStock(List<Product> cart) {
        for (Product product : cart) {
            int remainingStock = product.getStock() - 1;  // Mengurangi stok sebanyak 1 per produk
            product.setStock(remainingStock);
            rentalSystem.updateProduct(product);  // Menyimpan perubahan stok ke dalam sistem penyewaan
        }
    }

    // Mencetak informasi checkout, yaitu produk yang disewa dan total harga
    private void printCheckoutDetails(List<Product> cart, double totalPrice) {
        System.out.println("Melakukan checkout untuk item berikut:");
        for (Product product : cart) {
            System.out.println("- " + product.getName() + " (" + product.getBrand() + ")");
        }
        System.out.println("Total Harga: " + totalPrice);  // Menampilkan total harga produk
    }

    // Mengambil daftar produk yang tersedia untuk disewa
    public List<Product> getAvailableProducts() {
        return rentalSystem.getAvailableProducts();  // Mengambil produk yang tersedia dari sistem penyewaan
    }

    // Melihat status penyewaan untuk penyewaan tertentu
    public void viewRentalStatus(Rental rental) {
        System.out.println("Status Penyewaan: " + rental.getRentalStatus());

        // Jika ada biaya keterlambatan, tampilkan jumlahnya
        if (rental.getLateFee() > 0) {
            System.out.println("Biaya Keterlambatan: Rp " + rental.getLateFee());
        }
    }

    // Mengambil produk-produk yang ada di dalam keranjang pelanggan
    public List<Product> getCart() {
        return customer.getCart();  // Mengambil produk yang ada di keranjang pelanggan
    }

    // Mencari penyewaan berdasarkan ID penyewaan
    public Rental getRentalById(int rentalID) {
        return rentalSystem.getRentalList().values().stream()
                .filter(rental -> rental.getRentalID() == rentalID)  // Mencari penyewaan berdasarkan ID
                .findFirst()
                .orElse(null);  // Mengembalikan null jika penyewaan tidak ditemukan
    }

    public int checkout(Rental rental) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkout'");
    }
}
