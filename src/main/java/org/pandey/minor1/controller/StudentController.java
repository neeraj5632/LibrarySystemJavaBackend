package org.pandey.minor1.controller;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import org.pandey.minor1.exception.TxnException;
import org.pandey.minor1.model.Student;
import org.pandey.minor1.request.StudentCreateRequest;
import org.pandey.minor1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public Student createStudent(@RequestBody StudentCreateRequest studentCreateRequest) throws TxnException {

          //Validation here for request
        if(studentCreateRequest.getPhoneNo() == null || StringUtils.isEmpty(studentCreateRequest.getPhoneNo())){
            throw new TxnException("Student phoneNo can not be null or empty. ");
        }

        return studentService.createStudent(studentCreateRequest);
    }
           //this exception will work only for this book Controller

//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handle(Exception e){
//
//        return  new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
//    }



}
