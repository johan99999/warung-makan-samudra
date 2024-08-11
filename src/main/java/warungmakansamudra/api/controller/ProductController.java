package warungmakansamudra.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.entity.Product;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateProductRequest;
import warungmakansamudra.api.model.ProductResponse;
import warungmakansamudra.api.model.WebResponse;
import warungmakansamudra.api.repository.ProductRepository;
import warungmakansamudra.api.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path = "/api/products/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> create (@RequestBody CreateProductRequest createProductRequest) {
        ProductResponse productResponse = productService.create(createProductRequest);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }
}
