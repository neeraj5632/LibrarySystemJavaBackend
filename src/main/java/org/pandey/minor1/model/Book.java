package org.pandey.minor1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    private String bookNo;

    private int cost;

    @Enumerated                     //by default, it will take ordinal value of ENUM
    private BookType type;



    @ManyToOne
    @JoinColumn
    private Student student;

     @ManyToOne
     @JoinColumn
     //@JsonIgnoreProperties("bookList") // for not creating loop from author class to book class or vice versa(but not working)
     private Author author;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Txn> txnList;
}
