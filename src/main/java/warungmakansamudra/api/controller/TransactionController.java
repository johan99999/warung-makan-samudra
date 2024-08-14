package warungmakansamudra.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warungmakansamudra.api.entity.Transaction;
import warungmakansamudra.api.model.CreateTransactionRequest;
import warungmakansamudra.api.model.TotalSalesResponse;
import warungmakansamudra.api.model.TransactionResponse;
import warungmakansamudra.api.model.WebResponse;
import warungmakansamudra.api.service.TransactionService;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/api/transactions/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TransactionResponse> create(@RequestBody CreateTransactionRequest createTransactionRequest){
        TransactionResponse transactionResponse = transactionService.create(createTransactionRequest);
        return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
    }

    @GetMapping(
            path = "/api/transactions/{bill_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TransactionResponse> get(Transaction transaction, @PathVariable("bill_id") Long billId){
        TransactionResponse transactionResponse = transactionService.get(transaction, billId);
        return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
    }

    @GetMapping(
            path = "/api/transactions/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<TransactionResponse>> list(){
        List<TransactionResponse> transactionResponseList = transactionService.list();
        return WebResponse.<List<TransactionResponse>>builder().data(transactionResponseList).build();
    }

    @GetMapping("/api/transactions/total_sales")
    public ResponseEntity<WebResponse<TotalSalesResponse>> getTotalSales(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {

        TotalSalesResponse totalSales = transactionService.getTotalSales(startDate, endDate);
        WebResponse<TotalSalesResponse> response = new WebResponse<>();
        response.setData(totalSales);
        response.setErrors(null);
        response.setPaging(null);

        return ResponseEntity.ok(response);
    }
}
