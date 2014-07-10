package com.library.rest;

import com.library.domain.Book;
import com.library.hateoas.BookResourceAssembler;
import com.library.service.BookRepository;
import com.library.service.LibraryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Controller
@RequestMapping(value = "/books")
@ExposesResourceFor(Book.class)
public class BookController {

    private static final Logger LOGGER = Logger.getLogger(BookController.class);

    @Autowired
    LibraryService libraryService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    BookResourceAssembler bookResourceAssembler;

    //get collection
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<Resource<Book>>> getBooks() {


        List<Resource<Book>> bookResources = new ArrayList<Resource<Book>>();
        for (Book book : bookRepository.findAll()) {
           bookResources.add(bookResourceAssembler.toResource(book));
        }


        Resources<Resource<Book>> resources = new Resources<>(bookResources);
        resources.add(linkTo(BookController.class).withSelfRel());

        return new ResponseEntity<>(resources, HttpStatus.OK);

    }

    //post to collection
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Book>> createBook(@RequestBody Book book) {

        Book resultItem = bookRepository.save(book);
        Resource<Book> newBookResource = new Resource<>(resultItem);

        //TODO: Add header to make the new book discoverable
        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(URI.create(entityLinks.linkForSingleResource(resultItem).toString()));

        return new ResponseEntity<>(newBookResource, headers, HttpStatus.CREATED);
    }

    //get item
    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Resource<Book>> getBook(@PathVariable Long id) {
        Book book = bookRepository.findOne(id);
        return new ResponseEntity<>(bookResourceAssembler.toResource(book), HttpStatus.OK);
    }


    //put item
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Book>> putBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);

        Book savedBook = bookRepository.save(book);

        Resource<Book> savedBookResource = bookResourceAssembler.toResource(savedBook);
        return new ResponseEntity<>(savedBookResource, HttpStatus.OK);
    }

    @RequestMapping(value ="/{id}/checkout", method = RequestMethod.PUT)
    public ResponseEntity<Void> checkout(@PathVariable("id") Book book,
                                         @RequestParam(value="memberId", required=true) Long memberId) {

        libraryService.checkoutBooks(memberId, book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value ="/{id}/checkin", method = RequestMethod.PUT)
    public ResponseEntity<Void> checkin(@PathVariable("id") Book book) {
        libraryService.returnBooks(book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }




}
