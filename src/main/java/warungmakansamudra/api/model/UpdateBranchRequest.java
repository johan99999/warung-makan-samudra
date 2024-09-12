package warungmakansamudra.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateBranchRequest {

    @Size(max = 100)
    private String branchId;

    @Size(max = 100)
    private String branchCode;


    @Size(max = 100)
    private String branchName;


    @Size(max = 100)
    private String address;

    @NotBlank
    @Size(max = 100)
    private String phoneNumber;
}
