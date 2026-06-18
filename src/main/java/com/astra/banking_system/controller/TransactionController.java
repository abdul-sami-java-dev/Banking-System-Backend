package com.astra.banking_system.controller;

import com.astra.banking_system.dto.TransactionHistoryResponse;
import com.astra.banking_system.dto.TransactionRequest;
import com.astra.banking_system.dto.TransferRequest;
import com.astra.banking_system.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionRequest request){
        transactionService.deposit(request);
        return ResponseEntity.ok("Deposit Successful");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody TransactionRequest request){
        transactionService.withdraw(request);
        return ResponseEntity.ok("Withdrawal Successful");
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request){
        transactionService.transfer(request);
        return ResponseEntity.ok("Transfer Successful");
    }

    @GetMapping("/transactionHistory")
    public ResponseEntity<List<TransactionHistoryResponse>> transactionHistory(){
        return ResponseEntity.ok(transactionService.transactionHistory());
    }

}
