package models;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Setter
@Getter
public class Order {

    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    public Order() {
    }

    public Order(Integer id, Integer petId, Integer quantity, String shipDate, String status, Boolean complete) {
        super();
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.petId == null) ? 0 : this.petId.hashCode()));
        result = ((result * 31) + ((this.quantity == null) ? 0 : this.quantity.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.shipDate == null) ? 0 : this.shipDate.hashCode()));
        result = ((result * 31) + ((this.complete == null) ? 0 : this.complete.hashCode()));
        result = ((result * 31) + ((this.status == null) ? 0 : this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Order)) {
            return false;
        }
        Order rhs = (Order) other;

        ZonedDateTime thisShipDate = parseDate(this.shipDate);
        ZonedDateTime rhsShipDate = parseDate(rhs.shipDate);

        return Objects.equals(this.petId, rhs.petId) &&
                Objects.equals(this.quantity, rhs.quantity) &&
                Objects.equals(this.id, rhs.id) &&
                Objects.equals(thisShipDate, rhsShipDate) &&
                Objects.equals(this.complete, rhs.complete) &&
                Objects.equals(this.status, rhs.status);
    }

    public static ZonedDateTime parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            return ZonedDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            System.err.println("Failed to parse date: " + dateString);
            return null;
        }
    }

    @Override
    public String toString() {
        return "Order {" +
                "id = " + id +
                ", petId = " + petId +
                ", quantity = " + quantity +
                ", shipDate = '" + shipDate + '\'' +
                ", status = '" + status + '\'' +
                ", complete = " + complete +
                '}';
    }
}