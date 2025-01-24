package TA2.View;

import TA2.Controller.CustomerController;
import TA2.Model.Product;
import TA2.Model.Rental;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerView {
    private CustomerController customerController;
    private Scanner scanner = new Scanner(System.in);

    public CustomerView(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Lihat & Filter Produk");
            System.out.println("2. Masukkan Produk ke Keranjang");
            System.out.println("3. Checkout");
            System.out.println("4. Lihat Status Penyewaan");
            System.out.println("5. Logout");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    showAvailableProducts();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    checkout();
                    break;
                case 4:
                    viewRentalStatus();
                    break;
                case 5:
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }    

    private void showAvailableProducts() {
        List<Product> availableProducts = customerController.getAvailableProducts().stream()
                .filter(p -> p.getStatus() != null && p.getStatus().equalsIgnoreCase("Tersedia") && p.getStock() > 0)
                .collect(Collectors.toList());
        
        if (availableProducts.isEmpty()) {
            System.out.println("Tidak ada produk yang tersedia.");
            return;
        }
        
        System.out.println("\n=== Daftar Produk Tersedia ===");
        for (Product product : availableProducts) {
            System.out.println("ID: " + product.getId());
            System.out.println("Nama: " + product.getName());
            System.out.println("Merek: " + product.getBrand());
            System.out.println("Jenis: " + product.getType());
            System.out.println("Harga: " + product.getPrice());
            System.out.println("Deskripsi: " + product.getDescription());
            System.out.println("Stok: " + product.getStock());
            System.out.println("Status: " + product.getStatus());
            System.out.println("------------------------------");
        }
    
        System.out.print("Apakah ingin memfilter produk? (ya/tidak): ");
        String filterChoice = scanner.nextLine().trim().toLowerCase();
        if (filterChoice.equals("ya")) {
            filterProducts(availableProducts);
        } else {
            System.out.println("Kembali ke menu utama.");
        }
    }

    private void filterProducts(List<Product> products) {
        System.out.println("Filter berdasarkan:");
        System.out.println("1. Jenis");
        System.out.println("2. Merek");
        System.out.print("Pilih opsi: ");
        int filterOption = scanner.nextInt();
        scanner.nextLine();

        List<Product> filteredProducts;
        if (filterOption == 1) {
            System.out.print("Masukkan jenis produk: ");
            String type = scanner.nextLine();
            filteredProducts = products.stream()
                    .filter(p -> p.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        } else if (filterOption == 2) {
            System.out.print("Masukkan merek produk: ");
            String brand = scanner.nextLine();
            filteredProducts = products.stream()
                    .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Pilihan tidak valid. Menampilkan semua produk.");
            filteredProducts = products;
        }

        if (filteredProducts.isEmpty()) {
            System.out.println("Produk tidak ditemukan dengan filter tersebut.");
            return;
        }

        System.out.println("\n=== Produk yang Sesuai ===");
        for (Product product : filteredProducts) {
            System.out.println("ID: " + product.getId() + " | Nama: " + product.getName() +
                    " | Merek: " + product.getBrand() + " | Jenis: " + product.getType() +
                    " | Harga: " + product.getPrice() + " | Stok: " + product.getStock() +
                    " | Status: " + product.getStatus());
        }
    }

    private void addToCart() {
        System.out.print("\nMasukkan ID produk yang ingin ditambahkan ke keranjang: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        boolean success = customerController.addToCart(productId);
        if (success) {
            System.out.println("Produk dengan ID " + productId + " berhasil ditambahkan ke keranjang.");
        } else {
            System.out.println("Produk dengan ID " + productId + " tidak ditemukan atau stok habis.");
        }
    }

    private void checkout() {
        List<Product> cart = customerController.getCart();
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }
    
        System.out.print("Masukkan tanggal mulai sewa (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Masukkan durasi sewa (hari): ");
        int duration = scanner.nextInt();
        scanner.nextLine();
    
        String endDate = calculateEndDate(startDate, duration);
    
        System.out.println("Pilih metode pengiriman:");
        System.out.println("1. Kurir");
        System.out.println("2. Ambil di toko");
        System.out.print("Pilih opsi: ");
        int shippingChoice = scanner.nextInt();
        scanner.nextLine();
        String shippingMethod = (shippingChoice == 1) ? "Kurir" : "Ambil di toko";
    
        System.out.println("Pilih metode pembayaran:");
        System.out.println("1. Transfer Bank");
        System.out.println("2. Kartu Kredit");
        System.out.println("3. COD");
        System.out.print("Pilih opsi: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();
        String paymentMethod = switch (paymentChoice) {
            case 1 -> "Transfer Bank";
            case 2 -> "Kartu Kredit";
            case 3 -> "COD";
            default -> "Transfer Bank";
        };
    
        double totalPrice = cart.stream().mapToDouble(Product::getPrice).sum() * duration;
    
        Rental rental = new Rental(0, null, cart, startDate, endDate, shippingMethod, paymentMethod, totalPrice);
        int rentalID = customerController.checkout(rental);
    
        System.out.println("\nCheckout berhasil!");
        System.out.println("Rental ID: " + rentalID);
        System.out.println("Tanggal Sewa: " + startDate);
        System.out.println("Tanggal Pengembalian: " + endDate);
        System.out.println("Metode Pengiriman: " + shippingMethod);
        System.out.println("Metode Pembayaran: " + paymentMethod);
        System.out.println("Total Harga: Rp " + totalPrice);
    
        rental.setRentalStatus("Menunggu Persetujuan");
    }

    private String calculateEndDate(String startDate, int duration) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = start.plusDays(duration);
        return end.toString();
    }

    public void viewRentalStatus() {
        // Assuming customer has a method to get the current rental (for example, latest rental or by rental ID)
        Rental rental = customerController.getRentalById(1); // Replace '1' with the appropriate rental ID
        if (rental != null) {
            System.out.println("Rental Status: " + rental.getRentalStatus());
            if (rental.getLateFee() > 0) {
                System.out.println("Late Fee: Rp " + rental.getLateFee());
            }
        } else {
            System.out.println("Rental tidak ditemukan.");
        }
    }
}
