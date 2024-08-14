package warungmakansamudra.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import warungmakansamudra.api.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Transaction.TransactionType findByTransactionType(Transaction.TransactionType transactionType);
//    Optional<Transaction> countByTransactionTypeAndTotalSales(Transaction.TransactionType transactionType, Long totalSales);
//    Long countByTotalSales(Long totalSales);
@Query("SELECT SUM(t.totalSales) FROM Transaction t WHERE t.transactionType = :transactionType " +
        "AND (:start IS NULL OR t.transactionDate >= :start) " +
        "AND (:end IS NULL OR t.transactionDate <= :end)")
Long getTotalSalesByTypeAndDate(@Param("transactionType") Transaction.TransactionType transactionType,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);
}
