package warungmakansamudra.api.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import warungmakansamudra.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> , JpaSpecificationExecutor<Product> {

//    Optional<Product> findFirstByProductAndId(Product product, String productId);
//
//    List<Product> findAllByProduct(Product product);
List<Product> findByBranchBranchId(String branchId);
}
