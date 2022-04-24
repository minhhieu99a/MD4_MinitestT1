package com.codegym.repo.book;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IBookRepository extends PagingAndSortingRepository<Book, Long> {
    Iterable<Book> findAllByCategory(Category category);

    //    Iterable<Book>findBookByName(String name);
    List<Book> findBookByName(String name);

}
