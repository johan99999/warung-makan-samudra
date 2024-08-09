package warungmakansamudra.api.service;

import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateBranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import warungmakansamudra.api.repository.BranchRepository;

import java.util.UUID;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public BranchResponse create(Branch branch, CreateBranchRequest request) {
        validationService.validate(request);

        if(branchRepository.existsById(request.getBranchId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Branch already exists");
        }

        Branch createBranch = new Branch();
        createBranch.setBranchId(request.getBranchId());
        createBranch.setBranchCode(request.getBranchCode());
        createBranch.setBranchName(request.getBranchName());
        createBranch.setAddress(request.getAddress());
        createBranch.setPhoneNumber(request.getPhoneNumber());

        branchRepository.save(createBranch);

        return toBranchResponse(createBranch);
    }

    private BranchResponse toBranchResponse(Branch branch) {
        return BranchResponse.builder()
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .build();
    }
}
