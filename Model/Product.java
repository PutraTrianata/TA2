package TA2.Model;

import java.io.Serializable;  // Import untuk Serializable

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;  // Menambahkan serialVersionUID untuk kompatibilitas versi
    private int id;
    private String name;
    private String brand;
    private String type;
    private double price;
    private String description;
    private int stock;
    private String category;
    private String status;

    // Konstruktor utama dengan status
    public Product(int id, String name, String brand, String type, double price, String description, int stock, String status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.status = status;
    }

    // Konstruktor tanpa status (untuk kasus tertentu)
    public Product(int id, String name, String brand, String type, double price, String description, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.status = "Tersedia";  // Default status
    }

    // Getter dan Setter untuk category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter dan Setter untuk properti lainnya
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
