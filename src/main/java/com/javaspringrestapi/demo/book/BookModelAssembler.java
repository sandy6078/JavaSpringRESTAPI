package com.javaspringrestapi.demo.book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {
    	//Convert a Book object into an EntityModel<Book> object using Spring HATEOAS
    	return EntityModel.of(book,linkTo(methodOn(BookController.class).getBook(book.getId())).withSelfRel(),linkTo(methodOn(BookController.class).getBooks()).withRel("books"));
    }

}