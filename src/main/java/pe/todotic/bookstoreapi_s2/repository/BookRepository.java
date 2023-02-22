package pe.todotic.bookstoreapi_s2.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.todotic.bookstoreapi_s2.model.Book;
import java.util.List;
import java.util.Optional;

//convertir la interface en un repositorio para la entidad Book
@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findTop6ByOrderByCreatedAtDesc();
    Optional<Book> findOneBySlug(String slug);
}
