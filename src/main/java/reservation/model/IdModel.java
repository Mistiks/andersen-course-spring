package reservation.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class IdModel {

    @NotNull
    @Min(1)
    private int id;

    public IdModel() {}

    public IdModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
