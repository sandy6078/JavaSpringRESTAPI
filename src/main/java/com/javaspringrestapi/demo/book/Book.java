package com.javaspringrestapi.demo.book;

import java.util.Objects;

import com.javaspringrestapi.demo.ReadJSON;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOOK")
public class Book {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 55)
    private String name;
	@Column(nullable = false, length = 55)
    private String author;
	@Column(nullable = false, length = 4)
    private String publicationDate;
	@Column(nullable = false, length = 55)
	private String publisher;
	@Column(nullable = false, length = 10)
	private Float price;
	@Column(nullable = false, length = 10)
	private Long stock;
	@Column(nullable = false, length = 255)
	private String[] genre;

	Book() {}

    public Book(String name, String author, String publicationDate, String publisher, Float price, Long stock, String genreString) {
        this.name = name;
        this.author = author;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.price = price;
        this.stock = stock;
        //Get a string array from JSON string so we correctly display genre as an array in the API call
        ReadJSON readJson = new ReadJSON();
        this.genre = readJson.stringToStringArray(genreString);
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationDate() {
		return publicationDate;
	}
    
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
    
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
    public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String[] getGenre() {
		return genre;
	}

	public void setGenre(String genreString) {
		//Get a string array from JSON string so we correctly display genre as an array in the API call
        ReadJSON readJson = new ReadJSON();
        this.genre = readJson.stringToStringArray(genreString);
	}
	
    @Override
    public boolean equals(Object o) {
    	//If this object is equal to object return true
        if (this == o) return true;
        //If object is not an instance and type of book then return false
        if (!(o instanceof Book)) return false;
        //Cast object as an object of Book and compare its value if all are the same return true else return false 
        Book book = (Book) o;
        return Objects.equals(this.id, book.id) && Objects.equals(this.name, book.name) && Objects.equals(this.author, book.author) 
        && Objects.equals(this.publicationDate, book.publicationDate) && Objects.equals(this.publisher, book.publisher) && Objects.equals(this.price, book.price)
        && Objects.equals(this.stock, book.stock) && Objects.equals(this.genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.author, this.publicationDate, this.publisher, this.price, this.stock, this.genre);
    }

    @Override
    public String toString() {
        return "Book = {" + "id=" + this.id + ", name=" + this.name + ", author=" + this.author + ", publicationDate=" + this.publicationDate
        + ", publisher='" + this.publisher + ", price=" + this.price + ", stock=" + this.stock + ", genre=" + this.genre + "}";
    }
    
}