package com.library.exceptions;

import com.library.domain.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookNotCheckedOutException extends RuntimeException {

    public BookNotCheckedOutException(Book book) {
        super(String.format("%s [ISBN:%s] is not checked out.", book.getTitle(), book.getIsbn()));
    }
}
