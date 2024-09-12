package warungmakansamudra.api.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warungmakansamudra.api.entity.Product;

import java.util.function.Function;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse{


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

}
