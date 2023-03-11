package com.javaspringrestapi.demo.order;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class CustomerOrder {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(nullable = false, length = 20)
    private Long clientId;
	@Column(nullable = false, length = 20)
    private Long bookId;
	@Column(nullable = false, length = 11)
    private Float price;
	@Column(nullable = false, length = 11)
	private String purchaseDate;
	@Column(nullable = false, length = 55)
	private String status;

	CustomerOrder() {}

	public CustomerOrder(Long clientId, Long bookId, Float price, String purchaseDate, String status) {
        this.clientId = clientId;
        this.bookId = bookId;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    @Override
    public boolean equals(Object o) {
    	//If this object is equal to object return true
        if (this == o) return true;
        //If object is not an instance and type of book then return false
        if (!(o instanceof CustomerOrder)) return false;
        //Cast object as an object of CustomerOrder and compare its value if all are the same return true else return false 
        CustomerOrder customerOrder = (CustomerOrder) o;
        return Objects.equals(this.id, customerOrder.id) && Objects.equals(this.clientId, customerOrder.clientId) && Objects.equals(this.bookId, customerOrder.bookId) 
        && Objects.equals(this.price, customerOrder.price) && Objects.equals(this.purchaseDate, customerOrder.purchaseDate) && Objects.equals(this.status, customerOrder.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.clientId, this.bookId, this.price, this.purchaseDate, this.status);
    }

    @Override
    public String toString() {
        return "Customer Order = {" + "id=" + this.id + ", clientId=" + this.clientId + ", bookId=" + this.bookId + ", price=" + this.price
        + ", purchaseDate=" + this.purchaseDate + ", status=" + this.status + "}";
    }
}