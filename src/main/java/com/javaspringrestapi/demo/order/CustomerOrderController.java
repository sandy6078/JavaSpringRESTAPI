package com.javaspringrestapi.demo.order;

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
class CustomerOrderController {

	private final CustomerOrderRepository repository;
	private final CustomerOrderModelAssembler assembler;

	CustomerOrderController(CustomerOrderRepository customerOrderRepository, CustomerOrderModelAssembler assembler) {
	this.repository = customerOrderRepository;
	this.assembler = assembler;
	}

	//Get all customer orders
	@GetMapping("/customer-orders")
	CollectionModel<EntityModel<CustomerOrder>> getCustomerOrders() {
		//Fetch all customer orders then change into a list of EntityModel objects wrapped with the CustomerOrderModelAssembler
		List<EntityModel<CustomerOrder>> customerOrders = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(customerOrders, linkTo(methodOn(CustomerOrderController.class).getCustomerOrders()).withSelfRel());
	}
	
	//Get a customer order by id
	@GetMapping("/customer-orders/{id}")
	EntityModel<CustomerOrder> getCustomerOrder(@PathVariable Long id) {
		//Fetch an object with an if of value else throw ObjectNotFoundException with name of value "customer order" and id given
		CustomerOrder customerOrder = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("customer order", id));
		return assembler.toModel(customerOrder);
	}

	//Set new a customer order
	@PostMapping("/customer-orders")
	ResponseEntity<?> newCustomerOrder(@RequestBody CustomerOrder newCustomerOrder) {
		//Create an entity model of the object newCustomerOrder; save to repository wrapped with the CustomerOrderModelAssembler
		EntityModel<CustomerOrder> entityModel = assembler.toModel(repository.save(newCustomerOrder));
		//Retrieve link created by CustomerOrderModelAssembler with IanaLinkRelations.SELF and turn into an URI 
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	//Update a customer order by id
	@PutMapping("/customer-orders/{id}")
	ResponseEntity<?> replaceCusomterOrder(@RequestBody CustomerOrder newCustomerOrder, @PathVariable Long id) {
		//Look for customer order by id and update if exists
		CustomerOrder updatedCustomerOrder = repository.findById(id).map(customerOrder -> {
			customerOrder.setClientId(newCustomerOrder.getClientId());
			customerOrder.setBookId(newCustomerOrder.getBookId());
			customerOrder.setPrice(newCustomerOrder.getPrice());
			customerOrder.setPurchaseDate(newCustomerOrder.getPurchaseDate());
			customerOrder.setStatus(newCustomerOrder.getStatus());
			return repository.save(customerOrder);
		}).orElseGet(() -> {
			//Create if doesn't exist
			newCustomerOrder.setId(id);
			return repository.save(newCustomerOrder);
		});
		//Create an entity model book wrapped by the CustomerOrderModelAssembler
		EntityModel<CustomerOrder> entityModel = assembler.toModel(updatedCustomerOrder);
		//Retrieve link created by CustomerOrderModelAssembler with IanaLinkRelations.SELF and turn into an URI 
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	//Delete customer order by id
	@DeleteMapping("/customer-orders/{id}")
	EntityModel<CustomerOrder> deleteCusomterOrder(@PathVariable Long id) {
		CustomerOrder customerOrder = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("customer order", id));;
		if (customerOrder != null) {
			repository.deleteById(id);
		}
		return assembler.toModel(customerOrder);
	}
	
	//Get customer orders by client id
	@GetMapping("/customer-orders-client-id/{id}")
	CollectionModel<EntityModel<CustomerOrder>> getCustomerOrdersByClientId(@PathVariable Long id) {
		List<EntityModel<CustomerOrder>> customerOrders = repository.findByClientId(id).stream().map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(customerOrders, linkTo(methodOn(CustomerOrderController.class).getCustomerOrders()).withSelfRel());
	}
	
	//Get customer orders by book id
	@GetMapping("/customer-orders-book-id/{id}")
	CollectionModel<EntityModel<CustomerOrder>> getCustomerOrdersByBookId(@PathVariable Long id) {
		List<EntityModel<CustomerOrder>> customerOrders = repository.findByBookId(id).stream().map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(customerOrders, linkTo(methodOn(CustomerOrderController.class).getCustomerOrders()).withSelfRel());
	}
}