package com.library.service;

import com.library.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@RepositoryRestResource(path = "books", collectionResourceRel = "books", itemResourceRel = "book")
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByDueDateBefore(@Param("date") Date date);

    //List<Book> findByAuthorContainsIgnoreCase(@Param("author") String author);
}
