package com.library.rest;

import com.library.domain.Book;
import com.library.service.BookRepository;
import com.library.service.LibraryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Controller
@RequestMapping(value = "/books")
public class BookController {

    private static final Logger LOGGER = Logger.getLogger(BookController.class);

    @Autowired
    LibraryService libraryService;

    @Autowired
    BookRepository bookRepository;



    //get collection
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = new ArrayList<>();
        CollectionUtils.addAll(books, bookRepository.findAll().iterator());
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    //post to collection
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        Book resultItem = bookRepository.save(book);

        return new ResponseEntity<Book>(resultItem, HttpStatus.CREATED);
    }

    //get item
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return new ResponseEntity<Book>(bookRepository.findOne(id), HttpStatus.OK);
    }

    //put item
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book> putBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        bookRepository.save(book);
        return new ResponseEntity<Book>(bookRepository.findOne(id), HttpStatus.OK);
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
