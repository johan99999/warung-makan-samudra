package warungmakansamudra.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateBranchRequest;
import warungmakansamudra.api.model.UpdateBranchRequest;
import warungmakansamudra.api.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.service.BranchService;

@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchRepository branchRepository;

    @PostMapping(
            path = "/api/branch/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<BranchResponse> create(Branch branch, @RequestBody CreateBranchRequest createBranchRequest) {
        BranchResponse branchResponse = branchService.create(branch, createBranchRequest);
        return WebResponse.<BranchResponse>builder().data(branchResponse).build();
    }

    @PutMapping(
            path = "/api/branch/{branch_id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BranchResponse> update(Branch branch ,@PathVariable("branch_id") String branchId,
                                              @RequestBody UpdateBranchRequest updateBranchRequest) {

        updateBranchRequest.setBranchId(branchId);

        BranchResponse branchResponse = branchService.update(updateBranchRequest);
        return WebResponse.<BranchResponse>builder().data(branchResponse).build();
    }

    @DeleteMapping(
            path = "/api/branch/{branch_id}"
    )
    public WebResponse<String> delete(Branch branch, @PathVariable("branch_id") String branchId){
        branchService.delete(branch, branchId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/branch/{branch_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BranchResponse> get(Branch branch, @PathVariable("branch_id") String branchId){
        BranchResponse branchResponse = branchService.get(branch, branchId);
        return WebResponse.<BranchResponse>builder().data(branchResponse).build();
    }
    }



