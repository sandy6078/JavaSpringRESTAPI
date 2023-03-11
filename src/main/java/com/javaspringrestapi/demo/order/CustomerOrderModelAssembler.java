package com.javaspringrestapi.demo.order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CustomerOrderModelAssembler implements RepresentationModelAssembler<CustomerOrder, EntityModel<CustomerOrder>> {

    @Override
    public EntityModel<CustomerOrder> toModel(CustomerOrder customerOrder) {
    	//Convert a CustomerOrder object into an EntityModel<CustomerOrder> object using Spring HATEOAS
    	return EntityModel.of(customerOrder,linkTo(methodOn(CustomerOrderController.class).getCustomerOrder(customerOrder.getId())).withSelfRel(),
        linkTo(methodOn(CustomerOrderController.class).getCustomerOrders()).withRel("customer orders"));
    }

}