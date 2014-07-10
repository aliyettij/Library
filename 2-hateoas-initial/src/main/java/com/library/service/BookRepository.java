package com.library.service;

import com.library.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByDueDateBefore(@Param("date") Date date);

    //List<Book> findByAuthorContainsIgnoreCase(@Param("author") String author);
}
