package com.library.service;

import com.library.domain.Book;

import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface LibraryService {

    void checkoutBooks(Long member, Book... books);
    void returnBooks(Book... books);
    List<Book> getOverdueBooks();
}
