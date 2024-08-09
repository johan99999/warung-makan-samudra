package warungmakansamudra.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import warungmakansamudra.api.entity.Branch;
import warungmakansamudra.api.model.BranchResponse;
import warungmakansamudra.api.model.CreateBranchRequest;
import warungmakansamudra.api.model.WebResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import warungmakansamudra.api.repository.BranchRepository;

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

    @BeforeEach
    void setUp() {
        branchRepository.deleteAll();
    }

    @Test
    void createBranch() throws Exception {

        branchRepository.deleteAll();

        CreateBranchRequest branch = new CreateBranchRequest();
        branch.setBranchId(UUID.randomUUID().toString());
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
}