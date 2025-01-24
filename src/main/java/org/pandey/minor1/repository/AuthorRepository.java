package org.pandey.minor1.repository;

import org.pandey.minor1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    // 1. first way of writing the query
   // @Query(value = "Select * from author where email = :email", nativeQuery = true) //mysql running the query
   // Author getAuthor(String email);

  // 2. Second way of writing the query

   // @Query(value = "Select a from Author a where a.email = :email") //hibernate running the query y default nativeQuery = false
   // Author getAuthorWithoutNative(String email);

    // 3. Third way of writing the query
    //we have to follow some rule( way of writing) then Hibernate will create my query

    Author findByEmail(String email);

   //Author findByEmailAndName(String email, String name);

}
