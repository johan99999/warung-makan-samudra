package warungmakansamudra.api.service;

import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.entity.Product;
import warungmakansamudra.api.entity.Transaction;
import warungmakansamudra.api.model.CreateTransactionRequest;
import warungmakansamudra.api.model.TotalSalesResponse;
import warungmakansamudra.api.model.TransactionResponse;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;
import warungmakansamudra.api.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TransactionResponse create(CreateTransactionRequest request) {
        validationService.validate(request);

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Transaction transaction = new Transaction();
        transaction.setBranch(branch);
        transaction.setProduct(product);
        transaction.setReceiptNumber(request.getReceiptNumber());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setQuantity(request.getQuantity());
        transaction.setTotalSales(request.getTotalSales());
        transactionRepository.save(transaction);

        return toTransactionResponse(transaction, product, branch);
    }

    @Transactional(readOnly = true)
    public TransactionResponse get(Transaction transaction, Long billId){
        transaction = transactionRepository.findById(billId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found"));
        return toTransactionResponse(transaction, transaction.getProduct(), transaction.getBranch());
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> list(){
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(this::toTransactionResponseList)
                .collect(Collectors.toList());
    }
    public TotalSalesResponse getTotalSales(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime start = Optional.ofNullable(startDate).map(date -> LocalDateTime.parse(date, formatter)).orElse(null);
        LocalDateTime end = Optional.ofNullable(endDate).map(date -> LocalDateTime.parse(date, formatter)).orElse(null);

        Long eatInTotal = transactionRepository.getTotalSalesByTypeAndDate(Transaction.TransactionType.EAT_IN, start, end);
        Long takeAwayTotal = transactionRepository.getTotalSalesByTypeAndDate(Transaction.TransactionType.TAKE_AWAY, start, end);
        Long onlineTotal = transactionRepository.getTotalSalesByTypeAndDate(Transaction.TransactionType.ONLINE, start, end);

        TotalSalesResponse response = new TotalSalesResponse();
        response.setEatIn(eatInTotal);
        response.setTakeAway(takeAwayTotal);
        response.setOnline(onlineTotal);

        return response;
    }


    private TransactionResponse toTransactionResponse(Transaction transaction, Product product, Branch branch) {
        return TransactionResponse.builder()
                .billId(transaction.getBillId())
                .receiptNumber(transaction.getReceiptNumber())
                .transactionDate(transaction.getTransactionDate())
                .transactionType(transaction.getTransactionType())
                .productId(product.getProductId())
                .productPriceId(product.getProductPriceId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .price(product.getPrice())
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .quantity(transaction.getQuantity())
                .totalSales(transaction.getTotalSales())
                .build();
    }

    private TransactionResponse toTransactionResponseList(Transaction transaction) {
        Product product = transaction.getProduct();
        Branch branch = transaction.getBranch();
        return TransactionResponse.builder()
                .billId(transaction.getBillId())
                .receiptNumber(transaction.getReceiptNumber())
                .transactionDate(transaction.getTransactionDate())
                .transactionType(transaction.getTransactionType())
                .productId(product.getProductId())
                .productPriceId(product.getProductPriceId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .price(product.getPrice())
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .quantity(transaction.getQuantity())
                .totalSales(transaction.getTotalSales())
                .build();
    }
}
