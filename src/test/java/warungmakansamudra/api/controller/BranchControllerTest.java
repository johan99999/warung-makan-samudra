package warungmakansamudra.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateBranchRequest;
import warungmakansamudra.api.model.UpdateBranchRequest;
import warungmakansamudra.api.model.WebResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;
import warungmakansamudra.api.repository.TransactionRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        productRepository.deleteAll();
        branchRepository.deleteAll();
    }

    @Test
    void createBranch() throws Exception {

        branchRepository.deleteAll();

        CreateBranchRequest branch = new CreateBranchRequest();
        branch.setBranchId("test");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("Test");
        branch.setPhoneNumber("999");

        mockMvc.perform(
                post("/api/branch/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branch))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<BranchResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertEquals(branch.getBranchId(), response.getData().getBranchId());
        });
    }

    @Test
    void updateBranch() throws Exception {
        Branch branch = new Branch();
        branch.setBranchId("test2");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("Test");
        branch.setPhoneNumber("999");
        branchRepository.save(branch);

        UpdateBranchRequest request = new UpdateBranchRequest();
        request.setBranchCode("test2");
        request.setBranchName("test2");
        request.setAddress("test2");
        request.setPhoneNumber("998");

        mockMvc.perform(
                put("/api/branch/" + branch.getBranchId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<BranchResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("test2", response.getData().getBranchCode());
            assertEquals("test2", response.getData().getBranchName());
            assertEquals("test2", response.getData().getAddress());
            assertEquals("998", response.getData().getPhoneNumber());

            Branch branchUpdate = branchRepository.findById("test2").orElse(null);
            assertNotNull(branchUpdate);
            assertEquals(branch.getBranchId(), branchUpdate.getBranchId());
        });
    }

    @Test
    void deleteBranch() throws Exception {
        Branch branch = new Branch();
        branch.setBranchId("test3");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("Test");
        branch.setPhoneNumber("999");
        branchRepository.save(branch);

        mockMvc.perform(
                delete("/api/branch/" + branch.getBranchId())
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
    void getBranch() throws Exception {
        Branch branch = new Branch();
        branch.setBranchId("test4");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("Test");
        branch.setPhoneNumber("999");
        branchRepository.save(branch);

        mockMvc.perform(
                get("/api/branch/" + branch.getBranchId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<BranchResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            assertEquals("test4", response.getData().getBranchId());
            assertEquals("test", response.getData().getBranchCode());
            assertEquals("test", response.getData().getBranchName());
            assertEquals("Test", response.getData().getAddress());
            assertEquals("999", response.getData().getPhoneNumber());

        });
    }

}