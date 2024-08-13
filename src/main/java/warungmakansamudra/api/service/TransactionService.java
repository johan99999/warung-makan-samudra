package warungmakansamudra.api.service;

import jakarta.transaction.Transactional;
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
}
