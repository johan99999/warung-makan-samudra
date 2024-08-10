package warungmakansamudra.api.service;

import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateBranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import warungmakansamudra.api.model.UpdateBranchRequest;
import warungmakansamudra.api.repository.BranchRepository;

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

    @Transactional
    public BranchResponse update(UpdateBranchRequest request) {
        validationService.validate(request);

        Branch branchToUpdate = branchRepository.findById(request.getBranchId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        branchToUpdate.setBranchId(request.getBranchId());
        branchToUpdate.setBranchCode(request.getBranchCode());
        branchToUpdate.setBranchName(request.getBranchName());
        branchToUpdate.setAddress(request.getAddress());
        branchToUpdate.setPhoneNumber(request.getPhoneNumber());
        branchRepository.save(branchToUpdate);

        return BranchResponse.builder()
                .branchId(branchToUpdate.getBranchId())
                .branchCode(branchToUpdate.getBranchCode())
                .branchName(branchToUpdate.getBranchName())
                .address(branchToUpdate.getAddress())
                .phoneNumber(branchToUpdate.getPhoneNumber())
                .build();
    }


    @Transactional
    public void delete(Branch branch, String branchId) {
        branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        branchRepository.delete(branch);
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


    private BranchResponse toBranchResponseUpdate(Branch branch) {
        return BranchResponse.builder()
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .build();
    }
}
