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
import warungmakansamudra.api.model.TransactionResponse;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;
import warungmakansamudra.api.repository.TransactionRepository;

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

    public List<TransactionResponse> list(){
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(this::toTransactionResponseList)
                .collect(Collectors.toList());
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
