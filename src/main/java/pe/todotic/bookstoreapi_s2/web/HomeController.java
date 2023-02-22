package pe.todotic.bookstoreapi_s2.web;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.todotic.bookstoreapi_s2.model.Book;
import pe.todotic.bookstoreapi_s2.model.SalesOrder;
import pe.todotic.bookstoreapi_s2.repository.BookRepository;
import pe.todotic.bookstoreapi_s2.repository.SalesOrderRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @GetMapping("/last-books")
    List<Book> getLastBooks(){
      // Pageable pageable= PageRequest.of(0,6, Sort.by("createdAt").descending());
      // return bookRepository.findAll(pageable).getContent();
        return bookRepository.findTop6ByOrderByCreatedAtDesc();
    }
    @GetMapping
    Page<Book> getBooks(@PageableDefault(sort = "title",direction = Sort.Direction.ASC,size = 5)Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    @GetMapping("/books-api/{slug}")
    Book getBook(@PathVariable String slug){
        return bookRepository.findOneBySlug(slug)
                .orElseThrow(EntityNotFoundException::new);
    }
    @GetMapping("/orders/{id}")
    SalesOrder getSalesOrder(@PathVariable Integer id){
        return salesOrderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
