package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private IBookService bookService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("books")
    public ModelAndView listBooks() {
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("books/list");
        modelAndView.addObject("listBooks", books);
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("books/create");
        modelAndView.addObject("books1", new Book());
        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView saveBook(@ModelAttribute("books1") Book book) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("books/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "New book created successfully");
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("books/edit");
            modelAndView.addObject("book", book.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("edit")
    public ModelAndView updateBook(@ModelAttribute("book")Book book){
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("books/edit");
        modelAndView.addObject("book",book);
        modelAndView.addObject("message", "Book updated successfully");
        return  modelAndView;
    }
    @GetMapping("delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Book>book=bookService.findById(id);
        if (book !=null){
            ModelAndView modelAndView = new ModelAndView("books/delete");
            modelAndView.addObject("book",book.get());
            return  modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("delete")
    public String deleteBook(@ModelAttribute("book")Book book){
        bookService.remove(book.getId());
        return "redirect:books";
    }
    @GetMapping("view-category/{id}")
    public ModelAndView viewCategory(@PathVariable Long id){
        Optional<Category>category = categoryService.findById(id);
        if (!category.isPresent()){
            return new ModelAndView("/error.404");
        }
        Iterable<Book> books = bookService.findAllByCategory(category.get());

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category", category.get());
        modelAndView.addObject("books", books);
        return modelAndView;
    }
    @GetMapping("view-name")
    public ModelAndView findBookByName(@RequestParam String name){
        List<Book> book = bookService.findBookByName(name);
        ModelAndView modelAndView = new ModelAndView("books/view-book");
        modelAndView.addObject("books1",book);
        return modelAndView;
    }
}
