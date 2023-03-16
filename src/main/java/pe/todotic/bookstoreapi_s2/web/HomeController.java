package pe.todotic.bookstoreapi_s2.web;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s2.model.Book;
import pe.todotic.bookstoreapi_s2.model.SalesOrder;
import pe.todotic.bookstoreapi_s2.repository.BookRepository;
import pe.todotic.bookstoreapi_s2.repository.SalesOrderRepository;
import pe.todotic.bookstoreapi_s2.service.PaypalService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private PaypalService paypalService;
    @GetMapping("/last-books")
    List<Book> getLastBooks(){

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

    @PostMapping("/checkout/paypal/create")
    Map<String, String> createPaypalCheckout(
            @RequestBody List<Integer> bookId,
            @RequestParam String returnUrl){

        paypalService.createOrder(null,returnUrl,returnUrl);
    }
}
