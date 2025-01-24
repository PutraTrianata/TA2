package TA2.View;

import TA2.Controller.AdminController;
import TA2.Model.Product;
import TA2.Model.Rental;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    private AdminController adminController;
    private Scanner scanner = new Scanner(System.in);

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Lihat Daftar Produk");
            System.out.println("3. Edit Produk");
            System.out.println("4. Hapus Produk");
            System.out.println("5. Kelola Status Produk");
            System.out.println("6. Lihat Penyewaan yang Menunggu Persetujuan");
            System.out.println("7. Setujui Penyewaan");
            System.out.println("8. Tolak Penyewaan");
            System.out.println("9. Logout");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    adminController.viewProducts();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    manageProductStatus();
                    break;
                case 6:
                    processRentalRequests();
                    break;
                case 7:
                    approveRental();
                    break;
                case 8:
                    rejectRental();
                    break;
                case 9:
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Nama Produk: ");
        String name = scanner.nextLine();
        System.out.print("Merek: ");
        String brand = scanner.nextLine();
        System.out.print("Jenis: ");
        String type = scanner.nextLine();
        System.out.print("Harga: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Deskripsi: ");
        String description = scanner.nextLine();
        System.out.print("Stok: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
    
        // Pastikan objek produk dibuat dengan benar
        Product product = new Product(0, name, brand, type, price, description, stock, "Tersedia");
        adminController.addProduct(product);
    }
    

    private void editProduct() {
        System.out.print("Masukkan ID Produk yang ingin diedit: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Membuang newline

        System.out.print("Nama Baru: ");
        String name = scanner.nextLine();
        System.out.print("Merek Baru: ");
        String brand = scanner.nextLine();
        System.out.print("Jenis Baru: ");
        String type = scanner.nextLine();
        System.out.print("Harga Baru: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Deskripsi Baru: ");
        String description = scanner.nextLine();
        System.out.print("Stok Baru: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        adminController.editProduct(productId, name, brand, type, price, description, stock);
    }

    private void deleteProduct() {
        System.out.print("Masukkan ID Produk yang ingin dihapus: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        adminController.deleteProduct(productId);
    }


    // Misalnya dalam AdminView atau Controller
    private void manageProductStatus() {
        System.out.print("Masukkan ID Produk yang ingin dikelola statusnya: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // untuk membersihkan buffer input
    
        // Gunakan adminController untuk mendapatkan produk berdasarkan ID
        Product product = adminController.getProductById(productId);
        if (product == null) {
            System.out.println("Produk tidak ditemukan.");
            return;
        }
    
        System.out.println("Pilih status produk yang baru:");
        System.out.println("1. Tersedia");
        System.out.println("2. Dalam Perawatan");
        System.out.println("3. Tidak Tersedia");
        System.out.print("Pilih opsi: ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine(); // untuk membersihkan buffer input
    
        String status = "";
        switch (statusChoice) {
            case 1:
                status = "Tersedia";
                break;
            case 2:
                status = "Dalam Perawatan";
                break;
            case 3:
                status = "Tidak Tersedia";
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }
    
        // Update status produk
        product.setStatus(status);
        System.out.println("Status produk dengan ID " + productId + " berhasil diperbarui.");
    
        // Simpan perubahan produk setelah update status
        saveProductsToFile(adminController.getProducts());
        System.out.println("Perubahan status telah disimpan.");
    }  

    private void approveRental() {
        System.out.print("Masukkan Rental ID yang akan disetujui: ");
        int rentalID = scanner.nextInt();
        scanner.nextLine();
        adminController.approveRental(rentalID);
    }

    private void rejectRental() {
        System.out.print("Masukkan Rental ID yang akan ditolak: ");
        int rentalID = scanner.nextInt();
        scanner.nextLine();
        adminController.rejectRental(rentalID);
    }

    private void processRentalRequests() {
        System.out.println("=== Daftar Penyewaan Menunggu Persetujuan ===");
        List<Rental> pendingRentals = adminController.getPendingRentals();

        if (pendingRentals.isEmpty()) {
            System.out.println("Tidak ada penyewaan yang menunggu persetujuan.");
            return;
        }

        for (Rental rental : pendingRentals) {
            System.out.println("Rental ID: " + rental.getRentalID());
            System.out.println("Customer: " + rental.getCustomer().getUsername());
            System.out.println("Tanggal Sewa: " + rental.getStartDate());
            System.out.println("Total Harga: Rp " + rental.getTotalPrice());
            System.out.println("------------------------------");
        }

        System.out.print("Masukkan Rental ID yang ingin diproses: ");
        int rentalID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Pilih aksi:");
        System.out.println("1. Setujui Penyewaan");
        System.out.println("2. Tolak Penyewaan");
        System.out.print("Pilih opsi: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        boolean success = false;
        if (action == 1) {
            success = adminController.approveRental(rentalID);
            System.out.println("Penyewaan dengan Rental ID " + rentalID + " disetujui.");
        } else if (action == 2) {
            success = adminController.rejectRental(rentalID);
            System.out.println("Penyewaan dengan Rental ID " + rentalID + " ditolak.");
        } else {
            System.out.println("Pilihan tidak valid.");
        }

        if (!success) {
            System.out.println("Gagal memproses penyewaan. Pastikan Rental ID benar.");
        }
    }
    
    private void saveProductsToFile(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
            oos.writeObject(products);
            System.out.println("Daftar produk berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan produk ke file: " + e.getMessage());
        }
    }
    
}
