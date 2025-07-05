package model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationModel {

    @NotNull
    @Min(1)
    private int id;

    @NotNull
    @Min(1)
    private int spaceId;

    @NotBlank
    private String clientName;

    @NotBlank
    private String date;

    @NotBlank
    private String timeStart;

    @NotBlank
    private String timeEnd;

    public ReservationModel() {
    }

    public ReservationModel(int id, int spaceId, String clientName, String date, String timeStart, String timeEnd) {
        this.id = id;
        this.spaceId = spaceId;
        this.clientName = clientName;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
