package org.pandey.minor1.controller;

import jakarta.validation.Valid;
import org.pandey.minor1.exception.TxnException;

import org.pandey.minor1.request.TxnCreateRequest;
import org.pandey.minor1.request.TxnReturnRequest;
import org.pandey.minor1.response.GenericResponse;
import org.pandey.minor1.service.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("txn")
public class TxnController {

    @Autowired
    private TxnService txnService;


    @PostMapping("/create")
    public ResponseEntity<GenericResponse<String>> createTxn(@RequestBody @Valid TxnCreateRequest txnCreateRequests) throws TxnException {

        String txnId =  txnService.create(txnCreateRequests);

        GenericResponse<String> response = new GenericResponse<>(txnId, "" , "Succcess" , "200");

        ResponseEntity entity = new ResponseEntity<>(response, HttpStatus.OK);

        return entity;
    }

    @PutMapping("/return")
    public int returnTxn(@RequestBody TxnReturnRequest txnReturnRequest) throws TxnException {

        return txnService.returnBook(txnReturnRequest);
    }

}
