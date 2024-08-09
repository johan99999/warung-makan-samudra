package model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    @Id
    private String productId;

    private String productPriceId;

    private String productCode;

    private String productName;

    private Long price;

    private String branchId;

}
