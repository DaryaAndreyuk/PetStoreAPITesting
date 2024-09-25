package models;

import java.util.Objects;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
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
        if (other == this) {
            return true;
        }
        if (!(other instanceof Order)) {
            return false;
        }
        Order rhs = ((Order) other);
        return ((((((Objects.equals(this.petId, rhs.petId)) &&
                (Objects.equals(this.quantity, rhs.quantity))) &&
                (Objects.equals(this.id, rhs.id))) &&
                (Objects.equals(this.shipDate, rhs.shipDate))) &&
                (Objects.equals(this.complete, rhs.complete))) &&
                (Objects.equals(this.status, rhs.status)));
    }

}
