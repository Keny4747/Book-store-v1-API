package pe.todotic.bookstoreapi_s2.web;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s2.exception.BadRequestException;
import pe.todotic.bookstoreapi_s2.model.Book;
import pe.todotic.bookstoreapi_s2.model.SalesItem;
import pe.todotic.bookstoreapi_s2.model.SalesOrder;
import pe.todotic.bookstoreapi_s2.repository.BookRepository;
import pe.todotic.bookstoreapi_s2.repository.SalesItemRepository;
import pe.todotic.bookstoreapi_s2.repository.SalesOrderRepository;
import pe.todotic.bookstoreapi_s2.service.PaypalService;
import pe.todotic.bookstoreapi_s2.service.SalesOrderService;
import pe.todotic.bookstoreapi_s2.service.StorageService;
import pe.todotic.bookstoreapi_s2.web.paypal.OrderCaptureResponse;
import pe.todotic.bookstoreapi_s2.web.paypal.OrderResponse;

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

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SalesItemRepository salesItemRepository;

    @Autowired
    private StorageService storageService;

    @GetMapping("/last-books")
    List<Book> getLastBooks() {

        return bookRepository.findTop6ByOrderByCreatedAtDesc();
    }

    @GetMapping
    Page<Book> getBooks(@PageableDefault(sort = "title", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @GetMapping("/books-api/{slug}")
    Book getBook(@PathVariable String slug) {
        return bookRepository.findOneBySlug(slug)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/orders/{id}")
    SalesOrder getSalesOrder(@PathVariable Integer id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/checkout/paypal/create")
    Map<String, String> createPaypalCheckout(
            @RequestBody List<Integer> bookIds,
            @RequestParam String returnUrl) {

        SalesOrder salesOrder = salesOrderService.create(bookIds);
        OrderResponse orderResponse = paypalService.createOrder(salesOrder, returnUrl, returnUrl);

        String approveUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return Map.of("approve", approveUrl);

    }

    @PostMapping("/checkout/paypal/capture")
    Map<String, Object> capturePaypalCheckout(@RequestParam String token) {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(token);

        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");
        int orderId = 0;

        if (completed) {
            orderId = Integer.parseInt(orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId());
            SalesOrder salesOrder = salesOrderRepository
                    .findById(orderId)
                    .orElseThrow(RuntimeException::new);

            salesOrder.setPaymentStatus(SalesOrder.PaymentStatus.PAID);
            salesOrderRepository.save(salesOrder);
        }

        return Map.of("completed", completed, "orderId", orderId);
    }

    @GetMapping("/orders/{orderId}/items/{itemId}/book/download")
    Resource downloadBookFromSalesItem(@PathVariable Integer orderId, @PathVariable Integer itemId) {
        SalesOrder salesOrder = salesOrderRepository
                .findById(orderId)
                .orElseThrow(RuntimeException::new);

        if (!salesOrder.getPaymentStatus().equals(SalesOrder.PaymentStatus.PAID)) {
            throw new BadRequestException("The order hasn't bean paid yet.");
        }
        SalesItem salesItem = salesItemRepository
                .findOneByIdAndOrderId(itemId, orderId)
                .orElseThrow(EntityNotFoundException::new);

        if (salesItem.getDownloadsAvailable() > 0) {
            salesItem.setDownloadsAvailable(salesItem.getDownloadsAvailable() - 1);
            salesItemRepository.save(salesItem);
        } else {
            throw new BadRequestException("can't download this file anymore.");
        }
        return storageService.loadAsResource(salesItem.getBook().getFilePath());

    }
}
