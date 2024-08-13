package warungmakansamudra.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warungmakansamudra.api.entity.Transaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionRequest {

    private Long receiptNumber;

    private Transaction.TransactionType transactionType;

    @NotBlank
    @Size(max = 100)
    private String productId;

    @NotBlank
    @Size(max = 100)
    private String branchId;


    private Long quantity;

    private Long totalSales;

}
