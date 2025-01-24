package org.pandey.minor1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    @Column(length = 30, unique = true, nullable = false) //suppose if we remove ullable constraits here it wil not change in DB
    private  String email;      //After creatig tbale wqe can not chnage the contraints but can add new column with constariants

     // later addedd to confrm db and hibernate how adding to Db
//    @Column(length = 30, unique = true, nullable = false)
//    private  String contact;



    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @JsonIgnore   // for not creating loop from author class to book class or vice versa
    @OneToMany(mappedBy = "author")
   // @JsonIgnoreProperties("bookList")
    private List<Book> booklist;


}
