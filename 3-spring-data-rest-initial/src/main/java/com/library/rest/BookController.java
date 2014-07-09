package com.library.rest;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.hateoas.BookResourceProcessor;
import com.library.service.BookRepository;
import com.library.service.LibraryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

    @Autowired
    EntityLinks entityLinks;

    //TODO:  Complete wiring for this method.
    /**
     * This method that handles a POST to  http://localhost:8080/Library/books/{id}/checkout.
     * The Member should be determined from the RequestBody, which can have content-type "text/uri-list"
     * @param book
     * @param member
     * @return
     */
    public ResponseEntity<Void> checkout(Book book, Member member) {

        libraryService.checkoutBooks(member.getId(), book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value ="/{id}/checkout", method = RequestMethod.POST)
    public ResponseEntity<Void> checkout(@PathVariable("id") Book book,
                                         @RequestParam(value="memberId", required=true) Long memberId) {

        libraryService.checkoutBooks(memberId, book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }





    @RequestMapping(value ="/{id}/checkin", method = RequestMethod.POST)
    public ResponseEntity<Void> checkin(@PathVariable("id") Book book) {
        libraryService.returnBooks(book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }




}
