package warungmakansamudra.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warungmakansamudra.api.entity.Transaction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long billId;
    private Long receiptNumber;
    private LocalDateTime transactionDate;
    private Transaction.TransactionType transactionType;
    private String productId;
    private String productPriceId;
    private String productCode;
    private String productName;
    private Long price;
    private String branchId;
    private String branchCode;
    private String branchName;
    private String address;
    private String phoneNumber;
    private Long quantity;
    private Long totalSales;
}
