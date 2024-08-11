package warungmakansamudra.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.entity.Product;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateProductRequest;
import warungmakansamudra.api.model.ProductResponse;
import warungmakansamudra.api.model.UpdateProductRequest;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;

import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public ProductResponse create(CreateProductRequest request) {

//        log.info("Creating product with request: {}", request);
        if (request.getProductId() == null || request.getBranchId() == null) {
            throw new IllegalArgumentException("Product ID and Branch ID must not be null");
        }

        validationService.validate(request);

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));


        if(productRepository.existsById(request.getProductId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists");
        }


        Product product = new Product();
        product.setProductId(request.getProductId());
        product.setProductPriceId(request.getProductPriceId());
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setBranch(branch);
        productRepository.save(product);
        return toProductResponse(product, branch);
    }

    @Transactional
    public ProductResponse update(UpdateProductRequest request) {
        validationService.validate(request);

        log.info("Finding product with ID: {}", request.getProductId());
        Product productToUpdate = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        log.info("Product found: {}", productToUpdate);

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
        log.info("Branch found: {}", branch);

        productToUpdate.setProductId(request.getProductId());
        productToUpdate.setProductPriceId(request.getProductPriceId());
        productToUpdate.setProductCode(request.getProductCode());
        productToUpdate.setProductName(request.getProductName());
        productToUpdate.setPrice(request.getPrice());
        productToUpdate.setBranch(branch);
        productRepository.save(productToUpdate);

        return toProductResponse(productToUpdate, branch);
    }

    private ProductResponse toProductResponse(Product product, Branch branch) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productPriceId(product.getProductPriceId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .price(product.getPrice())
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .build();
    }
}
