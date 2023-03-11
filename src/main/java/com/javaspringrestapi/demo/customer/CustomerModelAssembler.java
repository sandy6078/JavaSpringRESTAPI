package com.javaspringrestapi.demo.customer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer customer) {
    	//Convert a Customer object into an EntityModel<Customer> object using Spring HATEOAS
    	return EntityModel.of(customer,linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));
    }

}