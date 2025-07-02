package model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class WorkSpaceModel {

    @NotBlank
    @Min(1)
    private int id;

    @NotBlank
    private String type;

    @NotBlank
    @Min(1)
    private int price;

    @NotBlank
    private boolean availability = true;

    public WorkSpaceModel() {}

    public WorkSpaceModel(int id, String type, int price, boolean availability) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.availability = availability;
    }

    public WorkSpaceModel(int id, String type, int price) {
        this.id = id;
        this.type = type;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
