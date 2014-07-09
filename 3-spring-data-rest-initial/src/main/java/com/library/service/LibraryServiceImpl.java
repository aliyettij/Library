package com.library.service;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.exceptions.BookAlreadyCheckedOutException;
import com.library.exceptions.BookNotCheckedOutException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service
@Transactional(readOnly = true)
public class LibraryServiceImpl implements LibraryService{

    private static final Logger LOGGER = Logger.getLogger(LibraryServiceImpl.class);
    private static final int LENDING_LIMIT = 2;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void checkoutBooks(Long memberId, Book... books) {
        Member member = memberRepository.findOne(memberId);

        for (Book book : books) {
            if (book.isCheckedOut()) {
                throw new BookAlreadyCheckedOutException(book);
            }

            book.setCheckedOutTo(member);
            book.setCheckedOutOn(new Date());
            book.setDueDate(DateUtils.addWeeks(new Date(), 2));
        }


        bookRepository.save(Arrays.asList(books));


    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void returnBooks(Book... books) {
        for (Book book : books) {
            if (!book.isCheckedOut()) {
                throw new BookNotCheckedOutException(book);
            }

            book.setCheckedOutTo(null);
            book.setDueDate(null);
        }

        bookRepository.save(Arrays.asList(books));
    }

    @Override
    public List<Book> getOverdueBooks() {
        return bookRepository.findByDueDateBefore(new Date());
    }
}
