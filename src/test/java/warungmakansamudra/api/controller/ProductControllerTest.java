package warungmakansamudra.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import warungmakansamudra.api.model.WebResponse;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
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
}