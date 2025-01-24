package org.pandey.minor1.controller;

import org.pandey.minor1.model.Book;
import org.pandey.minor1.model.FilterType;
import org.pandey.minor1.model.Operator;
import org.pandey.minor1.request.BookCreateRequest;
import org.pandey.minor1.response.GenericResponse;
import org.pandey.minor1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("create")
    public Book createBook(@RequestBody BookCreateRequest bookCreateRequest){
    //validation can be here
           return bookService.createBook(bookCreateRequest);

    }

    @GetMapping("/filter")
    public ResponseEntity<GenericResponse<List<Book>>> filter(@RequestParam("filterBy") FilterType filterBy,
                                                             @RequestParam("operator") Operator operator,
                                                             @RequestParam("value") String value){

  List<Book> list = bookService.filter(filterBy,operator,value);

        GenericResponse<List<Book>> response = new GenericResponse<>(list, "" , "Succcess" , "200");

        ResponseEntity entity = new ResponseEntity<>(response, HttpStatus.OK);

        return entity;
    }
}
