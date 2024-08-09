package service;

import entity.Branch;
import model.BranchResponse;
import model.CreateBranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BranchRepository;
import repository.ProductRepository;

import java.util.UUID;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public BranchResponse create(CreateBranchRequest request) {
        validationService.validate(request);

        Branch createBranch = new Branch();
        createBranch.setBranchId(UUID.randomUUID().toString());
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
