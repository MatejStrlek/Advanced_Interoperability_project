package hr.algebra.advanced_interoperability_project.dto;

import jakarta.validation.constraints.NotNull;

public class MobileDTO {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Brand is required")
    private String brand;
    @NotNull(message = "Price is required")
    private double price;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Rating is required")
    private double rating;

    public MobileDTO(String name, String brand, double price, String description, double rating) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    public MobileDTO() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
}