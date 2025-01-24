package org.pandey.minor1.service;

import org.pandey.minor1.model.*;
import org.pandey.minor1.repository.AuthorRepository;
import org.pandey.minor1.repository.BookRepository;
import org.pandey.minor1.request.BookCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private  AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;


    public Book createBook(BookCreateRequest bookCreateRequest) {

        // first need to check what is coming to me from FE is already present in my Db or not
        //if not present add to Db otherwise will not add one more duplicate row into Db

        //so we are querying and checking these from ths line


        Author authorFromDb =   authorRepository.findByEmail(bookCreateRequest.getAuthorEmail());



        if(authorFromDb == null){

            // I have to crete a row inside an author table

            authorFromDb = authorRepository.save(bookCreateRequest.toAuthor());

        }

        // create a row inside my book

        Book book = bookCreateRequest.toBook();
        book.setAuthor(authorFromDb);
        return bookRepository.save(book);
    }

    public List<Book> filter(FilterType filterBy, Operator operator, String value) {

        switch(operator){
            case EQUALS :
                switch (filterBy){
                    case BOOK_NO :
                        return bookRepository.findByBookNo(value);

                    case AUTHOR_NAME:
                        return bookRepository.findByAuthorName(value);

                    case COST:
                        return bookRepository.findByCost(Integer.valueOf(value));

                    case BOOK_TYPE:
                        return bookRepository.findByType(BookType.valueOf(value));
                }


            case LESS_THAN:
                switch (filterBy){

                    case COST :
                        return bookRepository.findByCostLessThan(Integer.valueOf(value));
                }

            default:
                return new ArrayList<>();

        }



        }

    public void saveUpdate(Book book){
        bookRepository.save(book);
    }
}

//create filter for student
//crete one more column in author as contact and that ddl auto shoud be as update only letus see changes can be done
