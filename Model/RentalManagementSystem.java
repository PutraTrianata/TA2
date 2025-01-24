package TA2.Model;

import java.util.*;
import java.util.stream.Collectors;

public class RentalManagementSystem {
    private Map<Integer, Product> productCatalog = new HashMap<>();
    private Map<Integer, Rental> rentalList = new HashMap<>();
    private List<Product> products = new ArrayList<>();  // Tambahkan ini
    private int nextProductId = 1; // ID produk akan bertambah otomatis

    public List<Product> getAvailableProducts() {
        return products.stream()
                .filter(p -> p.getStock() > 0) // Hanya produk dengan stok > 0
                .collect(Collectors.toList());
    }

    // Menambahkan produk ke katalog dan daftar produk
    public void addProduct(Product product) {
        product.setId(nextProductId);
        productCatalog.put(product.getId(), product);
        products.add(product);  // Tambahkan ke daftar produk
        nextProductId++;
    }

    // Menghapus produk dari katalog berdasarkan ID produk
    public void deleteProduct(int productID) {
        Product removedProduct = productCatalog.remove(productID);
        if (removedProduct != null) {
            products.remove(removedProduct);  // Hapus juga dari daftar produk
        }
    }

    // Mengambil produk berdasarkan ID produk
    public Product getProduct(int productID) {
        return productCatalog.get(productID);
    }

    public List<Product> getProducts() {
        return products;
    }
    

    // Menambahkan penyewaan ke dalam daftar penyewaan
    public void addRental(Rental rental) {
        rentalList.put(rental.getRentalID(), rental);
    }
    // Menampilkan daftar penyewaan
    public void viewRentals() {
        if (rentalList.isEmpty()) {
            System.out.println("No rentals available.");
        } else {
            System.out.println("List of Rentals:");
            for (Rental rental : rentalList.values()) {
                System.out.println("Rental ID: " + rental.getRentalID());
                System.out.println("Customer: " + rental.getCustomer().getUsername());
                System.out.println("Rental Status: " + rental.getRentalStatus());
                System.out.println("Total Price: " + rental.getTotalPrice());
                System.out.println("Return Date: " + rental.getReturnDate());
                System.out.println("-----------------------------");
            }
        }
    }

    // public Rental getRental(int rentalID) {
    //     if (!rentalList.containsKey(rentalID)) {
    //         System.out.println("Rental ID " + rentalID + " not found.");
    //         return null;
    //     }
    //     return rentalList.get(rentalID);
    // }
    public Map<Integer, Rental> getRentalList() {
        return rentalList;
    }
    

    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == updatedProduct.getId()) {
                products.set(i, updatedProduct);  // Update produk jika ID-nya cocok
                break;
            }
        }
    }

    // Menampilkan daftar produk yang tersedia
    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("Belum ada produk yang tersedia.");
            return;
        }
        System.out.println("\n=== Daftar Produk ===");
        for (Product product : products) {  // Use 'products' directly instead of rentalSystem.getProducts()
            System.out.println("ID: " + product.getId());
            System.out.println("Nama: " + product.getName());
            System.out.println("Merek: " + product.getBrand());
            System.out.println("Jenis: " + product.getType());
            System.out.println("Harga: " + product.getPrice());
            System.out.println("Deskripsi: " + product.getDescription());
            System.out.println("Stok: " + product.getStock());
            System.out.println("Status: " + product.getStatus());  // Tampilkan status produk
            System.out.println("----------------------------");
        }
    }

    public int getNextProductId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextProductId'");
    }    
}
