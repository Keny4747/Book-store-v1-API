package pe.todotic.bookstoreapi_s2.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.bookstoreapi_s2.model.SalesOrder;
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
}
