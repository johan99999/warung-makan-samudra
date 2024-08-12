package warungmakansamudra.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.entity.Product;
import warungmakansamudra.api.model.CreateProductRequest;
import warungmakansamudra.api.model.ProductResponse;
import warungmakansamudra.api.model.UpdateProductRequest;
import warungmakansamudra.api.model.WebResponse;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        branchRepository.deleteAll();
    }

    @Test
    void createProduct() throws Exception {

        Branch branch = new Branch();
        branch.setBranchId("test");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("test");
        branch.setPhoneNumber("999");
        branchRepository.save(branch);

        CreateProductRequest product = new CreateProductRequest();
        product.setBranchId(branch.getBranchId());
        product.setProductId("test");
        product.setProductPriceId("test");
        product.setProductCode("test");
        product.setProductName("test");
        product.setPrice(5000L);
        
        mockMvc.perform(
                post("/api/products/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(branch))
                        .content(objectMapper.writeValueAsString(product))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertNotNull(branchRepository.findByBranchId(branch.getBranchId()));
            assertNotNull(productRepository.findById(product.getProductId()));
            assertEquals("test", response.getData().getBranchId());
            assertEquals("test", response.getData().getProductId());
        });
    }

    @Test
    void updateProduct() throws Exception {

        Branch branch = new Branch();
        branch.setBranchId("test");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("test");
        branch.setPhoneNumber("999");
        branchRepository.save(branch);

        Branch branch2 = new Branch();
        branch2.setBranchId("test2");
        branch2.setBranchCode("test2");
        branch2.setBranchName("test2");
        branch2.setAddress("test2");
        branch2.setPhoneNumber("999");
        branchRepository.save(branch2);

        Product product = new Product();
        product.setBranch(branch);
        product.setProductId("test");
        product.setProductPriceId("test");
        product.setProductCode("test");
        product.setProductName("test");
        product.setPrice(5000L);
        productRepository.save(product);
        log.info("Product saved: {}", productRepository.findById("test"));



        UpdateProductRequest updateProduct = new UpdateProductRequest();
        updateProduct.setBranchId(branch2.getBranchId());
//        updateProduct.setProductId("test2");
//        updateProduct.setBranch(branch2);
        updateProduct.setProductPriceId("test2");
        updateProduct.setProductCode("test2");
        updateProduct.setProductName("test2");
        updateProduct.setPrice(15000L);


        mockMvc.perform(
                put("/api/products/" + product.getProductId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(branch))
                        .content(objectMapper.writeValueAsString(updateProduct))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertNotNull(branchRepository.findByBranchId(branch.getBranchId()));
            assertNotNull(productRepository.findById(product.getProductId()));
            assertEquals("test2", response.getData().getBranchId());
            assertEquals("test", response.getData().getProductId());
        });
    }

    @Test
    void deleteProduct() throws Exception {

        Branch branch = new Branch();
        branch.setBranchId("test");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("test");
        branch.setPhoneNumber("999");
        branchRepository.save(branch);

        Product product = new Product();
        product.setBranch(branch);
        product.setProductId("test");
        product.setProductPriceId("test");
        product.setProductCode("test");
        product.setProductName("test");
        product.setPrice(5000L);
        productRepository.save(product);

        mockMvc.perform(
                delete("/api/products/" + product.getProductId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            assertEquals("OK", response.getData());
        });
    }

    @Test
    void listProduct() throws Exception {


        for (int i = 0; i < 5; i++) {
            Branch branch = new Branch();
            branch.setBranchId("test - " + i);
            branch.setBranchCode("test - " + i);
            branch.setBranchName("test - " + i);
            branch.setAddress("test - " + i);
            branch.setPhoneNumber("999" + i);
            branchRepository.save(branch);

            Product productList = new Product();
            productList.setBranch(branch);
            productList.setProductId("test - " + i);
            productList.setProductPriceId("test - " + i);
            productList.setProductCode("test - " + i);
            productList.setProductName("test - " + i);
            productList.setPrice(5000L);
            productRepository.save(productList);
        }
//        List<Product> product = productRepository.findAll();

        mockMvc.perform(
                get("/api/products/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ProductResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(5, response.getData().size());
        });
    }
}