package com.javaspringrestapi.demo.customer;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(nullable = false, length = 55)
    private String firstName;
	@Column(nullable = false, length = 55)
    private String lastName;
	@Column(nullable = false, length = 55)
    private String city;
	@Column(nullable = false, length = 55)
    private String postCode;
	@Column(nullable = false, length = 55)
    private String address;
	@Column(nullable = false, length = 55)
    private String phoneNumber;

	Customer() {}

	public Customer(String firstName, String lastName, String city, String postCode, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.postCode = postCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    @Override
    public boolean equals(Object o) {
    	//If this object is equal to object return true
        if (this == o) return true;
        //If object is not an instance and type of book then return false
        if (!(o instanceof Customer)) return false;
        //Cast object as an object of Customer and compare its value if all are the same return true else return false 
        Customer customer = (Customer) o;
        return Objects.equals(this.id, customer.id) && Objects.equals(this.firstName, customer.firstName) && Objects.equals(this.lastName, customer.lastName) 
        && Objects.equals(this.city, customer.city) && Objects.equals(this.postCode, customer.postCode) && Objects.equals(this.address, customer.address)
        && Objects.equals(this.phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.city, this.postCode, this.address, this.phoneNumber);
    }

    @Override
    public String toString() {
        return "Customer = {" + "id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", city=" + this.city
        + ", postCode=" + this.postCode + ", address=" + this.address + ", phoneNumber=" + this.phoneNumber + "}";
    }
}