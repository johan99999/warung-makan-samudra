package controller;

import entity.Branch;
import model.BranchResponse;
import model.CreateBranchRequest;
import model.ProductResponse;
import model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.BranchService;

@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping(
            path = "/api/branch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<BranchResponse> create(Branch branch, @RequestBody CreateBranchRequest createBranchRequest) {
        BranchResponse branchResponse = branchService.create(createBranchRequest);
        return WebResponse.<BranchResponse>builder().data(branchResponse).build();
    }

}
