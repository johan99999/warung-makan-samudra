package warungmakansamudra.api.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponse {


    private String branchId;

    private String branchCode;

    private String branchName;

    private String address;

    private String phoneNumber;
}
