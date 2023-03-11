package com.javaspringrestapi.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.javaspringrestapi.demo.book.Book;
import com.javaspringrestapi.demo.book.BookRepository;
import com.javaspringrestapi.demo.customer.Customer;
import com.javaspringrestapi.demo.customer.CustomerRepository;
import com.javaspringrestapi.demo.order.CustomerOrder;
import com.javaspringrestapi.demo.order.CustomerOrderRepository;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository, CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository) {
        return args -> {
        	//Populate Books, ClientOrders with information from JSON files
        	//Books JSON file
        	loadJSONFile("Books", bookRepository, customerOrderRepository, customerRepository);
        	bookRepository.findAll().forEach(book -> 
    			log.info("Preloaded " + book
    		));
        	//Customer JSON file
        	loadJSONFile("Customers", bookRepository, customerOrderRepository, customerRepository);
        	customerRepository.findAll().forEach(customer -> 
    			log.info("Preloaded " + customer
    		));
        	//Customer Orders JSON file
        	loadJSONFile("Customer_Orders", bookRepository, customerOrderRepository, customerRepository);
        	customerOrderRepository.findAll().forEach(customerOrder -> 
    			log.info("Preloaded " + customerOrder
    		));
        	
        };  
    }
    
    private void loadJSONFile(String fileName, BookRepository bookRepository, CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository) throws JsonMappingException, JsonProcessingException {
    	ReadJSON readJson = new ReadJSON();
    	Map<String, Map<String, Object>> fileMap = readJson.read("resources/" + fileName + ".json");
    	for (String key : fileMap.keySet()) {
    		//Get object by a key; keys are within the resource file
    	    Map<String, Object> obj = fileMap.get(key);
    	    switch(fileName) {
    	    	case "Books":
        	    	//Create a book object 
            	    String name = (String) obj.get("name");
            	    String author = (String) obj.get("author");
            	    String publicationDate = (String) obj.get("publicationDate");
            	    String publisher = (String) obj.get("publisher");
            	    Float price = Float.valueOf((String) obj.get("price"));
            	    Long stock = Long.parseLong((String) obj.get("stock"));
            	    LinkedHashMap<?, ?> genreMap = (LinkedHashMap<?, ?>) obj.get("genre");
            	    //Get a string from LinkedHashMap
            	    String genreString = readJson.mapToString(genreMap);
            	    //Save to repository
            	    bookRepository.save(new Book(name, author, publicationDate, publisher, price, stock, genreString));
            	    break;
    	    	case "Customers":
    	    		//Create a customer object 
            	    String firstName = (String) obj.get("firstName");
            	    String lastName = (String) obj.get("lastName");
            	    String city = (String) obj.get("city");
            	    String postCode = (String) obj.get("postCode");
            	    String address = (String) obj.get("address");
            	    String phoneNumber = (String) obj.get("phoneNumber");
            	    //Save to repository
            	    customerRepository.save(new Customer(firstName, lastName, city, postCode, address, phoneNumber));
            	    break;
    	    	case "Customer_Orders":
    	    		//Create a customer orders object 
            	    Long clientId = Long.parseLong((String) obj.get("clientId"));
            	    Long bookId =  Long.parseLong((String) obj.get("bookId"));
            	    Float orderPrice = Float.valueOf((String) obj.get("price"));
            	    String purchaseDate = (String) obj.get("purchaseDate");
            	    String status = (String) obj.get("status");
            	    //Save to repository
            	    customerOrderRepository.save(new CustomerOrder(clientId, bookId, orderPrice, purchaseDate, status));
            	    break;
    	    }
    	}
    }
}