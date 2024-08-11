package warungmakansamudra.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank
    @Size(max = 100)
    private String productId;

    @NotBlank
    @Size(max = 100)
    private String productPriceId;

    @NotBlank
    @Size(max = 100)
    private String productCode;

    @NotBlank
    @Size(max = 100)
    private String productName;

    private Long price;

    @NotBlank
    @Size(max = 100)
    private String branchId;
}
