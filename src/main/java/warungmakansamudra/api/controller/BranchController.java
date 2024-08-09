package warungmakansamudra.api.controller;

import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateBranchRequest;
import warungmakansamudra.api.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import warungmakansamudra.api.service.BranchService;

@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping(
            path = "/api/branch/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<BranchResponse> create(Branch branch, @RequestBody CreateBranchRequest createBranchRequest) {
        BranchResponse branchResponse = branchService.create(branch, createBranchRequest);
        return WebResponse.<BranchResponse>builder().data(branchResponse).build();
    }

}
