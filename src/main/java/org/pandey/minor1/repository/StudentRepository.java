package org.pandey.minor1.repository;

import org.pandey.minor1.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByPhoneNo(String phoneNo);
}
