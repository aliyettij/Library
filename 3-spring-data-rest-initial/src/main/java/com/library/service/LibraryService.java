package com.library.service;

import com.library.domain.Book;
import com.library.domain.Member;
import org.springframework.hateoas.Resources;

import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface LibraryService {

    void checkoutBooks(Long memberId, Book... books);
    void returnBooks(Book... books);
    List<Book> getOverdueBooks();
}
