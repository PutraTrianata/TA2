package TA2;

import TA2.Controller.AdminController;
import TA2.Controller.CustomerController;
import TA2.Model.*;
import TA2.View.AdminView;
import TA2.View.CustomerView;

import java.io.*;
import java.util.*;

public class Main {
    private static final String CUSTOMER_FILE = "TA2/customers.txt";
    private static final String PRODUCT_FILE = "TA2/products.txt";

    public static void main(String[] args) {
        RentalManagementSystem rentalSystem = new RentalManagementSystem();
        Scanner scanner = new Scanner(System.in);

        List<Customer> customers = loadCustomersFromFile();
        List<Product> products = loadProductsFromFile();

        products.forEach(rentalSystem::addProduct);

        while (true) {
            System.out.println("\n=== Sistem Penyewaan ===");
            System.out.println("1. Login sebagai Admin");
            System.out.println("2. Daftar sebagai Customer");
            System.out.println("3. Login sebagai Customer");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> loginAdmin(scanner, rentalSystem);
                case 2 -> registerCustomer(scanner, customers);
                case 3 -> loginCustomer(scanner, customers, rentalSystem);
                case 4 -> {
                    saveCustomersToFile(customers);
                    saveProductsToFile(rentalSystem.getProducts());
                    System.out.println("Data customer dan produk telah disimpan.");
                    System.out.println("Terima kasih telah menggunakan sistem penyewaan.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void loginAdmin(Scanner scanner, RentalManagementSystem rentalSystem) {
        System.out.print("Masukkan username admin: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {
            Admin admin = new Admin("admin", "admin123", "08123456789", "Alamat Admin", 1, rentalSystem);
            AdminController adminController = new AdminController(admin, rentalSystem);
            AdminView adminView = new AdminView(adminController);
            adminView.showMenu();
        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }
    }

    private static void registerCustomer(Scanner scanner, List<Customer> customers) {
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan nomor HP: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();
        System.out.print("Masukkan alamat (pisahkan dengan koma jika lebih dari satu): ");
        String addressInput = scanner.nextLine();

        List<String> addresses = new ArrayList<>(Arrays.asList(addressInput.split(",")));
        Customer newCustomer = new Customer(username, password, phoneNumber, addresses, customers.size() + 1);
        customers.add(newCustomer);
        System.out.println("Pendaftaran berhasil! Anda sekarang dapat login.");
    }

    private static void loginCustomer(Scanner scanner, List<Customer> customers, RentalManagementSystem rentalSystem) {
        if (customers.isEmpty()) {
            System.out.println("Belum ada customer yang terdaftar. Silakan daftar terlebih dahulu.");
            return;
        }

        System.out.print("Masukkan username customer: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                CustomerController customerController = new CustomerController(c, rentalSystem);
                CustomerView customerView = new CustomerView(customerController);
                customerView.showMenu();
                return;
            }
        }
        System.out.println("Login gagal! Username atau password salah atau belum terdaftar.");
    }

    private static void saveCustomersToFile(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer c : customers) {
                writer.write(c.getUsername() + "," + c.getPassword() + "," + c.getPhoneNumber() + "," + String.join(",", c.getAddresses()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data customer: " + e.getMessage());
        }
    }

    private static List<Customer> loadCustomersFromFile() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    List<String> addresses = Arrays.asList(parts[3].split(","));
                    customers.add(new Customer(parts[0], parts[1], parts[2], addresses, customers.size() + 1));
                }
            }
        } catch (IOException e) {
            System.out.println("Tidak ada data customer yang disimpan.");
        }
        return customers;
    }

    private static void saveProductsToFile(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product p : products) {
                writer.write(p.getId() + "," + p.getName() + "," + p.getBrand() + "," + p.getType() + "," + p.getPrice() + "," + p.getDescription() + "," + p.getStock() + "," + p.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data produk: " + e.getMessage());
        }
    }

    private static List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    products.add(new Product(
                            Integer.parseInt(parts[0]), parts[1], parts[2], parts[3],
                            Double.parseDouble(parts[4]), parts[5], Integer.parseInt(parts[6]), parts[7]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Tidak ada data produk yang disimpan.");
        }
        return products;
    }
}
