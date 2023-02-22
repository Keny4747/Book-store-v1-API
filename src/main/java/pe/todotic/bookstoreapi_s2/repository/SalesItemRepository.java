package pe.todotic.bookstoreapi_s2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.bookstoreapi_s2.model.SalesItem;
import pe.todotic.bookstoreapi_s2.model.SalesOrder;
@Repository
public interface SalesItemRepository extends JpaRepository<SalesItem, Integer> {
}
