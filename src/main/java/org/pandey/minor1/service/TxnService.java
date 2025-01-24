package org.pandey.minor1.service;

import org.pandey.minor1.exception.TxnException;
import org.pandey.minor1.model.*;
import org.pandey.minor1.repository.TxnRepository;
import org.pandey.minor1.request.TxnCreateRequest;
import org.pandey.minor1.request.TxnReturnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequestMapping("txn")
public class TxnService {

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Value("${student.valid.days}")
    private String validUpto;

    @Value("${student.delayed.finePerDay}")
    private int finePerDay;


    private Student filterStudent(StudentFilterType type, Operator operator, String value) throws TxnException {
        //Wanted to see if student is valid or not

        List<Student> studentList =  studentService.filter(type, operator, value);

        if(studentList == null || studentList.isEmpty()){

            throw new TxnException("Student does not belong to my Student Libaray. ");

        }

        Student studentFromDB = studentList.get(0);

        return studentFromDB;

    }

    private Book filterBook(FilterType type, Operator operator, String value) throws TxnException {
        //Wanted to see if book is valid or not

        List<Book> bookList =  bookService.filter(type, operator, value);

        if(bookList == null || bookList.isEmpty()){

            throw new TxnException("Book does not belong to my Student Libaray. ");

        }

        Book bookFromLib = bookList.get(0);

        return bookFromLib;

    }

    @Transactional(rollbackFor = {TxnException.class})
    public String create(TxnCreateRequest txnCreateRequests) throws TxnException {

        //Wanted to see if student is valid or not

        Student studentFromDB = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, txnCreateRequests.getStudentContact());




        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnCreateRequests.getBookNo());




        if(bookFromLib.getStudent() != null){

            throw new TxnException("Book has already belong to some other student. ");
        }

         String txnId = UUID.randomUUID().toString();
      Txn txn = Txn.builder().
              student(studentFromDB).
              book(bookFromLib).
              txnId(txnId).
              paidAmount(txnCreateRequests.getAmount()).
              status(TxnStatus.ISSUED).
              build();

      txn = txnRepository.save(txn);

      bookFromLib.setStudent(studentFromDB);

      bookService.saveUpdate(bookFromLib);

      return txn.getTxnId();



    }

    private int calculateSettlementAmount(Txn txn){

      long issueTime =  txn.getCreatedOn().getTime();
      long currentTime = System.currentTimeMillis();

      long timeDff = currentTime - issueTime;

      int daysPassed = (int) TimeUnit.DAYS.convert(timeDff, TimeUnit.MILLISECONDS);

      if(daysPassed > Integer.valueOf(validUpto)){

          int fineAmount = ( daysPassed - Integer.valueOf(validUpto)) * finePerDay;
          return txn.getPaidAmount() - fineAmount;
      }

      return  txn.getPaidAmount();

    }

    @Transactional(rollbackFor = {TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException {

        Student studentFromDB = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, txnReturnRequest.getStudentContact());
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnReturnRequest.getBookNo());

         if(bookFromLib.getStudent() != null && bookFromLib.getStudent().equals(studentFromDB)){

             Txn  txnFromDb =  txnRepository.findByTxnId(txnReturnRequest.getTxnID());

             if(txnFromDb == null){
                 throw new TxnException("No Transaction hae been found for this transaction id");
             }

            int amount =  calculateSettlementAmount(txnFromDb);

             if(amount == txnFromDb.getPaidAmount()){
                 txnFromDb.setStatus(TxnStatus.RETURNED);
             }

             else{
                 txnFromDb.setStatus(TxnStatus.FINED);
             }
 //Setting the final paid amount by the user at last
             txnFromDb.setPaidAmount(amount);

             //now as transaction competed need to update the book by marking student as null

             bookFromLib.setStudent(null);
             bookService.saveUpdate(bookFromLib);

             return amount;

         }else{
             throw new TxnException("Book is either not assigned to anyone or may be assigned to someone else");
         }
    }
//    @Transactional(rollbackFor = {TxnException.class})
//    public String createTxnInDb(Txn txn, Book bookFromLib){   //its a good idea to put transactional annotation to small block
//
//        txn = txnRepository.save(txn);
//        bookFromLib.setStudent(studentFromDB);
//
//        bookService.saveUpdate(bookFromLib);
//
//        return txn.getTxnId();
//
//
//    }
}
