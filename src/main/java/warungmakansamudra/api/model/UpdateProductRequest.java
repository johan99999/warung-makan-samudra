package warungmakansamudra.api.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warungmakansamudra.api.entity.Branch;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {

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


    @Size(max = 100)
    private String branchId;


//    @ManyToOne
//    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
//    private Branch branch;
}
