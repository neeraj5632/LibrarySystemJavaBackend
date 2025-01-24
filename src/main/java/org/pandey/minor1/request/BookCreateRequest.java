package org.pandey.minor1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.pandey.minor1.model.Author;
import org.pandey.minor1.model.Book;
import org.pandey.minor1.model.BookType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequest {


    @NotBlank(message = "Book Name must not be blank")
    private String name;

    @NotBlank(message = "Book no must not be blank")
    private String bookNo;

    @Positive
    private Integer cost;

    private BookType type;

    private String authorName;


    private String authorEmail;

    public Author toAuthor() {

        return Author.builder().
                name(this.authorName).
                email(this.authorEmail).build();
    }

    public Book toBook() {

        return Book.builder().
                name(this.name).
                bookNo(this.bookNo).
                cost(this.cost).
                type(this.type).
                build();
    }
}
