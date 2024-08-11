package warungmakansamudra.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.entity.Product;
import warungmakansamudra.api.model.*;
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

    @PutMapping(path = "/api/products/{product_id}")
    public WebResponse<ProductResponse> update (@RequestBody UpdateProductRequest updateProductRequest,
                                                @PathVariable("product_id") String productId) {

        updateProductRequest.setProductId(productId);

        ProductResponse productResponse = productService.update(updateProductRequest);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }
}
