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
import warungmakansamudra.api.entity.Transaction;
import warungmakansamudra.api.model.CreateTransactionRequest;
import warungmakansamudra.api.model.TransactionResponse;
import warungmakansamudra.api.model.WebResponse;
import warungmakansamudra.api.repository.BranchRepository;
import warungmakansamudra.api.repository.ProductRepository;
import warungmakansamudra.api.repository.TransactionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static warungmakansamudra.api.entity.Transaction.TransactionType.EAT_IN;
import static warungmakansamudra.api.entity.Transaction.TransactionType.ONLINE;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        productRepository.deleteAll();
        branchRepository.deleteAll();
    }

    @Test
    void createTransactions() throws Exception{
        Branch branch = new Branch();
        branch.setBranchId("test");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("test");
        branch.setPhoneNumber("test");
        branchRepository.save(branch);

        Product product = new Product();
        product.setBranch(branch);
        product.setProductId("test");
        product.setProductPriceId("test");
        product.setProductCode("test");
        product.setProductName("test");
        product.setPrice(15000L);
        productRepository.save(product);

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setBranchId(branch.getBranchId());
        request.setProductId(product.getProductId());
        request.setTransactionType(ONLINE);
        request.setQuantity(2L);
        request.setReceiptNumber(123123L);
        request.setTotalSales(request.getQuantity() * product.getPrice());

        mockMvc.perform(
                post("/api/transactions/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                WebResponse<TransactionResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
        });
                assertNotNull(response);
                assertNull(response.getErrors());
            assertNotNull(branchRepository.findByBranchId(branch.getBranchId()));
            assertNotNull(productRepository.findById(product.getProductId()));
            assertEquals("test", response.getData().getBranchId());
            assertEquals("test", response.getData().getProductId());
    });
}

@Test
    void getTransactions() throws Exception{
        Branch branch = new Branch();
        branch.setBranchId("test");
        branch.setBranchCode("test");
        branch.setBranchName("test");
        branch.setAddress("test");
        branch.setPhoneNumber("test");
        branchRepository.save(branch);

        Product product = new Product();
        product.setBranch(branch);
        product.setProductId("test");
        product.setProductPriceId("test");
        product.setProductCode("test");
        product.setProductName("test");
        product.setPrice(15000L);
        productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setBranch(branch);
        transaction.setProduct(product);
        transaction.setTransactionType(EAT_IN);
        transaction.setReceiptNumber(12345679L);
        transaction.setQuantity(5L);
        transaction.setTotalSales(product.getPrice() * transaction.getQuantity());
        transactionRepository.save(transaction);

        Transaction getTransaction = transactionRepository.findById(transaction.getBillId()).orElseThrow(null);

        mockMvc.perform(
                get("/api/transactions/" + getTransaction.getBillId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                WebResponse<TransactionResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
        });
                assertNotNull(response);
                assertNull(response.getErrors());
            assertNotNull(branchRepository.findByBranchId(branch.getBranchId()));
            assertNotNull(productRepository.findById(product.getProductId()));
            assertEquals("test", response.getData().getBranchId());
            assertEquals("test", response.getData().getProductId());
    });
}

@Test
    void listTransactions() throws Exception{
    for (int i = 0; i < 5; i++) {
        Branch branch = new Branch();
        branch.setBranchId("test - " + i);
        branch.setBranchCode("test - " + i);
        branch.setBranchName("test");
        branch.setAddress("test");
        branch.setPhoneNumber("test");
        branchRepository.save(branch);

        Product product = new Product();
        product.setBranch(branch);
        product.setProductId("test" + i);
        product.setProductPriceId("test" + i);
        product.setProductCode("test");
        product.setProductName("test");
        product.setPrice(15000L);
        productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setBranch(branch);
        transaction.setProduct(product);
        transaction.setTransactionType(EAT_IN);
        transaction.setReceiptNumber(12345679L);
        transaction.setQuantity(5L);
        transaction.setTotalSales(product.getPrice() * transaction.getQuantity());
        transactionRepository.save(transaction);

    }
        List<Transaction> getTransaction = transactionRepository.findAll();

        mockMvc.perform(
                get("/api/transactions/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                WebResponse<List<TransactionResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
        });

                assertNull(response.getErrors());
                assertEquals(5, response.getData().size());
    });
}
}