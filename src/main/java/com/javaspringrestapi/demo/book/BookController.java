package com.javaspringrestapi.demo.book;

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
import com.javaspringrestapi.demo.ReadJSON;

@RestController
class BookController {

    private final BookRepository repository;

    private final BookModelAssembler assembler;

    BookController(BookRepository repository, BookModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    //Get all books
    @GetMapping("/books")
    CollectionModel<EntityModel<Book>> getBooks() {
    	//Fetch all books then change into a list of EntityModel objects wrapped with the BookModelAssembler
        List<EntityModel<Book>> books = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(books, linkTo(methodOn(BookController.class).getBooks()).withSelfRel());
    }
    
    //Get a book by id
    @GetMapping("/books/{id}")
    EntityModel<Book> getBook(@PathVariable Long id) {
    	//Fetch an object with an if of value else throw ObjectNotFoundException with name of value "book" and id given
        Book book = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("book", id));
    	return assembler.toModel(book);
    }

    //Set new book
    @PostMapping("/books")
    ResponseEntity<?> newBook(@RequestBody Book newBook) {
    	//Create an entity model of the object newBook; save to repository wrapped with the BookModelAssembler
        EntityModel<Book> entityModel = assembler.toModel(repository.save(newBook));
        //Retrieve link created by BookModelAssembler with IanaLinkRelations.SELF and turn into an URI 
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    
    //Update a book by id
    @PutMapping("/books/{id}")
    ResponseEntity<?> updateBook(@RequestBody Book newBook, @PathVariable Long id) {
    	//Look for the book by id and update if exists
        Book updatedBook = repository.findById(id).map(book -> {
            book.setName(newBook.getName());
            book.setAuthor(newBook.getAuthor());
            book.setPublicationDate(newBook.getPublicationDate());
            book.setPublisher(newBook.getPublisher());
            book.setStock(newBook.getStock());
            ReadJSON readJson = new ReadJSON();
            book.setGenre(readJson.stringArrayToString(newBook.getGenre()));
            return repository.save(book);
        }).orElseGet(() -> {
        	//Create if doesn't exist
            newBook.setId(id);
            return repository.save(newBook);
        });
        //Create an entity model book wrapped by the BookModelAssembler
        EntityModel<Book> entityModel = assembler.toModel(updatedBook);
        //Retrieve link created by BookModelAssembler with IanaLinkRelations.SELF and turn into an URI 
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    //Delete a book by id
    @DeleteMapping("/books/{id}")
    EntityModel<Book> deleteBook(@PathVariable Long id) {
    	//Fetch an object with an if of value else throw ObjectNotFoundException with name of value "book" and id given
    	Book book = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("book", id));;
    	if (book != null) {
            repository.deleteById(id);
    	}
        return assembler.toModel(book);
    }

}