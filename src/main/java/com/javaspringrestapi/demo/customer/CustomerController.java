package com.javaspringrestapi.demo.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaspringrestapi.demo.ObjectNotFoundException;

@RestController
public class CustomerController {
	
	private final CustomerRepository repository;
	private final CustomerModelAssembler assembler;

	CustomerController(CustomerRepository customerRepository, CustomerModelAssembler assembler) {
	this.repository = customerRepository;
	this.assembler = assembler;
	}

	//Get all customer
	@GetMapping("/customers")
	CollectionModel<EntityModel<Customer>> getCustomers() {
		//Fetch all customers then change into a list of EntityModel objects wrapped with the CustomerModelAssembler
		List<EntityModel<Customer>> customers = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());
	}
	
	//Get a customer by id
	@GetMapping("/customers/{id}")
	EntityModel<Customer> getCustomer(@PathVariable Long id) {
		//Fetch an object with an if of value else throw ObjectNotFoundException with name of value "customer" and id given
		Customer customer = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("customer", id));
		return assembler.toModel(customer);
	}

	//Set new customer
	@PostMapping("/customers")
	ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer) {
		//Create an entity model of the object newCustomer; save to repository wrapped with the CustomerModelAssembler
		EntityModel<Customer> entityModel = assembler.toModel(repository.save(newCustomer));
		//Retrieve link created by CustomerModelAssembler with IanaLinkRelations.SELF and turn into an URI 
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	//Update a customer by id
	@PutMapping("/customers/{id}")
	ResponseEntity<?> replaceCusomter(@RequestBody Customer newCustomer, @PathVariable Long id) {
		//Look for customer by id and update if exists
		Customer updatedCustomer = repository.findById(id).map(customer -> {
			customer.setFirstName(newCustomer.getFirstName());
			customer.setLastName(newCustomer.getLastName());
			customer.setCity(newCustomer.getCity());
			customer.setPostCode(newCustomer.getPostCode());
			customer.setAddress(newCustomer.getAddress());
			customer.setPhoneNumber(newCustomer.getPhoneNumber());
			return repository.save(customer);
		}).orElseGet(() -> {
			//Create if doesn't exist
			newCustomer.setId(id);
			return repository.save(newCustomer);
		});
		//Create an entity model book wrapped by the CustomerModelAssembler
		EntityModel<Customer> entityModel = assembler.toModel(updatedCustomer);
		//Retrieve link created by CustomerModelAssembler with IanaLinkRelations.SELF and turn into an URI 
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	//Delete customer by id
	@DeleteMapping("/customers/{id}")
	EntityModel<Customer> deleteCusomter(@PathVariable Long id) {
		Customer customer = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("customer", id));;
		if (customer != null) {
			repository.deleteById(id);
		}
		return assembler.toModel(customer);
	}
}
